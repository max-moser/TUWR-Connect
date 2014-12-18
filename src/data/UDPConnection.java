package data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class UDPConnection extends RunnableConnection{
	
	private final int port;
	
	public UDPConnection(DataCenter dc, int port){
		super(dc);
		this.port = port;
	}

	@Override
	public void run() {
		DatagramSocket clientSocket;
		byte[] receiveData = new byte[1024];  
		
		try {
			clientSocket = new DatagramSocket(port);
			
			while(true){
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);    
				clientSocket.receive(receivePacket);   
				ConnectData cdata = new ConnectData(Double.parseDouble(new String(receivePacket.getData())), new Date().getTime());
				dc.addToList(cdata);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
}

