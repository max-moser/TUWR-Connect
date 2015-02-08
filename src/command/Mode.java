package command;

import java.util.List;

import peak.can.basic.PeakCanHandler;

/**
 * A template for the modes used in the CommandCenter.
 * 
 * Each mode must have a PeakCanHandler, in order to send data over CAN.
 * The mode's purpose is to validate data and subsequently send it over CAN.
 * 
 * @author Maxmanski
 * @version 1.1
 *
 */
public abstract class Mode {

	protected PeakCanHandler canHandler;
	
	public Mode(PeakCanHandler handler){
		this.canHandler = handler;
	}
	
	/**
	 * Sends the specified message with the specified ID without performing any checks on it
	 * before.
	 * 
	 * @return TRUE if the transmission of the data was successful; FALSE otherwise
	 */
	public boolean sendData(byte id, byte[] data){
		return this.canHandler.write((int)id, (byte)data.length, (byte)0, data);
	}
	
	/**
	 * Sets the CAN Handler (PEAK CAN) which will be used for the transmission of data.
	 * 
	 * The CAN Handler must not be NULL.
	 */
	public void setCANHandler(PeakCanHandler canHandler){
		this.canHandler = canHandler;
	}
	
	/**
	 * Checks the specified command against compliance with the Mode and if the check is positive,
	 * the Command will be parsed to a CAN message and sent.
	 * 
	 * If the check against compliance is negative, no transmission attempt will be made and FALSE
	 * will be returned.
	 * 
	 * @param c The command to check and possibly send
	 * @return TRUE, if the message is compliant and the transmission successful, FALSE otherwise
	 */
	public boolean sendCommand(Command c){
		if(!this.checkID(c.getID())){
			return false;
		}
		
		if(!this.checkParameters(c.getParameters())){
			return false;
		}
		
		// convert the command to CAN
		// and execute it
		byte id = CommandToCAN.getID(c);
		byte[] data = CommandToCAN.getData(c);
		
		return this.sendData(id, data);
	}
	
	/**
	 * Checks the command's ID against compliance with the Mode.
	 * This means that certain IDs are not allowed in some modes.
	 * 
	 * @param id The ID to check
	 * @return TRUE, if the ID is allowed in the mode; FALSE otherwise
	 */
	protected abstract boolean checkID(int id);
	
	/**
	 * Checks the command's parameters against compliance with the Mode.
	 * This means that some parameters must not have certain values in some modes.
	 * 
	 * @param params The parameters to check
	 * @return TRUE, if all parameters are compliant with the mode; FALSE otherwise
	 */
	protected abstract boolean checkParameters(List<Parameter> params);
}
