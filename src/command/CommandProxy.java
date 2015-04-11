package command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import etc.FixPoint;
import etc.LogCenter;
import peak.can.basic.PeakCanHandler;
import peak.can.basic.TPCANBaudrate;
import peak.can.basic.TPCANHandle;

/**
 * VersionHistory:
 * V1.1: now usable with different baud-rates
 * 
 * 
 * @author Iorgreths
 * @version 1.1
 *
 */

//TODO
/*
 * edit sending commands:
 * instead of one-time send, send the command in an intervall of 10ms
 * instead of .sendCommand -> .changeCommand
 * The CommandCenter is only allowed to send commands, if one is set
 * 
 * ->
 * commandProxy has to delegate to the message-listener too
 * add message-listener methods:
 * add change messageListener-handler to changeBaudRate
 * add registerToListener
 * add unregisterToListener
 */

public class CommandProxy {
	
	private static CommandProxy instance;
	private CommandCenter center;
	private static TPCANBaudrate baud = TPCANBaudrate.PCAN_BAUD_1M;
	private static TPCANHandle handler = TPCANHandle.PCAN_USBBUS1;
	
	/**
	 * Creates a new CommandProxy.
	 * The new CommandProxy will create a new Connection for the CanHandler.
	 */
	private CommandProxy(){
		try {
			center = new CommandCenter(new PeakCanHandler(handler, baud));
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
	 * Changes the baud-rate to the baud-rate indicated by "rate".
	 * Note that this will not take immediate effect. For it to take
	 * effect the "reconnect" command has to be called once.
	 * </br>
	 * Usable Baud-Rates:
	 * <ul>
	 * <li> 1M </li>
	 * <li> 125K </li>
	 * <li> 100K </li>
	 * <li> 10K </li>
	 * <li> 20K </li>
	 * <li> 250K </li>
	 * <li> 33K </li>
	 * <li> 47K </li>
	 * <li> 50K </li>
	 * <li> 500K </li>
	 * <li> 5K </li>
	 * <li> 800K </li>
	 * <li> 83K </li>
	 * <li> 95K </li>
	 * </ul>
	 * 
	 * @param rate the new baud-rate. (usable rates are listed above)
	 */
	public void changeBaudRate(String rate){
		
		String baudrate = "PCAN_BAUD_" + rate;
		baud = TPCANBaudrate.valueOf(baudrate);
		/*
		switch(rate){
		case "1M":
			baud = TPCANBaudrate.PCAN_BAUD_1M;
			break;
		case "125K":
			baud = TPCANBaudrate.PCAN_BAUD_125K;
			break;
		case "100K":
			baud = TPCANBaudrate.PCAN_BAUD_100K;
			break;
		case "10K":
			baud = TPCANBaudrate.PCAN_BAUD_10K;
			break;
		case "20K":
			baud = TPCANBaudrate.PCAN_BAUD_20K;
			break;
		case "250K":
			baud = TPCANBaudrate.PCAN_BAUD_250K;
			break;
		case "33K":
			baud = TPCANBaudrate.PCAN_BAUD_33K;
			break;
		case "47K":
			baud = TPCANBaudrate.PCAN_BAUD_47K;
			break;
		case "50K":
			baud = TPCANBaudrate.PCAN_BAUD_50K;
			break;
		case "500K":
			baud = TPCANBaudrate.PCAN_BAUD_500K;
			break;
		case "5K":
			baud = TPCANBaudrate.PCAN_BAUD_5K;
			break;
		case "800K":
			baud = TPCANBaudrate.PCAN_BAUD_800K;
			break;
		case "83K":
			baud = TPCANBaudrate.PCAN_BAUD_83K;
			break;
		case "95K":
			baud = TPCANBaudrate.PCAN_BAUD_95K;
			break;
		default:
			baud = TPCANBaudrate.PCAN_BAUD_1M;
			break;
		}
		*/
		this.reconnect();
		
	}
	
	public void changeChannelAndBaudRate(String rate, String channel){
		
		channel = "PCAN_USBBUS" + channel;
		handler = TPCANHandle.valueOf(channel);
		this.changeBaudRate(rate);
		
	}
	
	/**
	 * This command should only be used after the "changeBaudRate" command.
	 * This will create a new CommandProxy instance for the wished baud-rate.
	 */
	private void reconnect(){
		center.setCANHandler(new PeakCanHandler(handler, baud));
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
		Logger l = LogCenter.getInstance().getLogger();
		l.log(Level.INFO, "Sending command: "+cmd);
		for(Entry<String, FixPoint> e : params.entrySet()){
			l.log(Level.INFO, "with parameter: "+e.getKey()+"-"+e.getValue().toString());
		}
		return center.sendCommand(cmd, params);
	}

}
