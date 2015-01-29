package command;

import peak.can.basic.PeakCanHandler;

public class CommandCenter {

	// predefined IDs, according to the TUWR Intranet
	public final static byte ID_ZERO = 0;
	public final static int ROTATION_MODE = 0, TORQUE_MODE = 1;
	
	private Mode activeMode;
	private final RotationMode rotMode;
	private final TorqueMode torMode;
	
	/**
	 * Creates a new CommandCenter with the specified CanHandler.
	 * The CommandCenter uses the CanHandler to write Data to the CAN bus.
	 * 
	 * @param canHandler
	 */
	public CommandCenter(PeakCanHandler canHandler){
		this.rotMode = new RotationMode(canHandler);
		this.torMode = new TorqueMode(canHandler);
		this.activeMode = rotMode;
	}
	
	/**
	 * TODO
	 * @param id
	 * @param data
	 * @return
	 */
	public boolean sendData(byte id, byte[] data){
		
		if(activeMode != rotMode && activeMode != torMode){
			assert(false);
			return false;
		}
		
		// the active Mode will check against validity of 
		return activeMode.sendData(id, data);
		
//		if(activeMode == rotMode){
//			
//			// TODO:
//			// filter for ID (only rotation stuff)
//			return activeMode.sendData(id, data);
//		}else if(activeMode == torMode){
//			
//			// TODO:
//			// filter for ID (only torque stuff)
//			return activeMode.sendData(id, data);
//		}
	}
	
	/**
	 * TODO
	 * @param mode One of the constants CommandCenter.ROTATION_MODE or CommandCenter.TORQUE_MODE
	 * @return
	 */
	public boolean changeMode(int mode){
		if(mode == CommandCenter.TORQUE_MODE){
			this.activeMode = torMode;
			return true;
		}else if(mode == CommandCenter.ROTATION_MODE){
			this.activeMode = rotMode;
			return true;
		}else{
			return false;
		}
	}
	
}
