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

public class ControlRadioButtonHandler implements ActionListener {

	private GUI gui;
	
	public ControlRadioButtonHandler(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		gui.adaptControlLeft();

	}

}
