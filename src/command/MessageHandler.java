package command;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import etc.XMLHandler;

public class MessageHandler extends XMLHandler{

	private List<Message> messageList;
	private List<MessageParameter> paramList;
	private String msgName;
	private int msgID;
	
	public MessageHandler(){
		this.messageList = new ArrayList<Message>();
		this.paramList = new ArrayList<MessageParameter>();
		msgName = null;
		msgID = -1;
	}
	
	@Override
	public void startDocument() {
		
	}

	@Override
	public void endDocument() {
		
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		
		if(qName.equalsIgnoreCase("message")){
			
			this.msgName = atts.getValue("name");
			this.msgID = Integer.parseInt(atts.getValue("id"));
			
		}else if(qName.equalsIgnoreCase("param")){
			
			String pname = null;
			int offset = -1, length = -1, fixpoint = -1;
			String type = MessageParameterType.ERROR.toString();
			String motor = MessageParameterMotor.LEFT.toString();
			
			if(atts.getValue("name") != null){
				pname = atts.getValue("name");
			}
			if(atts.getValue("offset") != null){
				offset = Integer.parseInt(atts.getValue("offset"));
			}
			if(atts.getValue("length") != null){
				length = Integer.parseInt(atts.getValue("length"));
			}
			if(atts.getValue("fixpoint") != null){
				fixpoint = Integer.parseInt(atts.getValue("fixpoint"));
			}
			if(atts.getValue("type") != null){
				type = atts.getValue("type");
			}
			if(atts.getValue("motor") != null){
				motor = atts.getValue("motor");
			}
			
			MessageParameter mp = new MessageParameter(pname, offset, length, type, motor, fixpoint);
			this.paramList.add(mp);
			
		}
		
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) {
		if(qName.toLowerCase().equals("message")){

			if((this.msgID == -1) || (this.msgName == null)){
				// TODO error!
			}

			this.messageList.add(new Message(this.msgName, this.msgID, this.paramList));
			this.msgName = null;
			this.msgID = -1;
			this.paramList = new ArrayList<MessageParameter>();

		}else if(qName.toLowerCase().equals("param")){

		}
	}

	@Override
	public List<Message> getResult() {
		return this.messageList;
	}

}
