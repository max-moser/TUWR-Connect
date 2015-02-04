package command;

import peak.can.basic.PeakCanHandler;

public interface Mode {

	/**
	 * Checks if the ID of the data is valid for the Mode.
	 * If it is, the data will be transmitted and a boolean value depending
	 * on the success of the transmission will be returned.
	 * If the ID is invalid though, no attempt of transmission will be made
	 * and FALSE will be returned.
	 * 
	 * @return TRUE if the ID was valid and the data transmitted successfully; FALSE otherwise
	 */
	public boolean sendData(byte id, byte[] data);
	
	/**
	 * Sets the CAN Handler (PEAK CAN) which will be used for the transmission of data.
	 * 
	 * The CAN Handler must not be a Nullpointer.
	 */
	public void setCanHandler(PeakCanHandler canHandler);
	
	/**
	 * Checks the specified ID against validity in the Mode and returns a corresponding boolean value.
	 * 
	 * @param id The ID to check against validity for the current mode
	 * @return TRUE, if the specified ID is valid for the mode; FALSE otherwise
	 */
	public boolean isValidId(int id);
	
	/**
	 * Checks the specified message against validity in the Mode and returns a corresponding boolean value.
	 * 
	 * @param msg The message to check
	 * @return TRUE, if the specified message is valid for the mode and FALSE otherwise
	 */
	public boolean isValidMessage(byte[] msg);
}
