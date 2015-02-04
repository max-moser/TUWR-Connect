package command;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import etc.FixPoint;
import etc.XMLHandler;

public class CommandHandler extends XMLHandler {

	private List<Command> commandList;
	private List<Parameter> paramList; // temporary list of parameters
	private String commandName; // temporary string containing the command name
	private int commandID; // temporary integer containing the command id

	public CommandHandler(){
		this.commandList = new ArrayList<Command>();
		this.paramList = new ArrayList<Parameter>();
		this.commandName = null;
		this.commandID = -1;
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

		if(qName.toLowerCase().equals("command")){

			this.commandName = atts.getValue("name");
			this.commandID = Integer.parseInt(atts.getValue("id"));
			
		}else if(qName.toLowerCase().equals("param")){
			
			String pname = null;
			int offset = -1, length = -1, fixpoint = -1;
			FixPoint defaultVal = new FixPoint("0.0");
			
			pname = atts.getValue("name");
			offset = Integer.parseInt(atts.getValue("offset"));
			length = Integer.parseInt(atts.getValue("length"));
			fixpoint = Integer.parseInt(atts.getValue("fixpoint"));
			
			Parameter p = new Parameter(pname, offset, length, fixpoint, defaultVal);
			this.paramList.add(p);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) {

		if(qName.toLowerCase().equals("command")){

			if((this.commandID == -1) || (this.commandName == null)){
				// TODO error!
			}

			this.commandList.add(new Command(this.commandName, this.commandID, this.paramList));
			this.commandName = null;
			this.commandID = -1;
			this.paramList = new ArrayList<Parameter>();

		}else if(qName.toLowerCase().equals("param")){

		}
	}

	/**
	 * Returns the resulting List of Commands with their respective Parameters.
	 * 
	 * @return A List containing those Commands which were parsed from the XML file
	 */
	@Override
	public List<Command> getResult(){
		return this.commandList;
	}
}
