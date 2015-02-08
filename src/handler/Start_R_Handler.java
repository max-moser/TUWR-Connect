package handler;

import etc.FixPoint;
import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import command.CommandProxy;

/**
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class Start_R_Handler implements ActionListener {

	private GUI gui;
	
	public Start_R_Handler(GUI gui) {

		this.gui = gui;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		int indicator = gui.adaptStartRight();
		
		/*
		 * 0 - start the motor</li>
         * 1 - stop the motor</li>
		 */
		
		switch(indicator){
		case 0:
			HashMap<String,FixPoint> param = new HashMap<String,FixPoint>();
			param.put("left", new FixPoint("1"));
			CommandProxy.getInstance().sendCommand("start", param);
			break;
		case 1:
			new StopMotor(gui).stopMotor(false);
			break;
		default:
			assert(false);
			break;
		}
		

	}

}
