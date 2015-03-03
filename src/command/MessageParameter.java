package command;

import java.awt.TrayIcon.MessageType;

import etc.FixPoint;

/**
 * The internal representation of a Parameter, as used by Commands.
 * 
 * It has a name, a value and the properties needed in order to translate the Command into a CAN message.
 * These properties are:
 * 
 * - name:
 * 			The alphanumeric String used to identify the Parameter
 * 
 * - offset:
 * 			The amount of bits in the CAN message left of the parameter
 * 
 * - length:
 * 			The amount of bits in the CAN message available for the parameter
 * 
 * - fixpoint:
 * 			The position of the fixed decimal point in the parameter's value.
 * 			E.g. A fixpoint of 1 means that there is 1 bit for the integral part of the value
 * 				and the rest is the fractional part.
 * 			Hint: A fixpoint of -1 means that there is no fractional part.
 * 
 * - Type:
 * 			Has one of two values: VALUE or ERRORCODE
 * 			ERRORCODE means that the value should be interpreted as integer code for an error
 * 				(the fixpoint parameter will be ignored)
 * 			VALUE means that the value is a fixpoint value
 * 
 * - value:
 * 			The parameter's actual value, also referred to as "real value".
 * 			This can be changed to an arbitrary value at any time as well as back to default.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 */
public class MessageParameter {

	private final String name;
	// offset: The bit at which the parameter's value should start (in the CAN message)
	// length: The length of the parameter's value, in Bits
	// fixpoint: The position of the decimal point (because it's a fixed point value)
	private final int offset, length, fixpoint;
	private FixPoint value;
	private MessageParameterType type;
	
	public MessageParameter(MessageParameter toClone){
		this.name = toClone.name;
		this.offset = toClone.offset;
		this.length = toClone.length;
		this.type = toClone.type;
		this.fixpoint = toClone.fixpoint;
		this.value = toClone.value;
	}
	
	public MessageParameter(String name, int offset, int length, String type, int fixpoint){
		this.name = name;
		this.offset = offset;
		this.length = length;
		this.type = MessageParameterType.valueOf(type.toUpperCase());
		this.fixpoint = (type.equals(MessageParameterType.ERRORCODE))?(-1):(fixpoint);
		this.value = new FixPoint("0");
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
	public MessageParameter clone(FixPoint realValue){
		MessageParameter ret = new MessageParameter(this);
		ret.value = realValue;
		return ret;
	}
	
	public MessageParameterType getType(){
		return this.type;
	}
	
	public int getFixpoint() {
		return this.fixpoint;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String getName(){
		return this.name;
	}

	public int getOffset() {
		return this.offset;
	}

	public FixPoint getValue(){
		return this.value;
	}

	public void setValue(FixPoint val){
		this.value = val;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		
		buf.append("\"" + this.name + "\": ");
		buf.append("O:" + this.offset + ", ");
		buf.append("L:" + this.length + ", ");
		buf.append("F:" + this.fixpoint + ", ");
		buf.append("T:" + this.type + ", ");
		buf.append("V:" + this.value);
		
		return buf.toString();
	}
}
