package handler;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		switch(indicator){
		case 0:
			//TODO
			break;
		case 1:
			//TODO
			break;
		default:
			assert(false);
			break;
		}
		
		//TODO send message indicated by indicator

	}

}
