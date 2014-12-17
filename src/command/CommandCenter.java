package command;

public class CommandCenter {

	public final static byte ID_ZERO = 0;
	
	private Mode mode;
	
	public CommandCenter(Mode mode){
		this.mode = mode;
	}
	
	public boolean sendData(byte id, byte[] data){
		return mode.sendData(id, data);
	}
	
}
