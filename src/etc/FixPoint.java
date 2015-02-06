package etc;

import java.math.BigDecimal;

public class FixPoint {

	private final BigDecimal number;

	public FixPoint(String number){
		this.number = new BigDecimal(number);
	}

	/**
	 * TODO description
	 * 
	 * The following 
	 * length > 0
	 * length >= fixpoint
	 * fixpoint >= 0 --OR-- fixpoint == -1, if disabled
	 * 
	 * @return
	 */
	public byte[] getFormatted(int fixpoint, int length){
		double dblVal, exp, tmpRes;
		long preDec, postDec;
		int preDecimalLength, postDecimalLength, len, bpb;
		byte[] result;
		boolean[] exps;
		
		boolean fixpointWork = (fixpoint >= 0);

		bpb = Byte.SIZE; // bpb: bits per byte (should be 8)
		len = ((length - 1) / bpb) + 1;
		result = new byte[len];
		for(int i=0; i<result.length; i++){
			result[i] = 0;
		}
		
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

	public String toString(){
		StringBuffer buf = new StringBuffer();

		buf.append(number.toString());

		return buf.toString();
	}
}
