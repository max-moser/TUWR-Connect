package command;

import peak.can.basic.PeakCanHandler;

public interface Mode {

	/**
	 * Sends Data to 
	 * @return
	 */
	public boolean sendData(byte id, byte[] data);
	
	/**
	 * TODO
	 */
	public void setCanHandler(PeakCanHandler canHandler);	
}
