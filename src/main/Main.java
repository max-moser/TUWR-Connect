package main;

import command.CommandCenter;
import data.DataCenter;
import data.UDPConnection;
import peak.can.basic.PeakCanHandler;
import peak.can.basic.TPCANBaudrate;
import peak.can.basic.TPCANHandle;

public class Main {

	public static void main(String[] args){
		PeakCanHandler canHandler = new PeakCanHandler(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M);
		CommandCenter cc = new CommandCenter(canHandler);
		DataCenter dc = DataCenter.getInstance();
		dc.connect();
	}
}
