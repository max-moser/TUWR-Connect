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
	public byte[] returnFormatted(){
		// TODO
		return new byte[2];
//		return this.number.byteValue();
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		
		buf.append(number.toString());
		
		return buf.toString();
	}
}
