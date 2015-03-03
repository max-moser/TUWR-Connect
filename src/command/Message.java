package command;

import java.util.ArrayList;
import java.util.List;

import etc.FixPoint;

/**
 * The internal representation of a Message.
 * It has the following properties:
 * - Name: The alphanumeric name of the message
 * - ID: The Command's CAN ID
 * - Parameters: A list of parameters, such as Offset, Length, ...
 * 
 * It features a method with which the values of its parameters can be changed.
 * Also, it features a copy constructor.
 * 
 * Thus, it is possible to create a set of template Messages which can be cloned
 * once necessary and the clone's parameters can be adjusted as needed.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public class Message {

	private final String msgName;
	private final List<MessageParameter> params;
	private final int id;
	
	// Copy Constructor
	public Message(Message toClone){
		this.msgName = toClone.msgName;
		this.params = new ArrayList<MessageParameter>();
		this.id = toClone.id;
		for(MessageParameter mp: toClone.params){
			this.params.add(new MessageParameter(mp));
		}
	}
	
	public Message(String name, int id, List<MessageParameter> parameters){
		this.msgName = name;
		this.params = new ArrayList<MessageParameter>();
		this.id = id;
		for(MessageParameter mp: parameters){
			this.params.add(new MessageParameter(mp));
		}
	}
	
	/**
	 * Returns the command's parameter, identified by name
	 * 
	 * @param name The name of the desired parameter
	 * @return Either an instance of a parameter or NULL, if no parameter with the specified name exists for the command
	 */
	public MessageParameter getParameter(String name){
		for(MessageParameter mp: this.params){
			if(mp.getName().equals(name)){
				return mp;
			}
		}
		return null;
	}
	
	/**
	 * Returns the Command's name.
	 * 
	 * @return A String containing the command's name
	 */
	public String getName(){
		return this.msgName;
	}
	
	/**
	 * Returns the Command's CAN ID.
	 * 
	 * @return An integer value representing the command's CAN ID.
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * Checks if this message bears the specified name.
	 * 
	 * @param msg The message to check
	 * @return TRUE if the specified message shares this command's name; FALSE otherwise
	 */
	public boolean isMessage(String msg){
		return (this.msgName.equals(msg));
	}
	
	/**
	 * Returns all of the Message's Parameters
	 * 
	 * @return A List containing all of the Message's Parameters
	 */
	public List<MessageParameter> getParameters(){
		return this.params;
	}
	
	/**
	 * Replaces one of the Message's Parameters with the specified one:
	 * 
	 * A parameter will be replaced if it has the same name as the specified one.
	 * In this case, TRUE will be returned.
	 * 
	 * If no parameter with the same name can be found, nothing will be done
	 * and FALSE will be returned.
	 * 
	 * @param p The new parameter
	 * @return TRUE if a parameter was found and replaced; FALSE if no parameter was replaced
	 */
	public boolean replaceParameter(MessageParameter p){
		MessageParameter tmp = null;
		for(MessageParameter param: this.params){
			param.getName().equals(p.getName());
			tmp = param;
			break;
		}
		
		if(tmp == null){
			return false;
		}
		
		this.params.remove(tmp);
		this.params.add(p);
		return true;
	}
	
	/**
	 * Replaces the value of one of the Message's Parameters with the specified one:
	 * 
	 * If the Command has a Parameter with the specified name, its value will be
	 * overridden by the specified value and TRUE will be returned.
	 * 
	 * Else, nothing will be done and FALSE will be returned.
	 * 
	 * @param name The name of the parameter to be overridden
	 * @param value The new value of the parameter
	 * @return TRUE, if a parameter was found and overridden; FALSE otherwise
	 */
	public boolean replaceParameter(String name, FixPoint value){
		MessageParameter tmp = null;
		for(MessageParameter param: this.params){
			if(param.getName().equals(name)){
				tmp = param;
				break;
			}
		}
		
		if(tmp == null){
			return false;
		}
		
		tmp.setValue(value);
		return true;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();

		buf.append("\"" + this.msgName + "\":");
		buf.append(this.id + "\n");
		for(MessageParameter mp: this.params){
			buf.append("\t" + mp.toString() + "\n");
		}
		
		// delete last "\n"
		buf.deleteCharAt(buf.length() - 1);
		
		return buf.toString();
	}
}
