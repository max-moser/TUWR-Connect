package peak.can.basic;


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
	
	public void doSomething(){
		while(true){
			
			TPCANStatus stat;
			while((stat = this.can.Read(this.handle, this.msg, null)) == TPCANStatus.PCAN_ERROR_OK){
				byte[] dat = this.msg.getData();
				String str = "";
				
				if(dat.length > 0){
					str = Byte.toString(dat[0]);
					for(int i=1; i<dat.length; i++){
						str += ":" + Byte.toString(dat[i]);
					}
				}
				
				System.out.println(str);

				this.status = this.can.Write(this.handle, this.msg);
				
				if(this.status != TPCANStatus.PCAN_ERROR_OK){
					System.out.println("Unable to write the CAN message");
					System.exit(0);
				}
			}
		}
	}
	
	public boolean write(int id, byte length, byte type, byte[] data){
		this.msg.setID(id);
		this.msg.setLength(length);
		this.msg.setType(type);
		this.msg.setData(data, length);
		
		TPCANStatus status = this.can.Write(this.handle, this.msg);
		return (status == TPCANStatus.PCAN_ERROR_OK);
	}
}
