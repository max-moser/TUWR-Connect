package command;

import java.util.HashMap;

public class Command {

	private final String cmdName;
	private final HashMap<String, Parameter> params;
	
	// Copy Constructor
	public Command(Command toClone){
		this.cmdName = toClone.cmdName;
		this.params = new HashMap<String, Parameter>();
		for(String p: toClone.params.keySet()){
			this.params.put(p, new Parameter(toClone.params.get(p)));
		}
	}
	
	public Command(String name, HashMap<String, Parameter> parameters){
		this.cmdName = name;
		this.params = new HashMap<String, Parameter>();
		for(String p: parameters.keySet()){
			this.params.put(p, new Parameter(parameters.get(p)));
		}
	}
	
	/**
	 * Returns the command's parameter, identified by name
	 * 
	 * @param name
	 * @return
	 */
	public Parameter getParameter(String name){
		return this.params.get(name);
	}
}
