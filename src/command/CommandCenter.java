package command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import peak.can.basic.PeakCanHandler;
import etc.FixPoint;
import etc.XMLParser;

/**
 * The main hub for sending and receiving commands and notifications over CAN.
 * 
 * Sending commands:
 * TODO
 * 
 * Receiving notifications:
 * TODO
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public class CommandCenter {

	// predefined IDs, according to the TUWR Intranet
	public final static int ROTATION_MODE = 1, TORQUE_MODE = 0;
	
	private Mode activeMode;
	private final RotationMode rotMode;
	private final TorqueMode torMode;
	private final List<Command> commands;
	
	/**
	 * Creates a new CommandCenter with the specified CanHandler.
	 * The CommandCenter uses the CanHandler to write Data to the CAN bus.
	 * 
	 * On creation, all of the commands contained in the file "command.xml"
	 * will be parsed and stored as template commands.
	 * 
	 * @param canHandler
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public CommandCenter(PeakCanHandler canHandler) throws IOException, SAXException, ParserConfigurationException{
		CommandHandler c = new CommandHandler();
		
		new XMLParser(c).parse("command.xml"); // TODO absolute install path
		this.commands = c.getResult();
		
		this.rotMode = new RotationMode(canHandler);
		this.torMode = new TorqueMode(canHandler);
		this.activeMode = rotMode;
	}
	
	/**
	 * Checks if the specified command has been 
	 * 
	 * Neither cmd nor params may be null.
	 * 
	 * @param cmd The command's name
	 * @param params A HashMap containing the parameter names as keys and the corresponding values as... values
	 * @return
	 */
	public boolean executeCommand(String cmd, HashMap<String, FixPoint> params){
		// look for the command
		Command command = null;
		for(Command c: this.commands){
			if(c.getName().equalsIgnoreCase(cmd)){
				command = c;
				break;
			}
		}
		
		if(command == null){
			return false;
		}
		
		// clone the template command
		// and set its parameters
		Command exec = new Command(command);
		List<Parameter> parameters = exec.getParameters();
		
		for(Parameter p: parameters){
			String pName = p.getName();
			// if a parameter could not be found, its default value will be used
			if(params.get(pName) != null){
				exec.replaceParameter(pName, params.get(pName));
			}
		}
		
		// convert the command to CAN
		// and execute it
		byte id = CommandToCAN.getID(exec);
		byte[] data = CommandToCAN.getData(exec);
		
		return this.sendData(id, data);
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
