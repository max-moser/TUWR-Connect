package etc;

/**
 * An Observable object that notifies all of its registered Observers
 * in case of an event (a received CAN message meeting specific criteria).
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public interface CANObservable {

	/**
	 * Adds the specified CANObserver to the list of observers to notify
	 * in case of an event.
	 * 
	 * @param o The Observer to add.
	 */
	public void registerObserver(CANObserver o);
	
	/**
	 * Removes the specified CANObserver from the list of observers to notify
	 * in case of an event.
	 * 
	 * @param o The Observer to remove.
	 */
	public void unregisterObserver(CANObserver o);
}
