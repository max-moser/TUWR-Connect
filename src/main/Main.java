package main;

import java.io.IOException;
import java.math.BigDecimal;
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
import command.CommandToCAN;
import data.DataCenter;
import etc.FixPoint;
import etc.XMLParser;

public class Main {

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException{

		boolean quit = true;

		try{
			CommandHandler handler = new CommandHandler();
			XMLParser xml = new XMLParser(handler);
			xml.parse("command.xml");
			List<Command> res = handler.getResult();
			//			for(Command c: res){
			//				System.out.println(c);
			//			}
			//			Command c = res.get(0);
			//			c.replaceParameter("modl", new FixPoint("1.0"));
			//			c.replaceParameter("modr", new FixPoint("1.0"));
			//			System.out.println(c.getParameter("modl"));
			//			System.out.println(c.getParameter("modr"));
			//			System.out.println(c.getParameter("right"));
			//			byte[] data = CommandToCAN.getData(c);
			//			
			//			for(byte b: data){
			//				System.out.print(Integer.toBinaryString(b) + "|");
			//			}
			//			System.out.println();
			FixPoint fp = new FixPoint("1.25");
			System.out.println(bytesToString(fp.getFormatted(8, 16)));
			
		}catch(Exception e){
			e.printStackTrace();
		}

		if(quit){
			System.exit(0);
		}


		PeakCanHandler canHandler = new PeakCanHandler(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M);
		try {
			CommandCenter cc = new CommandCenter(canHandler);
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
		DataCenter dc = DataCenter.getInstance();
		try {
			dc.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String bytesToString(byte[] bs){
		String str = "";
		for(byte b: bs){
			str += byteToString(b) + " ";
		}
		return str;
	}

	private static String byteToString(byte b){
		// trimbyte: byte used for trimming
		byte trimbyte = 1;
		for(int i=0; i<Byte.SIZE; i++){
			trimbyte <<= 1;
			trimbyte = (byte)(trimbyte | 1);
		}
		
		int bi = b;
		bi = bi & trimbyte;

		String bStr = Integer.toBinaryString(bi);
		String str = "";

		for(int i=0; i<(Byte.SIZE - bStr.length()); i++){
			str += "0";
		}
		str += bStr;

		return str;
	}
}
