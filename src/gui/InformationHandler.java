package gui;

import java.util.List;

/**
 * This interface describes a link between message from the CAN towards the output interface.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public interface InformationHandler {
	
	/**
	 * Notifies the information handler about transpired errors. Each error is represented
	 * by a string in the list. Furthermore the handler has to be told if the error has
	 * happened on the left (default) motor.
	 * 
	 * @param errors list of errors
	 * @param left happened the error on the left motor?
	 */
	public void notifyError(List<String> errors, boolean left);
	
	/**
	 * Notifies the information handler about transpired actions. Each action is represented
	 * by a string in the list. Furthermore the handler has to be told if the action has
	 * happened on the left (default) motor.
	 * 
	 * @param msg list of messages
	 * @param left happened the action on the left motor?
	 */
	public void notifyConsole(List<String> msg, boolean left);
	
	/**
	 * Notifies the information handler about the actual values. These values are the torque
	 * and rotation speed of the motor. Furthermore the handler has to be told if this
	 * is from the left motor.
	 * 
	 * @param torque the torque of the motor
	 * @param rotation the rotation speed of the motor
	 * @param left from the left motor?
	 */
	public void notifyActual(double torque, double rotation, boolean left);
	
	/**
	 * Notifies the information handler about the actual current values. These values are the id and iq
	 * currents. Furthermore the handler has to be told if this is from the left motor.
	 * 
	 * @param id id-current
	 * @param iq iq-current
	 * @param left from the left motor?
	 */
	public void notityCurrent(double id, double iq, boolean left);
	
}
