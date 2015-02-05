package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import peak.can.basic.PeakCanHandler;
import peak.can.basic.TPCANBaudrate;
import peak.can.basic.TPCANHandle;

import command.Command;
import command.CommandCenter;
import command.CommandHandler;

import data.DataCenter;
import etc.XMLParser;

public class Main {

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException{

		boolean quit = true;

		try{
			CommandHandler handler = new CommandHandler();
			XMLParser xml = new XMLParser(handler);
			xml.parse("command.xml");
			List<Command> res = handler.getResult();
			for(Command c: res){
				System.out.println(c);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		if(quit){
			System.exit(0);
		}


		PeakCanHandler canHandler = new PeakCanHandler(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M);
		CommandCenter cc = new CommandCenter(canHandler);
		DataCenter dc = DataCenter.getInstance();
		try {
			dc.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
