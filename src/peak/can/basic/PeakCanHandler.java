package peak.can.basic;

import etc.CANMessage;


public class PeakCanHandler {
	
	private PCANBasic can;
	private TPCANMsg msg;
	private TPCANStatus status;
	private TPCANHandle handle;
	private TPCANBaudrate baud;
	
	public PeakCanHandler(TPCANHandle canHandle, TPCANBaudrate baud){
		this.can = null;
		this.msg = null;
		this.status = null;
		this.handle = canHandle;
		this.baud = baud;
		
		this.can = new PCANBasic();
		
		if(!this.can.initializeAPI()){
			System.out.println("Unable to initialize the API");
			System.exit(0);
		}
		
		this.status = this.can.Initialize(this.handle, this.baud, TPCANType.PCAN_TYPE_NONE, 0, (short) 0);
		System.out.println(status);
		
		this.msg = new TPCANMsg();
	}
	
	public CANMessage read(){
		// TODO blocking or not?
		this.can.Read(this.handle, this.msg, null);
		return (new CANMessage(this.msg.getID(), this.msg.getData()));
	}
	
	public boolean write(int id, byte length, byte type, byte[] data){
		this.msg.setID(id);
		this.msg.setLength(length);
		this.msg.setType(type);
		this.msg.setData(data, length);
		
		TPCANStatus status = this.can.Write(this.handle, this.msg);
		return (status == TPCANStatus.PCAN_ERROR_OK);
	}
	
	public void uninit(){
		this.can.Uninitialize(this.handle);
	}
}
