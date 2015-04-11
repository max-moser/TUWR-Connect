package handler;

import gui.ConnectDialog;
import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is for handling the connect item of the
 * menubar. This also listens to Ctrl+R
 * 
 * @author Iorgreths
 * @versin 1.0
 *
 */

public class MenuReconnectHandler implements ActionListener {

	private GUI gui;
	
	/**
	 * Creates a new MenuReconnectHandler with the specified parent-frame.
	 * 
	 * @param parent
	 */
	public MenuReconnectHandler(GUI parent){
		gui = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ConnectDialog dialog = new ConnectDialog(gui);
		if(!gui.isBaudEnabled()){
			dialog.disableReconnect();
		}
		dialog.setVisible(true);

	}

}
