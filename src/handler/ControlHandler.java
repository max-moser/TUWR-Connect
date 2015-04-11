package handler;

import etc.FixPoint;
import etc.LogCenter;
import gui.FunctionDialog;
import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import command.CommandProxy;

/**
 * Version History: </b>
 * V1.1: Changed the handler to serve the purpose of sending control information </b>
 *    ID50 -> left motor </b>
 *    ID60 -> right motor </b>
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
		int index = 0;
		
		/* Send information about left motor */
		if(gui.leftStarted()){
			
			index = gui.selectedTab(true);
			switch(index){
			case 0:
				
				// CONTROL TAB
				l.log(Level.INFO,"left motor control information being sent.");
				ControlInformation info = gui.leftInformation();
				l.log(Level.INFO,"[INFORMATION]"+info.toString());
				HashMap<String,FixPoint> params = new HashMap<String,FixPoint>();
				
				if(info.controlWithTorque()){
					
					params.put("modl", new FixPoint("0"));
					params.put("fixpoint", new FixPoint("10"));
					
				}else{
					
					params.put("modl", new FixPoint("1"));
					params.put("fixpoint", new FixPoint("15"));
					
				}
				
				params.put("left", new FixPoint(String.valueOf(info.value())));
				CommandProxy.getInstance().sendCommand("control", params);
				System.out.println("DEBUG:"+String.valueOf(info.value()));
				break;
				// CONTROL TAB
				
			case 1:
				
				// FUNCTION TAB
				HashMap<String,List<String>> function = gui.getSelectedFunction();
				if(function.size() == 0){
					return; // do nothing
				}
				if(function.size() == 1){
					
					for(Entry<String,List<String>> entry : function.entrySet()){
						if(entry.getValue().size() == 0){
							
							//NOTE: directly send command -> no dialog
							CommandProxy.getInstance().sendCommand(entry.getKey(), null);
							
						}else{
							
							//NOTE: create a new dialog and block GUI
							new FunctionDialog(gui, entry.getKey(), entry.getValue()).setVisible(true);
							//gui.setFocusable(false);
							
						}
					}
					
				}else{
					assert(false); // should never get to this point
				}
				break;
				//FUNCTION TAB
				
			default:
				assert(false); // there should be no other indices
				break;
			}
			
		}
		
		/* do this only if the right part is expanded */
		if(gui.isExpanded()){
			
			/* Send information about right motor */
			if(gui.rightStarted()){
				
				index = gui.selectedTab(false);
				switch(index){
				case 0:
					
					//CONTROL TAB
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
					break;
					//CONTROL TAB
					
				case 1:
					
					//FUNCTION TAB
					HashMap<String,List<String>> function = gui.getSelectedFunctionRight();
					if(function.size() == 0){
						return; // do nothing
					}
					if(function.size() == 1){
						
						for(Entry<String,List<String>> entry : function.entrySet()){
							if(entry.getValue().size() == 0){
								
								//NOTE: directly send command -> no dialog
								CommandProxy.getInstance().sendCommand(entry.getKey(), null);
								
							}else{
								
								//NOTE: create a new dialog and block GUI
								new FunctionDialog(gui, entry.getKey(), entry.getValue()).setVisible(true);
								//gui.setFocusable(false);
								
							}
						}
						
					}else{
						assert(false); // should never reach this point
					}
					break;
					//FUNCTION TAB
					
				default:
					assert(false); // there should be no other indices
					break;
				}
				
			}
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
