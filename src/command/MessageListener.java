package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import peak.can.basic.PeakCanHandler;
import etc.CANMessage;
import etc.CANObservable;
import etc.CANObserver;
import etc.XMLParser;

public class MessageListener implements CANObservable{

	private final List<CANObserver> observers;
	private final List<Message> messages;
	private PeakCanHandler handle;
	private final Thread listenThread;
	private final ListenThreadRunnable ltr;
	private boolean running;

	public MessageListener(PeakCanHandler handle) throws IOException, SAXException, ParserConfigurationException{
		this.handle = handle;
		this.observers = new ArrayList<CANObserver>();
		MessageHandler m = new MessageHandler();
		new XMLParser(m).parse("message.xml");
		this.messages = m.getResult();
		this.running = true;
		
		this.ltr = new ListenThreadRunnable(this);
		this.listenThread = new Thread(ltr);
		this.listenThread.start();
	}

	public void stop(){
		this.running = false;
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
	
	private void notifyObserversError(){
			
	}
	
	private void notifyObserversConsole(){
		
	}
	
	private void notifyObserversActual(){
		
	}
	
	private void notifyObserversCurrent(){
		
	}

	/**
	 * A Thread Job
	 * 
	 * @author Maxmanski
	 *
	 */
	private class ListenThreadRunnable implements Runnable{

		private final MessageListener ml;
		private CANMessage cMsg;
		
		public ListenThreadRunnable(MessageListener ml){
			this.ml = ml;
		}
		
		@Override
		public void run() {
			while(this.ml.running){
				cMsg = this.ml.handle.read();
				Message msg = null;
				
				for(Message m: ml.messages){
					if(m.getID() == cMsg.getID()){
						msg = m;
						break;
					}
				}
				
				if(msg != null){
					for(MessageParameter p: msg.getParameters()){
						if(p.getType().equals(MessageParameterType.ERRORCODE)){
							
							
						}else{
							// is VALUE
							
						}
					}
				}
			}
		}
	}
}
