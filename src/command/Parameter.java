package command;

public class Parameter {

	private final String name;
	private final int offset, length, decimal;
	private final FixPoint defaultValue;
	private FixPoint value;
	
	public Parameter(Parameter toClone){
		this.name = toClone.name;
		this.offset = toClone.offset;
		this.length = toClone.length;
		this.decimal = toClone.decimal;
		this.defaultValue = toClone.defaultValue;
		this.value = toClone.value;
	}
	
	public Parameter(String name, int offset, int length, int decimal, FixPoint defaultVal){
		this.name = name;
		this.offset = offset;
		this.length = length;
		this.decimal = decimal;
		this.defaultValue = defaultVal;
		this.value = defaultVal;
	}
}
