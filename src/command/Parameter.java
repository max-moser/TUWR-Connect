package command;

import etc.FixPoint;

public class Parameter {

	private final String name;
	// offset: The bit at which the parameter's value should start (in the CAN message)
	// length: The length of the parameter's value, in Bits
	// fixpoint: The position of the decimal point (because it's a fixed point value)
	private final int offset, length, fixpoint;
	private final FixPoint defaultValue;
	private FixPoint value;
	
	public Parameter(Parameter toClone){
		this.name = toClone.name;
		this.offset = toClone.offset;
		this.length = toClone.length;
		this.fixpoint = toClone.fixpoint;
		this.defaultValue = toClone.defaultValue;
		this.value = toClone.value;
	}
	
	public Parameter(String name, int offset, int length, int fixpoint, FixPoint defaultVal){
		this.name = name;
		this.offset = offset;
		this.length = length;
		this.fixpoint = fixpoint;
		this.defaultValue = defaultVal;
		this.value = defaultVal;
	}
	
	/**
	 * Returns the parameter's name
	 * 
	 * @return A String, containing the Parameter's name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the Parameter's real value.
	 * 
	 * @return A FixPoint containing the Parameter's real value
	 */
	public FixPoint getValue(){
		return this.value;
	}
	
	/**
	 * Sets the Parameter's real value to the specified value.
	 * 
	 * @param val The parameter's new real value
	 */
	public void setValue(FixPoint val){
		this.value = val;
	}
	
	/**
	 * Takes this parameter as template for the construction of a new Parameter.
	 * The only difference is the new Parameter's real value.
	 * 
	 * This can be used for instantiating real-use Parameters from template Parameters.
	 * 
	 * @param realValue The real value of the newly constructed Parameter
	 * @return A deep copy of this Parameter, with another real value.
	 */
	public Parameter clone(FixPoint realValue){
		Parameter ret = new Parameter(this);
		ret.value = realValue;
		return ret;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		
		buf.append("\"" + this.name + "\": ");
		buf.append("O:" + this.offset + ", ");
		buf.append("L:" + this.length + ", ");
		buf.append("F" + this.fixpoint + ", ");
		buf.append("D:" + this.defaultValue + ", ");
		buf.append("V:" + this.value);
		
		return buf.toString();
	}
}
