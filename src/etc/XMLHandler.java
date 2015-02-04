package etc;

import org.xml.sax.Attributes;
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
		System.err.println("FRAUEN UND KINDER ZUERST");
	}

	@Override
	public void error(SAXParseException ex){
		System.err.println("MANN UEBER BORD");
	}
	
	@Override
	public void warning(SAXParseException ex){
		System.err.println("LAND IN SICHT");
	}
	
}
