package etc;

import java.math.BigDecimal;

/**
 * A storage class for fixed point values, as opposed to the primitive floating point values.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public class FixPoint {

	private final BigDecimal number;

	public FixPoint(String number){
		this.number = new BigDecimal(number);
	}

	/**
	 * Returns an array of bytes containing the stored fixed-point value with the specified formatting
	 * and as close an approximation of the fractional value as possible with the specified amount
	 * of bits.
	 * 
	 * Each byte will contain a portion of the value, with the highest-value bit in the first byte
	 * having the overall highest value and the lowest-value bit in the last byte having the overall
	 * lowest value.
	 * 
	 * The fixpoint parameter determines the position where the fractional part should start.
	 * E.g. a fixpoint of 2 will indidcate that there should be 2 bits for the integral part of the number
	 * 	before the start of the fractional part.
	 * 
	 * The fixpoint parameter can also be set to -1, which will result in not calculating a fractional part
	 * at all and instead returning an integer value with the specified amount of bits.
	 * 
	 * Hint:
	 * If the length is not a multiple of the amount of bits in a byte (usually 8),
	 * the bits in the last byte will be left-aligned, with zeroes as padding to the right.
	 * 
	 * The following inequations must be fulfilled in order for sensible results to emerge:
	 * length > 0
	 * length >= fixpoint
	 * fixpoint >= 0 --OR-- fixpoint == -1, if disabled
	 * 
	 * Warning:
	 * The current implementation will not work for values consuming more bits than available in the
	 * primitive long datatype.
	 * 
	 * @return An array of bytes, containing the fixed-point value with the specified formatting.
	 */
	public byte[] getFormatted(int fixpoint, int length){
		double dblVal, exp, tmpRes;
		long preDec, postDec;
		int preDecimalLength, postDecimalLength, padding, len, bpb;
		byte[] result;
		boolean[] exps;

		boolean fixpointWork = (fixpoint >= 0);

		bpb = Byte.SIZE; // bpb: bits per byte (should be 8)
		len = ((length - 1) / bpb) + 1;
		result = new byte[len];
		for(int i=0; i<result.length; i++){
			result[i] = 0;
		}
		padding = (len * bpb) - length;

		// initialisations in case that fixpoint == -1
		preDecimalLength = length;
		postDecimalLength = 0;
		exps = new boolean[postDecimalLength]; // dummy init.

		if(fixpointWork){
			preDecimalLength = fixpoint;
			postDecimalLength = length - preDecimalLength;

			// inits
			exps = new boolean[postDecimalLength];
			for(int i=0; i<postDecimalLength; i++){
				exps[i] = false;
			}

			tmpRes = 0;
			dblVal = this.number.doubleValue();
			dblVal = (dblVal - ((int)dblVal));
			// dblVal now only contains the fractional part of the number

			// determine the needed exponents via first-fit heuristics
			// the result should be:
			// resulting value <= true value
			// with the best approximation possible with the given amount of bits
			for(int i=0; i<postDecimalLength; i++){
				exp = Math.pow(2, ((-1) * (i + 1)));
				if((exp + tmpRes) <= dblVal){
					tmpRes += exp;
					exps[i] = true;
				}
			}
		}

		// filling in the results
		preDec = this.number.longValue();
		postDec = 0;

		for(int i=0; i<postDecimalLength; i++){
			postDec <<= 1;
			if(exps[i]){
				postDec = postDec | 0x1;
			}
		}

		// taking padding on the right into account
		if(fixpointWork){
			for(int i=0; i<padding; i++){
				postDec <<= 1;
			}
		}else{
			for(int i=0; i<padding; i++){
				preDec <<= 1;
			}
		}

		// creating the final result
		// fixpointbyte: the byte in which the fixpoint is
		// fixinbytepre: the amount of bits in the fixpointbyte before the fixpoint
		// fixinbytepost: the amount of bits in the fixpointbyte after the fixpoint
		// bytedist: the amount of bytes between the current byte and the fixpointbyte
		int fixpointbyte, fixinbytepre, fixinbytepost, bytedist, shiftdist;
		long tmp;

		// trimbyte: byte used for trimming
		byte trimbyte = 1;
		for(int i=0; i<Byte.SIZE; i++){
			trimbyte <<= 1;
			trimbyte = (byte)(trimbyte | 1);
		}

		fixpointbyte = (len - 1);
		fixinbytepre = bpb;
		fixinbytepost = 0;

		if(fixpointWork){
			fixpointbyte = fixpoint / bpb;
			fixinbytepre = fixpoint % bpb;
			fixinbytepost = bpb - fixinbytepre;
		}

		// do the pre decimal stuff
		for(int i=0; i<=fixpointbyte; i++){
			bytedist = (fixpointbyte - i);
			shiftdist = ((bytedist * bpb) - fixinbytepost);

			if(shiftdist > 0){
				tmp = (preDec >> shiftdist);
			}else{
				shiftdist = (-1) * shiftdist;
				tmp = (preDec << shiftdist);
			}

			tmp = (tmp & trimbyte);
			result[i] = (byte)(result[i] | ((byte)tmp));
		}

		if(fixpointWork){
			// do the post decimal stuff
			for(int i=fixpointbyte; i<len; i++){
				bytedist = ((len - 1 ) - i);
				shiftdist = (bytedist * bpb);

				if(shiftdist > 0){
					tmp = (postDec >> shiftdist);
				}else{
					shiftdist = (-1) * shiftdist;
					tmp = (postDec << shiftdist);
				}

				tmp = (tmp & trimbyte);
				result[i] = (byte)(result[i] | ((byte)tmp));
			}
		}

		return result;
	}

	@Override
	public boolean equals(Object other){
		if(other == null){
			return false;
		}else if(other.getClass() != this.getClass()){
			return false;
		}
		
		FixPoint o = (FixPoint) other;
		
		return this.number.equals(o.number);
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();

		buf.append(number.toString());

		return buf.toString();
	}
}
