package etc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A template for the XMLHandlers in this project, such as the CommandHandler.
 * 
 * Outside of listing the most likely needed methods-to-override, a default implementation for
 * error handling is defined as well.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public abstract class XMLHandler extends DefaultHandler{

	@Override
	public abstract void startDocument();
	
	@Override
	public abstract void endDocument();
	
	@Override
	public abstract void startElement(String namespaceURI, String localName, String qName, Attributes atts);
	
	@Override
	public abstract void endElement(String namespaceURI, String localName, String qName);
	
	@Override
	public void fatalError(SAXParseException ex){
		System.err.println("XML Fatal Error: Abandon All Hope Ye Who Read This");
		System.err.println(ex.getMessage());
	}

	@Override
	public void error(SAXParseException ex){
		String msg = "XML Error: Something seems to be wrong with the XML structure.\n";
		msg += "Hint: Have you specified a correct DTD in the first line?\n";
		msg += "(e.g. for command.xml: \"<!DOCTYPE command SYSTEM \"command.dtd\">\")";
		System.err.println(msg);
		System.err.println();
		System.err.print("Message: ");
		System.err.println(ex.getMessage());
		System.err.println("----------");
		System.err.println();
	}
	
	@Override
	public void warning(SAXParseException ex){
		System.err.println(ex.getMessage());
		System.err.println("----------");
		System.err.println();
	}
	
	/**
	 * This method will return the result of the parsing process.
	 * 
	 * This method will only return sensitive data when called after the corresponding XMLParser's 
	 * parse() method.
	 * 
	 * @return The result of the parsing process.
	 */
	public abstract Object getResult();
	
}
