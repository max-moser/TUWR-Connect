package data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class UDP extends RunnableConnection{
	
	public UDP(DataCenter dc){
		super(dc);
	}

	@Override
	public void run() {
		DatagramSocket clientSocket;
		byte[] receiveData = new byte[1024];  
		
		try {
			while(true){
				clientSocket = new DatagramSocket();
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

