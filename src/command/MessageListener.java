package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import peak.can.basic.PeakCanHandler;
import etc.CANObservable;
import etc.CANObserver;
import etc.XMLParser;

public class MessageListener implements CANObservable{

	private final List<CANObserver> observers;
	private final List<Message> messages;
	private PeakCanHandler handle;
	
	public MessageListener(PeakCanHandler handle) throws IOException, SAXException, ParserConfigurationException{
		this.handle = handle;
		this.observers = new ArrayList<CANObserver>();
		MessageHandler m = new MessageHandler();
		new XMLParser(m).parse("messages.xml");
		this.messages = m.getResult();
	}
	
	public void setCANHandler(PeakCanHandler handle){
		this.handle = handle;
	}
	
	@Override
	public void registerObserver(CANObserver o) {
		this.observers.add(o);
	}

	@Override
	public void unregisterObserver(CANObserver o) {
		this.observers.remove(o);
	}
}
