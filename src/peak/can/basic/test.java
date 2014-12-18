package peak.can.basic;


public class test {
	public static void main(String[] args)
	{
		PCANBasic can = null;
		TPCANMsg msg = null;
		TPCANStatus status = null;
		can = new PCANBasic();
		if(!can.initializeAPI())
		{
			System.out.println("Unable to initialize the API");
			System.exit(0);
		}
		status = can.Initialize(TPCANHandle.PCAN_USBBUS1, TPCANBaudrate.PCAN_BAUD_1M, TPCANType.PCAN_TYPE_NONE, 0, (short) 0);
		System.out.println(status);
		msg = new TPCANMsg();
		while(true)
		{
			TPCANStatus stat;
			while((stat = can.Read(TPCANHandle.PCAN_USBBUS1, msg, null)) == TPCANStatus.PCAN_ERROR_OK)
			{
				byte[] dat = msg.getData();
				String str = "";
				
				if(dat.length > 0){
					str = Byte.toString(dat[0]);
					for(int i=1; i<dat.length; i++){
						str += ":" + Byte.toString(dat[i]);
					}
				}
				
				System.out.println(str);

				status = can.Write(TPCANHandle.PCAN_USBBUS1, msg);
				if(status != TPCANStatus.PCAN_ERROR_OK)
				{
					System.out.println("Unable to write the CAN message");
					System.exit(0);
				}
			}

//			System.out.println(stat);
		}
	}
}
