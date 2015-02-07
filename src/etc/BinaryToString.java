package etc;

/**
 * 
 * A class for the proper representation of numeric integral values as binary Strings with leading zeroes.
 * 
 * @author Maxmanski
 *
 */
public class BinaryToString {

	/**
	 * Bits per Byte. Should be 8. Just in case.
	 */
	private static final int bpb = Byte.SIZE;
	
	/**
	 * Returns a binary String representation of the specified byte, including leading zeroes.
	 * 
	 * @param b The byte to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String byteToString(Byte b){
		return xToString(b, Byte.BYTES);
	}
	
	/**
	 * Returns a binary String representation of the specified bytes, including leading zeroes.
	 * The values' representations are separated with a colon.
	 * 
	 * @param b The bytes to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String bytesToString(byte[] bs){
		String str = "";
		
		for(int i=0; i<bs.length; i++){
			str += byteToString(bs[i]) + ((i == (bs.length - 1)) ? "" : (", "));
		}
		
		return str;
	}
	
	/**
	 * Returns a binary String representation of the specified short value, including leading zeroes.
	 * 
	 * @param b The short value to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String shortToString(Short s){
		return xToString(s, Short.BYTES);
	}
	
	/**
	 * Returns a binary String representation of the specified short values, including leading zeroes.
	 * The values' representations are separated with a colon.
	 * 
	 * @param b The short values to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String shortsToString(short[] ss){
		String str = "";
		
		for(int i=0; i<ss.length; i++){
			str += shortToString(ss[i]) + ((i == (ss.length - 1)) ? "" : (", "));
		}
		
		return str;
	}
	
	/**
	 * Returns a binary String representation of the specified integer, including leading zeroes.
	 * 
	 * @param b The integer to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String intToString(Integer i){
		return xToString(i, Integer.BYTES);
	}
	
	/**
	 * Returns a binary String representation of the specified integers, including leading zeroes.
	 * The values' representations are separated with a colon.
	 * 
	 * @param b The integers to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String intsToString(int[] is){
		String str = "";
		
		for(int i=0; i<is.length; i++){
			str += intToString(is[i]) + ((i == (is.length - 1)) ? "" : (", "));
		}
		
		return str;
	}
	
	/**
	 * Returns a binary String representation of the specified long value, including leading zeroes.
	 * 
	 * @param b The long value to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String longToString(Long l){
		return xToString(l, Long.BYTES);
	}

	/**
	 * Returns a binary String representation of the specified long values, including leading zeroes.
	 * The values' representations are separated with a colon.
	 * 
	 * @param b The long values to represent as String
	 * @return The String representation of the given parameter
	 */
	public static String longsToString(long[] ls){
		String str = "";
		
		for(int i=0; i<ls.length; i++){
			str += longToString(ls[i]) + ((i == (ls.length - 1)) ? "" : (", "));
		}
		
		return str;
	}

	/**
	 * Returns a binary String representation of the specified byte, including leading zeroes.
	 * 
	 * @param n The number to represent as String
	 * @param bytes The amount of bytes making up the number
	 * @return The String representation of the number, using the specified amount of bytes
	 */
	private static String xToString(Number n, int bytes){
		String str = "", tmpStr = "";
		long val = n.longValue(), tmpVal, minus, minussr1, mask; //minussr1 = minus shift right (1)
		int stop;
		
		// handling for negative values
		minus = 0x1;
		minussr1 = 0x1;
		mask = 0x1;
		for(int i=1; i<(bytes * BinaryToString.bpb - 1); i++){
			minussr1 <<= 1;
			mask = ((mask << 1) | 0x1);
		}
		minus = (minussr1 << 1);
		mask = ((mask << 1) | 0x1);

		tmpVal = (val & mask);
		if((minus & tmpVal) != 0){
			if((tmpVal & 0x1) == 0){
				str += "0";
			}else{
				str += "1";
			}
			tmpVal = tmpVal ^ minus;
			tmpVal >>= 1;
			tmpVal = (tmpVal | minussr1);
		}
		// end of handling for negative values
		
		while(tmpVal != 0){
			if((tmpVal & 0x1) == 0){
				str += "0";
			}else{
				str += "1";
			}
			tmpVal >>= 1;
		}
		
		
		str = new StringBuilder(str).reverse().toString();
		
		stop = ((bytes * BinaryToString.bpb) - str.length());
		for(int i=0; i<stop; i++){
			str = "0" + str;
		}
		
		for(int i=0; i<str.length(); i++){
			if((i % 8) == 0){
				tmpStr += " ";
			}
			tmpStr += str.charAt(i);
		}
		
		str = tmpStr.trim();
		
		return str;
	}
}
