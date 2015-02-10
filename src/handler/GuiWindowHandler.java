package handler;

import gui.GUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import command.CommandProxy;

/**
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class GuiWindowHandler implements WindowListener {

	private GUI gui;
	
	public GuiWindowHandler(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
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
		}catch(Exception e){
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Problem with closing the program! \n"+e.getMessage());
			System.exit(-3);
		}catch(java.lang.UnsatisfiedLinkError ule){
			java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, "Problem with closing the program! \n"+ule.getMessage());
			System.exit(-3);
		}

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
