package command;

import java.util.List;

/**
 * A class with static methods for converting Commands into CAN messages (an array of bytes with a fixed length).
 * 
 * @author Maxmanski
 * @version 1.0
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
			byte tmp, maskl, maskr; // maskl: mask for the bits on the left side of the byte; maskr analogous
			
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
			
			// initialising masks
			maskl = 0x1;
			maskr = 0x1;
			for(int i=1; i<bitsright; i++){
				maskl <<= 1;
				maskl = (byte)(maskl | 0x1);
			}
			for(int i=1; i<bitsleft; i++){
				maskr <<= 1;
				maskr = (byte)(maskr | 0x1);
				maskl <<= 1;
			}
			maskl <<= 1; // because the loop was 1 time too little
			
			// for a bit understanding, have a look at the draft that i made
			// TODO scan draft & save it in src folder
			for(int b = bytenofrom; b <= bytenoto; b++){
				ind = b - bytenofrom; // the index of the byte from the FP
				tmp = 0;
				
				if(ind == 0){
					
					tmp = value[0];
					tmp >>= bitsright;
					tmp = (byte)(tmp & maskr);
					ret[b] = (byte)(ret[b] | tmp);
					
				}else{
					
					tmp = value[ind - 1];
					tmp <<= bitsleft;
					tmp = (byte)(tmp & maskl);
					ret[b] = (byte)(ret[b] | tmp);
					
					if(ind < value.length){
						tmp = value[ind];
						tmp >>= bitsright;
						tmp = (byte)(tmp & maskr);
						ret[b] = (byte)(ret[b] | tmp);
					}
				}
			}
		}
		
		// calculate checksum & place it in the CAN message
		byte csum = calcChecksum(ret);
		
		// clear the first 12 bits
		ret[0] = (byte)(ret[0] & ((byte)0b0000_0000));
		ret[1] = (byte)(ret[1] & ((byte)0b0000_1111));
		
		// set them to the value of the checksum
		ret[0] = (byte)(ret[0] | csum);
		// TODO
		// set the first 4 bits of ret[1] to the counter (C)
//		ret[1] = (byte)(ret[1] | csum[1]);
		
		return ret;
	}
	
	/**
	 * TODO
	 * get algorithm from Igor
	 * @return
	 */
	private static byte calcChecksum(byte[] b){
		/*
		 * Original-Algorithmus von Infineon
		 * 
		 * INLINE uint8 CAN_MsgChecksum(const uint8* Data) {
		 * 	uint8 Result;
		 * 	Result = Data[1] ^ Data[2] ^ Data[3] ^ Data[4] ^ Data[5] ^ Data[6] ^ Data[7] ^ 0x00u;
		 * 	return Result;
		 * }
		 * 
		 */
		
		byte csum = 0;
		byte mask = 0x1;
		for(int i=1; i<CommandToCAN.bpb; i++){
			mask <<= 1;
			mask = (byte)(mask | 0x1);
		}
		
		csum = (byte)0;
		
		for(int i=0; i<b.length; i++){
			csum ^= ((byte) b[i]);
		}
		
		return csum;
	}
}
