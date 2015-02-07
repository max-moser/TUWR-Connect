package command;

import java.util.List;

/**
 * TODO descr
 * 
 * @author Maxmanski
 *
 */
public class CommandToCAN {

	private static final int bpb = Byte.SIZE;
	private static final int canmsglen = 8;
	
	/**
	 * Extracts the command's CAN ID
	 * 
	 * @param c The command to extract the ID from
	 * @return A byte containing the CAN ID of the command
	 */
	public static byte getID(Command c){
		return (byte)c.getID();
	}
	
	/**
	 * Converts the command with its parameters into a CAN message
	 * with a length of 8 (bytes).
	 * 
	 * @param c The command to convert
	 * @return A Byte array of length 8
	 */
	public static byte[] getData(Command c){
		byte[] ret = new byte[CommandToCAN.canmsglen];
		
		for(int i=0; i<CommandToCAN.canmsglen; i++){
			ret[i] = 0;
		}
		
		List<Parameter> params = c.getParameters();
		
		for(Parameter p: params){
			// assumption:
			// each parameter's area [the "affected" bits] are pairwise disjoint
			
			int offset, length, fixpoint, bytenofrom, bitnofrom, bytenoto, bytelen;
			int bitsleft, bitsright, ind;
			byte[] value;
			byte tmp, mask;
			
			offset = p.getOffset();
			length = p.getLength();
			fixpoint = p.getFixpoint();
			value = p.getValue().getFormatted(fixpoint, length); // the FP
			bytelen = ((offset + length - 1) / CommandToCAN.bpb) - (offset / CommandToCAN.bpb);
			// assumption:
			// the superfluous bits in the value bytes are filled with 0
			// e.g. if the bit length of the value is 13, the bytes look like this:
			// [XXXX XXXX][XXXX X000]
			
			bytenofrom = offset / CommandToCAN.bpb; // the index of the starting byte (ranging from 0 to 7)
			bitnofrom = offset % CommandToCAN.bpb; // the index of the starting bit in the starting byte (0 = highest value, 7 = least value)
			bytenoto = bytenofrom + bytelen; // the index of the ending byte (should be greater or equal to the starting byte)
			
			bitsright = bitnofrom; // the amount of bits to shift FP[i] to the right
			bitsleft = CommandToCAN.bpb - bitsright; // the amount of bits to shift FP[i-1] to the left
			
			// for a bit understanding, have a look at the draft that i made
			// TODO scan draft & save it in src folder
			for(int b = bytenofrom; b <= bytenoto; b++){
				ind = b - bytenofrom; // the index of the byte from the FP
				tmp = 0;
				mask = 0x1;
				for(int i=1; i<bitsleft; i++){
					mask <<= 1;
					mask = (byte)(mask | 0x1);
				}
				
				if(ind == 0){
					
					tmp = value[0];
					tmp >>= bitsright;
					tmp = (byte)(tmp & mask);
					ret[b] = (byte)(ret[b] | tmp);
					
				}else{
					
					tmp = value[ind - 1];
					tmp <<= bitsleft;
					tmp = (byte)(tmp & mask);
					ret[b] = (byte)(ret[b] | tmp);
					
					tmp = value[ind];
					tmp >>= bitsright;
					tmp = (byte)(tmp & mask);
					ret[b] = (byte)(ret[b] | tmp);
					
				}
			}
		}
		
		// calculate checksum & place it in the CAN message
		byte[] csum = calcChecksum(ret);
		
		// clear the first 12 bits
		ret[0] = (byte)(ret[0] & ((byte)0b0000_0000));
		ret[1] = (byte)(ret[1] & ((byte)0b0000_1111));
		
		// set them to the value of the checksum
		ret[0] = (byte)(ret[0] | csum[0]);
		ret[1] = (byte)(ret[1] | csum[1]);
		
		return ret;
	}
	
	/**
	 * TODO
	 * get algorithm from Igor
	 * @return
	 */
	private static byte[] calcChecksum(byte[] b){
		byte[] csum = new byte[2];
		byte mask = 0x1;
		for(int i=1; i<8; i++){
			mask <<= 1;
			mask = (byte)(mask | 0x1);
		}
		
		csum[0] = (byte)0b1111_1111;
		csum[1] = (byte)0b1111_0000;
		
		int cnt = 0;
		byte tmp = (byte)0b0000_0001;
		
		for(int i=0; i<b.length; i++){
			tmp = (byte)0b0000_0001;
			for(int j=1; j<8; j++){
				if((tmp & b[i]) != 0){
					cnt ++;
				}
				tmp <<= 1;
			}
		}
		
		csum[1] = (byte)((cnt << 4) & mask);
		csum[0] = (byte)((cnt >> 4) & mask);
		
		return csum;
	}
}
