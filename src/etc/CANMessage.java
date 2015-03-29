package etc;

/**
 * A wrapper for a CAN message, including the ID and the data.
 * 
 * @author Maxmanski
 */
public class CANMessage {

	private final int id;
	private final byte[] data;
	
	public CANMessage(int id, byte[] data){
		this.id = id;
		this.data = data;
		//System.out.println("INCOMING: "+id+" :: "+BinaryToString.bytesToString(data));
	}
	
	public int getID(){
		return this.id;
	}
	
	public byte[] getData(){
		return this.data;
	}
	
}
