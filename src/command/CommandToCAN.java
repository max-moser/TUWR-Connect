package command;

import java.util.List;

public class CommandToCAN {

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
		byte[] ret = new byte[8];
		
		for(int i=0; i<8; i++){
			ret[i] = 0;
		}
		
		List<Parameter> params = c.getParameters();
		
		for(Parameter p: params){
			// assumption:
			// each parameter's area [the "affected" bits] are pairwise disjoint
			
			int offset, length, fixpoint, bytenofrom, bitnofrom, bytenoto;
			int bitsleft, bitsright, ind;
			byte[] value;
			byte tmp;
			
			offset = p.getOffset();
			length = p.getLength();
			fixpoint = p.getFixpoint();
			value = p.getValue().getFormatted(fixpoint, length); // the FP
			// assumption:
			// the superfluous bits in the value bytes are filled with 0
			// e.g. if the bit length of the value is 13, the bytes look like this:
			// [XXXX XXXX][XXXX X000]
			
			bytenofrom = offset / 8; // the index of the starting byte (ranging from 0 to 8)
			bitnofrom = offset % 8; // the index of the starting bit in the starting byte (0 = highest value, 7 = least value)
			bytenoto = (offset + length) / 8; // the index of the ending byte (should be greater or equal to the starting byte)
			
			bitsright = bitnofrom; // the amount of bits to shift FP[i] to the right
			bitsleft = 8 - bitsright; // the amount of bits to shift FP[i-1] to the left
			
			for(int b = bytenofrom; b < bytenoto; b++){
				ind = b - bytenofrom; // the index of the byte from the FP
				tmp = 0;
				
				if(ind == 0){
					
					tmp = value[ind];
					tmp >>= bitsright;
					ret[b] = (byte)(ret[b] | tmp);
					
				}else{
					
					tmp = value[ind - 1];
					tmp <<= bitsleft;
					ret[b] = (byte)(ret[b] | tmp);
					tmp = value[ind];
					tmp >>= bitsright;
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
		
		csum[0] = (byte)0b0000_0000;
		csum[1] = (byte)0b0000_0000;
		
		int cnt = 0;
		byte tmp = (byte)0b1000_0000;
		
		for(int i=0; i<b.length; i++){
			tmp = (byte)0b1000_0000;
			for(int j=0; j<8; j++){
				if((tmp & b[i]) > 0){
					cnt ++;
				}
				tmp >>= 1;
			}
		}
		
		csum[1] = (byte)cnt;
		csum[0] = (byte)(cnt >> 8);
		
		return csum;
	}
}
