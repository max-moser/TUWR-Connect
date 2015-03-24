package handler;

import etc.FixPoint;
import etc.LogCenter;
import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import command.CommandProxy;

/**
 * VersionHistory: </br>
 * V1.1: adapted new ID for right motor -> ID60
 * 
 * 
 * @author Iorgreths
 * @version 1.1
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
		Logger l = LogCenter.getInstance().getLogger();
		l.log(Level.INFO, "Start left-motor with indicator "+indicator);
		/*
		 * 0 - start the motor</li>
         * 1 - stop the motor</li>
		 */
		
		switch(indicator){
		case 0:
			HashMap<String,FixPoint> param = new HashMap<String,FixPoint>();
			param.put("left", new FixPoint("1"));
			CommandProxy.getInstance().sendCommand("start_r", param);
			gui.baudChoosable(false);
			break;
		case 1:
			new StopMotor(gui).stopMotor(false);
			if(gui.allStopped()){
				gui.baudChoosable(true);
			}
			break;
		default:
			assert(false);
			break;
		}
		

	}

}
