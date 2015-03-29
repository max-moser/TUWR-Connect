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
		//TODO add change-baud rate
		// this.stop()
		this.handle = handle;
		// this.listenThread.start()
		// is the thread now running again?
		//NOTE: the PeakCanHandler will be uninitialized in the CommandProxy -> nothing to do here
	}

	@Override
	public void registerObserver(CANObserver o) {
		this.observers.add(o);
	}

	@Override
	public void unregisterObserver(CANObserver o) {
		this.observers.remove(o);
	}

	private void notifyObserversError(List<String> errors, boolean modL){
		for(CANObserver o: this.observers){
			o.notifyError(errors, modL);
		}
	}

	private void notifyObserversConsole(List<String> msg, boolean modL){
		for(CANObserver o: this.observers){
			o.notifyConsole(msg, modL);
		}
	}

	private void notifyObserversActual(double torque, double rotation, boolean modL){
		for(CANObserver o: this.observers){
			o.notifyActual(torque, rotation, modL);
		}
	}

	private void notifyObserversCurrent(double id, double iq, boolean modL){
		for(CANObserver o: this.observers){
			o.notifyCurrent(id, iq, modL);
		}
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
						// TODO
						switch(p.getType()){
						case ERROR:
							//NOTE: notify error
							break;
						case MESSAGE:
							//NOTE: notify console
							break;
						case TORQUE:
							//NOTE: notify actual
							break;
						case ROTATION:
							//NOTE: notify actual
							break;
						case ID:
							//NOTE: notify current
							break;
						case IQ:
							//NOTE: notify current
							break;
						default:

						}
					}
				}
			}
		}
	}
}
