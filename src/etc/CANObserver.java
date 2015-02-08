package etc;

import java.util.List;

/**
 * An Observer which will be notified whenever a specific CAN message was received.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public interface CANObserver {

	/**
	 * Notification of the Observer when message containing error messages
	 * was received.
	 * 
	 * The parameter modL describes whether the message concerns the left
	 * motor or not.
	 */
	public void notifyError(List<String> errors, boolean modL);
	
	/**
	 * Notifies the observer about an incoming message containing the
	 * specified messages.
	 * 
	 * The parameter modL describes whether the message concerns the left
	 * motor or not.
	 */
	public void notifyConsole(List<String> msg, boolean modL);
	
	/**
	 * Notifies the observer about an incoming message concerning the current
	 * torque and rotational speed.
	 * 
	 * The parameter modL describes whether the message concerns the left
	 * motor or not.
	 */
	public void notifyActual(double torque, double rotation, boolean modL);
	
	/**
	 * Notifies the observer about an incoming message concerning the current
	 * electric currents "id" and "iq".
	 * 
	 * The parameter modL describes whether the message concerns the left
	 * motor or not.
	 */
	public void notifyCurrent(double id, double iq, boolean modL);
}
