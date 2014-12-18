package command;

import peak.can.basic.PeakCanHandler;

public class TorqueMode implements Mode {

	private PeakCanHandler canHandler;
	
	public TorqueMode(PeakCanHandler canHandler){
		this.canHandler = canHandler;
	}
	
	@Override
	public boolean sendData(byte id, byte[] data) {
		return this.canHandler.write((byte)id, (byte)data.length, (byte)0, data);
	}

	@Override
	public void setCanHandler(PeakCanHandler canHandler) {
		this.canHandler = canHandler;
	}

}
