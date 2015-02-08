package handler;

import java.util.HashMap;

import command.CommandProxy;
import etc.FixPoint;
import gui.GUI;

public class StopMotor {
	
	private GUI gui;
	
	public StopMotor(GUI gui){
		this.gui = gui;
	}
	
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
			params.put("r2d", new FixPoint(String.valueOf(1)));
			if(left){
				
				params.put("left", new FixPoint(String.valueOf(torque)));
				
			}else{
				
				params.put("right", new FixPoint(String.valueOf(torque)));
				
			}
			CommandProxy.getInstance().sendCommand("stop", params);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}while(torque > 0);
		
	}

}
