package command;

import peak.can.basic.PeakCanHandler;

public class CommandCenter {

	public final static byte ID_ZERO = 0;
	
	private Mode mode;
	private final PeakCanHandler canHandler;
	
	public CommandCenter(PeakCanHandler canHandler){
		this();
	}
	
	public CommandCenter(Mode mode, PeakCanHandler canHandler){
		this.mode = mode;
		this.canHandler = canHandler;
	}
	
	public boolean sendData(byte id, byte[] data){
		return mode.sendData(id, data);
	}
	
}
