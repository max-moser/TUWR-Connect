package command;

public interface Mode {

	/**
	 * Sends Data to 
	 * @return
	 */
	public boolean sendData(byte id, byte[] data);
	
}
