package command;

import peak.can.basic.PeakCanHandler;

public class RotationMode implements Mode{

	private PeakCanHandler canHandler;
	
	public RotationMode(PeakCanHandler canHandler){
		this.canHandler = canHandler;
	}
	
	@Override
	public boolean sendData(byte id, byte[] data) {
		return this.canHandler.write((int)id, (byte)data.length, (byte)0, data);
	}

	@Override
	public void setCanHandler(PeakCanHandler canHandler) {
		this.canHandler = canHandler;
	}
	
}
