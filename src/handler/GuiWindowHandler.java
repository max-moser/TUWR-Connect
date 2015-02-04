package handler;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class GuiWindowHandler implements WindowListener {

	public GuiWindowHandler() {
		// TODO Auto-generated constructor stub
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
		
		/*
		 * Close all open connections and exit the program afterwards
		 */
		//TODO
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
