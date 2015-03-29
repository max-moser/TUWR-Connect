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
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class StartHandler implements ActionListener {

	private GUI gui;
	
	public StartHandler(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int indicator = gui.adaptStart();
		Logger l = LogCenter.getInstance().getLogger();
		l.log(Level.INFO, "Start left-motor with indicator "+indicator);
		/*
		 * 0 - start the motor</li>
         * 1 - stop the motor</li>
		 */
		
		switch(indicator){
		case 0:
			HashMap<String,FixPoint> param = new HashMap<String,FixPoint>();
			param.put("left", new FixPoint("0"));
			CommandProxy.getInstance().sendCommand("start", param);
			gui.baudChoosable(false);
			break;
		case 1:
			new StopMotor(gui).stopMotor(true);
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
