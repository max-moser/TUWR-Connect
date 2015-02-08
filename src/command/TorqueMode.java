package command;

import java.util.List;

import etc.FixPoint;
import peak.can.basic.PeakCanHandler;

/**
 * The Torque mode.
 * It will validate commands-to-be-sent against compliance with the torque mode
 * (valid ID and valid parameters) before sending them over CAN.
 * 
 * @author Maxmanski
 * @version 1.1
 *
 */
public class TorqueMode extends Mode {

	private final int validModL, validModR;
	
	public TorqueMode(PeakCanHandler canHandler){
		super(canHandler);
		this.validModL = 0;
		this.validModR = 0;
	}

	@Override
	protected boolean checkID(int id) {
		return true;
	}

	@Override
	protected boolean checkParameters(List<Parameter> params) {
		String validVal = "";
		for(Parameter p: params){
			if(p.getName().equalsIgnoreCase("modl")){
				validVal = Double.toString(this.validModL);
				if(!p.getValue().equals(new FixPoint(validVal))){
					return false;
				}
				
			}else if(p.getName().equalsIgnoreCase("modr")){
				validVal = Double.toString(this.validModR);
				if(!p.getValue().equals(new FixPoint(validVal))){
					return false;
				}
			}
		}
		return true;
	}

}
