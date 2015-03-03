package command;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import etc.XMLHandler;

public class MessageHandler extends XMLHandler{

	private List<String> messageList;
	
	public MessageHandler(){
		this.messageList = new ArrayList<String>();
	}
	
	@Override
	public void startDocument() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Message> getResult() {
		return null;
	}

}
