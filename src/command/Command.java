package command;

import java.util.ArrayList;
import java.util.List;

import etc.FixPoint;

/**
 * The internal representation of a Command.
 * It has the following properties:
 * - Name: The alphanumeric name of the command
 * - ID: The Command's CAN ID
 * - Parameters: A list of parameters, such as Offset, Length, ...
 * 
 * It features a method with which the values of its parameters can be changed.
 * Also, it features a copy constructor.
 * 
 * Thus, it is possible to create a set of template Commands which can be cloned
 * once necessary and the clone's parameters can be adjusted as needed.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public class Command {

	private final String cmdName;
	private final List<Parameter> params;
	private final int id;
	
	// Copy Constructor
	public Command(Command toClone){
		this.cmdName = toClone.cmdName;
		this.params = new ArrayList<Parameter>();
		this.id = toClone.id;
		for(Parameter p: toClone.params){
			this.params.add(new Parameter(p));
		}
	}
	
	public Command(String name, int id, List<Parameter> parameters){
		this.cmdName = name;
		this.params = new ArrayList<Parameter>();
		this.id = id;
		for(Parameter p: parameters){
			this.params.add(new Parameter(p));
		}
	}
	
	/**
	 * Returns the command's parameter, identified by name
	 * 
	 * @param name The name of the desired parameter
	 * @return Either an instance of a parameter or NULL, if no parameter with the specified name exists for the command
	 */
	public Parameter getParameter(String name){
		for(Parameter p: this.params){
			if(p.getName().equals(name)){
				return p;
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
		return this.cmdName;
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
	 * Checks if this command bears the specified name.
	 * 
	 * @param cmd The command to check
	 * @return TRUE if the specified command shares this command's name; FALSE otherwise
	 */
	public boolean isCommand(String cmd){
		return (this.cmdName.equals(cmd));
	}
	
	/**
	 * Returns all of the Command's Parameters
	 * 
	 * @return A List containing all of the Command's Parameters
	 */
	public List<Parameter> getParameters(){
		return this.params;
	}
	
	/**
	 * Replaces one of the Command's Parameters with the specified one:
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
	public boolean replaceParameter(Parameter p){
		Parameter tmp = null;
		for(Parameter param: this.params){
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
	 * Replaces the value of one of the Command's Parameters with the specified one:
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
		Parameter tmp = null;
		for(Parameter param: this.params){
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

		buf.append("\"" + this.cmdName + "\":");
		buf.append(this.id + "\n");
		for(Parameter p: this.params){
			buf.append("\t" + p.toString() + "\n");
		}
		
		// delete last "\n"
		buf.deleteCharAt(buf.length() - 1);
		
		return buf.toString();
	}
}
