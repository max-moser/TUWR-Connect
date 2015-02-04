package command;

import java.util.HashMap;

import peak.can.basic.PeakCanHandler;

public class CommandCenter {

	// predefined IDs, according to the TUWR Intranet
	public final static byte ID_ZERO = 0;
	public final static int ROTATION_MODE = 1, TORQUE_MODE = 0;
	
	private Mode activeMode;
	private final RotationMode rotMode;
	private final TorqueMode torMode;
	private final HashMap<String, Command> commands;
	
	/**
	 * Creates a new CommandCenter with the specified CanHandler.
	 * The CommandCenter uses the CanHandler to write Data to the CAN bus.
	 * 
	 * @param canHandler
	 */
	public CommandCenter(PeakCanHandler canHandler){
		this.commands = new HashMap<String, Command>();
		this.rotMode = new RotationMode(canHandler);
		this.torMode = new TorqueMode(canHandler);
		this.activeMode = rotMode;
	}
	
	/**
	 * TODO
	 * @param cmd
	 * @return
	 */
	public boolean executeCommand(String cmd){
		// TODO
		// find out 
		return true;
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
		
		// the active Mode will check against validity of ID and data
		return activeMode.sendData(id, data);
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
