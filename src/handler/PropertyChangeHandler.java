/**
 * 
 */
package handler;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import command.CommandProxy;

/**
 * Handler for changing baud-rates
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
public class PropertyChangeHandler implements ActionListener {

	private GUI gui;
	
	public PropertyChangeHandler(GUI g){
		this.gui = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println("test");
		String rate = gui.getBaudRate();
		CommandProxy.getInstance().changeBaudRate(rate);
		
	}

}
