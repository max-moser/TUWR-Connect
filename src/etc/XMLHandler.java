package etc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

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
	 * 
	 * @return
	 */
	public Object getResult(){
		// TODO
		return new Integer(1337);
	}
	
}
