package handler;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import command.CommandProxy;

/**
 * This class is for listening to the close item from
 * the menubar. This also listens to Ctrl+X
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class MenuCloseHandler implements ActionListener {

	private GUI gui;
	
	/**
	 * Creates a new MenuCloseHandler for closing the program.
	 * 
	 * @param gui the main-window of the program
	 */
	public MenuCloseHandler(GUI gui){
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try{
			if(!gui.allStopped()){
				
				if(gui.leftStarted()){
					new StopMotor(gui).stopMotor(true);
				}
				if(gui.rightStarted()){
					new StopMotor(gui).stopMotor(true);
				}
				
			}
			CommandProxy.getInstance().sendCommand("stop", null);
			Thread.sleep(100);
			System.exit(1);
		}catch(Exception e1){
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Problem with closing the program! \n"+e1.getMessage());
			System.exit(-3);
		}catch(java.lang.UnsatisfiedLinkError ule){
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Problem with closing the program! \n"+ule.getMessage());
			System.exit(-3);
		}

	}

}
