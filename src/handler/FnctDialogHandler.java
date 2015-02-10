package handler;

import etc.FixPoint;

import gui.FunctionDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import command.CommandProxy;

/**
 * 
 * @author iorgreths
 * @version 1.0
 *
 */

public class FnctDialogHandler implements ActionListener {

	private FunctionDialog fnctdialog;
	
	/**
	 * Creates a new function handler for the designated dialog.
	 * After going through this handler the dialog will be closed.
	 * 
	 * @param dialog the dialog this handler is for
	 */
	public FnctDialogHandler(FunctionDialog dialog){
		this.fnctdialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String fnct = fnctdialog.getFunctionName();
		HashMap<String,FixPoint> params = fnctdialog.getParameters();
		fnctdialog.dispose();
		CommandProxy.getInstance().sendCommand(fnct, params);

	}

}
