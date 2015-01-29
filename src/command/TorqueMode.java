package command;

import java.util.ArrayList;
import java.util.List;

import peak.can.basic.PeakCanHandler;

public class TorqueMode implements Mode {

	private PeakCanHandler canHandler;
	private List<Integer> validIDs;

	public TorqueMode(PeakCanHandler canHandler){
		this.canHandler = canHandler;
		this.validIDs = new ArrayList<Integer>();
	}

	@Override
	public boolean sendData(byte id, byte[] data) {

		boolean isValidID = false;

		isValidID = this.validIDs.contains(id);

		if(isValidID == false){
			return false;
		}else{
			return this.canHandler.write((byte)id, (byte)data.length, (byte)0, data);
		}
	}

	@Override
	public void setCanHandler(PeakCanHandler canHandler) {
		this.canHandler = canHandler;
	}

	@Override
	public boolean isValidId(int id) {
		return this.validIDs.contains(id);
	}

}
