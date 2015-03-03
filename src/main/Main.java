package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import command.CommandHandler;
import command.MessageHandler;
import etc.XMLParser;
import gui.GUI;

public class Main {
	
	/**
	 * 
	 * Before actually starting the program, the main function tries 
	 * opening the needed external files. Should this not be possible
	 * will the program terminate.
	 * 
	 * Error-Codes:
	 * <ul>
	 * <li> (-1): ERR_OPENING_FILES </li>
	 * <li> (-2): ERR_SAX </li>
	 * <li> (-3): ERR_CLOSING </li>
	 * </ul>
	 * 
	 * For further information about the errors, please look into the log records.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(Main.class.getName());
		
		/* LOOK FOR EXTERNAL FILES */
		
		//try opening XML and properties files
		// if this isn't possible -> terminate with error
		CommandHandler handler = new CommandHandler();
		XMLParser xml;
		try {
			xml = new XMLParser(handler);
			xml.parse("command.xml");
		} catch (ParserConfigurationException e) {
			log.log(Level.WARNING, "XML-Parser configuration isn't ok");
		} catch (SAXException e) {
			log.log(Level.SEVERE, "a problem with SAX has occured");
			System.exit(-2);
		} catch (IOException e) {
			log.log(Level.SEVERE, "unable to open XML-File: command.xml");
			System.exit(-1);
		}
		try{
			xml = new XMLParser(new MessageHandler());
			xml.parse("messages.xml");
		} catch (ParserConfigurationException e) {
			log.log(Level.WARNING, "XML-Parser configuration isn't ok");
		} catch (SAXException e) {
			log.log(Level.SEVERE, "a problem with SAX has occured");
			System.exit(-2);
		} catch (IOException e) {
			log.log(Level.SEVERE, "unable to open XML-File: messages.xml");
			System.exit(-1);
		}
		boolean exist = true;
		Properties prop;
		String path = "fnct.config";
		prop = new Properties();
		try {
			prop.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			log.log(Level.WARNING, "no configuration file exists: no file namend 'fnct.config' ");
			exist = false;
		} catch (IOException e) {
			log.log(Level.SEVERE, "unable to open configuration file");
			System.exit(-1);
		}
		
		/* read the properties for the gui and parse them */
		HashMap<String,List<String>> functions = new HashMap<String,List<String>>();
		if(exist){
			/*
			 * NOTE:
			 * no config-file -> do not use any functions
			 */
			int fnctcount;
			try{
				fnctcount = Integer.valueOf(prop.getProperty("number_of_functions"));
			}catch(NumberFormatException e){
				fnctcount = 0;
			}
			
			for( int i=0; i<fnctcount; i++){
				
				String fnctname = prop.getProperty("function"+(i+1));
				String paramstring = prop.getProperty("function"+(i+1)+"_param");
				List<String> paramlist = new LinkedList<String>();
				for(String s : paramstring.split(";")){
					paramlist.add(s);
				}
				functions.put(fnctname, paramlist);
				
			}
			
		}
		new GUI(functions).setVisible(true);

	}

}
