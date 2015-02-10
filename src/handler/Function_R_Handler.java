package handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import gui.FunctionDialog;
import gui.GUI;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import command.CommandProxy;

/**
 * 
 * @author iorgreths
 * @version 1.0
 *
 */

public class Function_R_Handler implements ListSelectionListener {

	private GUI gui;
	
	/**
	 * Creates a new handler for a list selection.
	 * This handler needs to know the frame the list is in.
	 * 
	 * @param gui the gui containing the JList
	 */
	public Function_R_Handler(GUI gui){
		this.gui = gui;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		
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

	}

}
