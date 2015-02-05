package command;

import java.util.List;

import etc.FixPoint;

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
		
		for(int i=0; i<8; i++){
			ret[i] = 0;
		}
		
		List<Parameter> params = c.getParameters();
		
		for(Parameter p: params){
			int offset = p.getOffset();
			int length = p.getLength();
			int fixpoint = p.getFixpoint();
			FixPoint value = p.getValue();
			
			
		}
		
		// TODO
		
		return ret;
	}
}
