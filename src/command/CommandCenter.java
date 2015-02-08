package command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import peak.can.basic.PeakCanHandler;
import etc.CANObservable;
import etc.CANObserver;
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
 * @version 1.1
 *
 */
public class CommandCenter implements CANObservable{

	// predefined IDs, according to the TUWR Intranet
	public final static int ROTATION_MODE = 1, TORQUE_MODE = 0;
	
	private Mode activeMode;
	private final RotationMode rotMode;
	private final TorqueMode torMode;
	private final List<Command> commands;
	private final MessageListener listen;
	
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
		this.listen = new MessageListener(canHandler);
		
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
	public boolean sendCommand(String cmd, HashMap<String, FixPoint> params){
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
		
		return this.activeMode.sendCommand(exec);
	}
	
	/**
	 * Sends the specified data without any performing any validation first.
	 * 
	 * @param id The ID for the CAN message to send
	 * @param data The data of the CAN message to send
	 * @return TRUE, if the transmission was successful, FALSE otherwise
	 */
	public boolean sendData(byte id, byte[] data){
		return activeMode.sendData(id, data);
	}
	
	/**
	 * Changes the active mode of the Command Center to either the Rotation Mode or Torque Mode,
	 * depending on the specified parameter.
	 * 
	 * If the specified parameter is neither CommandCenter.ROTATION_MODE or CommandCenter.TORQUE_MODE,
	 * FALSE will be returned.
	 * 
	 * @param mode One of the constants CommandCenter.ROTATION_MODE or CommandCenter.TORQUE_MODE
	 * @return TRUE, if the switch was successful, FALSE otherwise
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
	
	public void setCANHandler(PeakCanHandler handle){
		this.rotMode.setCANHandler(handle);
		this.torMode.setCANHandler(handle);
		this.listen.setCANHandler(handle);
	}
	
	@Override
	public void registerObserver(CANObserver o) {
		this.listen.registerObserver(o);
	}

	@Override
	public void unregisterObserver(CANObserver o) {
		this.listen.unregisterObserver(o);
	}
	
}
