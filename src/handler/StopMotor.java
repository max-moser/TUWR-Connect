package handler;

import java.util.HashMap;

import command.CommandProxy;
import etc.FixPoint;
import gui.GUI;

/**
 * 
 * VersionHistory: </br>
 * V1.1: added support for the extra ID of the right motor -> ID60
 * 
 * @author Iorgreths
 * @version 1.1
 *
 */

public class StopMotor {
	
	private GUI gui;
	
	public StopMotor(GUI gui){
		this.gui = gui;
	}
	
	/**
	 * Sends the stop signal towards the controller.
	 * The motor will be stopped over an amount of signal, to prevent collateral
	 * damage.
	 * For this it's necessary to know, which motor shall be stopped
	 * (left or right).
	 * 
	 * @param left is it the left motor?
	 */
	public void stopMotor(boolean left){
		
		double torque = 0;
		
		if(left){
			torque = gui.getTorque();
		}else{
			torque = gui.getTorqueR();
		}
		
		do{
			
			torque = torque / 4;
			if(torque < 100){
				torque = 0;
			}
			HashMap<String,FixPoint> params = new HashMap<String,FixPoint>();
			
			if(torque == 0){
				params.put("r2d", new FixPoint(String.valueOf(0)));
			}else{
				params.put("r2d", new FixPoint(String.valueOf(1)));
			}
			
			if(left){
				
				params.put("left", new FixPoint(String.valueOf(torque)));
				CommandProxy.getInstance().sendCommand("stop", params);
				
			}else{
				
				params.put("right", new FixPoint(String.valueOf(torque)));
				CommandProxy.getInstance().sendCommand("stop_r", params);
				
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}while(torque > 0);
		
	}

}
