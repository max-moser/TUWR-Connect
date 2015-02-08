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
		
		if(!gui.allStopped()){
			
			if(gui.leftStarted()){
				new StopMotor(gui).stopMotor(true);
			}
			if(gui.rightStarted()){
				new StopMotor(gui).stopMotor(true);
			}
			
		}
		CommandProxy.getInstance().sendCommand("stop", null);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);

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
