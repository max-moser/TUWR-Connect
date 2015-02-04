package command;

import java.util.ArrayList;
import java.util.List;

public class Command {

	private final String cmdName;
	private final List<String> params;
	
	public Command(String name, String... parameters){
		this.cmdName = name;
		this.params = new ArrayList<String>();
		for(String p: parameters){
			this.params.add(p);
		}
	}
}
