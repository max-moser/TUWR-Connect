package handler;

/**
 * 
 * A class representing the information for controlling the motors.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class ControlInformation {

	private int modus; // 0=torque; 1=rotation
	private double value; // value of the torque or rotation speed
	private double id; // value of the id current
	
	/**
	 * Creates a new information class with the specified informations.
	 * 
	 * @param modus the modus of control (0=torque; 1=rotation)
	 * @param value the value of the torque or rotation speed
	 * @param id value of the id current
	 */
	public ControlInformation(int modus, double value, double id){
		this.modus = modus;
		this.value = value;
		this.id = id;
	}
	
	/**
	 * Returns whether the motor shall be controlled with
	 * torque.
	 * 
	 * @return controlled with torque?
	 */
	public boolean controlWithTorque(){
		
		if(modus == 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Returns whether the motor shall be controlled with
	 * rotation speed.
	 * 
	 * @return controlled with rotation speed?
	 */
	public boolean controlWithRotation(){
		
		if(modus == 1){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Returns the value of the torque/rotation speed
	 * @return value
	 */
	public double value(){
		return value;
	}
	
	/**
	 * Returns the value of the id current.
	 * @return value of id current
	 */
	public double idCurrent(){
		return id;
	}
	
	@Override
	public String toString(){
		return "[CONTROL INFORMATION] MODUS="+modus+"; VALUE="+value+"; ID_CURRENT="+id;
	}
	
}
