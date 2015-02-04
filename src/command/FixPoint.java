package command;

public class FixPoint {

	private int predec;
	private int postdec;
	
	public FixPoint(int predec, int postdec) {
		this.predec = predec;
		this.postdec = postdec;
	}
	
	/**
	 * Returns two bytes formated towards (16-fixpoint) 
	 * pre-decimal points and fixpoint post-decimal points.
	 * 
	 * @param fixpoint number of post-decimal points
	 * @return an array of 2 formatted bytes
	 */
	public byte[] returnFormated(int fixpoint){
		//TODO
		return null;
	}

}
