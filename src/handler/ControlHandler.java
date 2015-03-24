package handler;

import etc.FixPoint;
import etc.LogCenter;
import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import command.CommandProxy;

/**
 * Version History: </b>
 * V1.1: Changed the handler to serve the purpose of sending control information
 *    ID50 -> left motor
 *    ID60 -> right motor
 * by pressing enter.
 * 
 * 
 * @author Iorgreths
 * @version 1.1
 *
 */

public class ControlHandler implements ActionListener {
	
	private GUI gui;
	
	public ControlHandler(GUI gui){
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println("HANDLE");
		Logger l = LogCenter.getInstance().getLogger();
		
		/* Send information about left motor */
		if(gui.leftStarted()){
			l.log(Level.INFO,"left motor control information being sent.");
			ControlInformation info = gui.leftInformation();
			l.log(Level.INFO,"[INFORMATION]"+info.toString());
			HashMap<String,FixPoint> params = new HashMap<String,FixPoint>();
			
			if(info.controlWithTorque()){
				
				params.put("modl", new FixPoint("0"));
				params.put("fixpoint", new FixPoint("6"));
				
			}else{
				
				params.put("modl", new FixPoint("1"));
				params.put("fixpoint", new FixPoint("1"));
				
			}
			
			params.put("left", new FixPoint(String.valueOf(info.value())));
			CommandProxy.getInstance().sendCommand("control", params);
			System.out.println("DEBUG:"+String.valueOf(info.value()));
			
		}
		
		/* Send information about right motor */
		if(gui.rightStarted()){
			l.log(Level.INFO,"left motor control information being sent.");
			ControlInformation info = gui.rightInformation();
			HashMap<String,FixPoint> params = new HashMap<String,FixPoint>();
			l.log(Level.INFO,"[INFORMATION]"+info.toString());
			if(info.controlWithTorque()){
				
				params.put("modr", new FixPoint("0"));
				params.put("fixpoint", new FixPoint("6"));
				
			}else{
				
				params.put("modr", new FixPoint("1"));
				params.put("fixpoint", new FixPoint("1"));
				
			}
			
			params.put("right", new FixPoint(String.valueOf(info.value())));
			CommandProxy.getInstance().sendCommand("control_r", params);
			
		}
		
		//@deprecated
		/*if(gui.leftStarted()){
			
			ControlInformation info = gui.leftInformation();
			ControlInformation info2 = gui.rightInformation();
			
			HashMap<String,FixPoint> params = new HashMap<String,FixPoint>();
			
			if(info.controlWithTorque()){
				
				params.put("modl", new FixPoint("0"));
				params.put("fixpoint", new FixPoint("6"));
				
			}else{
				
				params.put("modl", new FixPoint("1"));
				params.put("fixpoint", new FixPoint("1"));
				
			}
			
			params.put("left", new FixPoint(String.valueOf(info.value())));
			
			if(info2.controlWithTorque()){
				
				params.put("modr", new FixPoint("0"));
				params.put("fixpoint", new FixPoint("6"));
				
			}else{
				
				params.put("modr", new FixPoint("1"));
				params.put("fixpoint", new FixPoint("1"));
				
			}
			
			params.put("right", new FixPoint(String.valueOf(info2.value())));
			
			CommandProxy.getInstance().sendCommand("control", params);
			
		}*/

	}

}
