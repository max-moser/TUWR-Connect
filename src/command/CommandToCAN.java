package command;

public class CommandToCAN {

	/**
	 * Extracts the command's CAN ID
	 * 
	 * @param c The command to extract the ID from
	 * @return A byte containing the CAN ID of the command
	 */
	public static byte getID(Command c){
		return (byte)c.getID();
	}
	
	/**
	 * Converts the command with its parameters into a CAN message
	 * with a length of 8 (bytes).
	 * 
	 * @param c The command to convert
	 * @return A Byte array of length 8
	 */
	public static byte[] getData(Command c){
		byte[] ret = new byte[8];
		
		// TODO
		
		return ret;
	}
}
