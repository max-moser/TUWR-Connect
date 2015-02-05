package handler;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import peak.can.basic.PeakCanHandler;
import peak.can.basic.TPCANBaudrate;
import peak.can.basic.TPCANHandle;
import command.CommandCenter;

/**
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class StartHandler implements ActionListener {

	private GUI gui;
	
	public StartHandler(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int indicator = gui.adaptStart();
		
		/*
		 * 0 - start the motor</li>
         * 1 - stop the motor</li>
		 */
		
		switch(indicator){
		case 0:
			try {
				new CommandCenter(new PeakCanHandler(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M)).executeCommand("start", null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 1:
			try {
				new CommandCenter(new PeakCanHandler(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M)).executeCommand("stop", null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		default:
			assert(false);
			break;
		}
		
		//TODO send message indicated by indicator

	}

}
