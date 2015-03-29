package peak.can.basic;

import etc.CANMessage;


public class PeakCanHandler {
	
	private PCANBasic can;
	private TPCANMsg msg;
	private TPCANStatus status;
	private TPCANHandle handle;
	private TPCANBaudrate baud;
	private boolean initialised;
	private static boolean apiInit;
	
	
	public PeakCanHandler(TPCANHandle canHandle, TPCANBaudrate baud){
		this.can = null;
		this.msg = null;
		this.status = null;
		this.handle = canHandle;
		this.baud = baud;
		
		this.can = new PCANBasic();
		
		if(!PeakCanHandler.apiInit){
			if(!this.can.initializeAPI()){
				// TODO throw exception
				// "cannot initialise CAN API"
			}else{
				PeakCanHandler.apiInit = true;
			}
		}
		
		this.status = this.can.Initialize(this.handle, this.baud, TPCANType.PCAN_TYPE_NONE, 0, (short) 0);
		if(!this.status.equals(TPCANStatus.PCAN_ERROR_OK)){
			// TODO
			// error handling: could not initialise
		}
		
		this.initialised = true;
		
		this.msg = new TPCANMsg();
	}
	
	public CANMessage read(){
		// TODO non-blocking!
		if(!this.initialised){
			throw new IllegalStateException("Cannot read from uninitialised CAN handler");
		}
		this.can.Read(this.handle, this.msg, null);
		return (new CANMessage(this.msg.getID(), this.msg.getData()));
	}
	
	public boolean write(int id, byte length, byte type, byte[] data){
		if(!this.initialised){
			throw new IllegalStateException("Cannot write to uninitialised CAN handler");
		}
		
		this.msg.setID(id);
		this.msg.setLength(length);
		this.msg.setType(type);
		this.msg.setData(data, length);

		TPCANStatus status = this.can.Write(this.handle, this.msg);
		return (status == TPCANStatus.PCAN_ERROR_OK);
	}
	
	public void uninit(){
		if(this.initialised){
			this.can.Uninitialize(TPCANHandle.PCAN_NONEBUS);
			this.initialised = false;
		}
	}
}
