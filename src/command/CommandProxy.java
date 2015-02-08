package command;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import etc.FixPoint;
import peak.can.basic.PeakCanHandler;
import peak.can.basic.TPCANBaudrate;
import peak.can.basic.TPCANHandle;

/**
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class CommandProxy {
	
	private static CommandProxy instance;
	private CommandCenter center;
	
	/**
	 * Creates a new CommandProxy.
	 * The new CommandProxy will create a new Connection for the CanHandler.
	 */
	private CommandProxy(){
		try {
			center = new CommandCenter(new PeakCanHandler(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M));
		} catch (IOException e) {
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Unable to open file");
			e.printStackTrace();
		} catch (SAXException e) {
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Unable to open SAX");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Parser config failed");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the current instance of the CommandProxy for this program
	 * @return the CommandProxy
	 */
	public static CommandProxy getInstance(){
		if(instance == null){
			instance = new CommandProxy();
		}
		return instance;
	}
	
	/**
	 * Sends the command towards the command center.
	 * 
	 * @param cmd The command's name
	 * @param params A HashMap containing the parameter names as keys and the corresponding values as... values
	 * @return
	 */
	public boolean sendCommand(String cmd, HashMap<String, FixPoint> params){
		if(cmd == null){
			cmd = "";
		}
		if(params == null){
			params = new HashMap<String,FixPoint>();
		}
		
		return center.sendCommand(cmd, params);
	}

}
