package etc;

import java.math.BigDecimal;

public class FixPoint {

	private final BigDecimal number;

	public FixPoint(String number){
		this.number = new BigDecimal(number);
	}

	/**
	 * 
	 * @return
	 */
	public byte[] getFormatted(int fixpoint, int length){
		byte[] ret = new byte[8]; // TODO
		for(int i=0; i<ret.length; i++){
			ret[i] = 0;
		}

		// TODO

		ret[0] = number.byteValue();

		if(ret[0] > 0){
			while(((~ret[0]) & 0b1000_0000) > 0){
				ret[0] <<= 1;
			}
		}

		ret[1] = 0;

		return ret;
	}

	public String toString(){
		StringBuffer buf = new StringBuffer();

		buf.append(number.toString());

		return buf.toString();
	}
}
