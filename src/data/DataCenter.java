package data;

import java.sql.ResultSet;
import java.sql.SQLException;

import etc.DoubleLinkedList;

public class DataCenter{
	
	public enum ConnectionMode{
		UDP, CAN;
	}
	
	private RunnableConnection con;
	protected DoubleLinkedList<ConnectData> data = new DoubleLinkedList<ConnectData>(); 
	private SQLConnection db;
	private static DataCenter instance;
	private Thread connectionThread;
	
	private DataCenter(ConnectionMode initialMode, int port){
		db = new SQLConnection(con, con.getParser()); 
		 
		if(initialMode.equals(ConnectionMode.CAN)){
			throw new UnsupportedOperationException("CAN Receiver not yet implemented");
		}else{
			con = new UDPConnection(this, port);
		}
	}
	
	public static DataCenter getInstance(){
		if(instance == null){
			return (instance = new DataCenter(ConnectionMode.UDP, 10000));
		}
		else{
			return null;
		}
	}
	
	public ConnectData getOldestDataEntry(){
		@SuppressWarnings("unused")
		int i = 0;
		while(this.data.isEmpty()){
			i++; // so that the CPU is not bored senseless
		}
		return this.data.getAndRemoveLast();
	}
	
	public DoubleLinkedList<ConnectData> getData(){
		return data;
	}
	
	protected synchronized void addToList(ConnectData conDat) throws SQLException {
		db.insertData(conDat); // TODO Buffer fuer die DB, weil sonst zu langsam
		data.addFirst(conDat);
		while(data.size() > 1024){
			data.removeLast();
		}
	}
	
	public ResultSet select(String stmt) throws SQLException{
		return db.select(stmt);
	}
	
	public DoubleLinkedList<ConnectData> getPuffer(){
		return data;
	}
	
	public void connect() throws SQLException{
		db.insertUnits();	
		connectionThread = new Thread(con);
		connectionThread.start();
	}
	
	public void changeConnection(int port, RunnableConnection con){
		this.con = con;
		db = new SQLConnection(con, con.getParser()); 
		data.clear();
	}
	
	public void disconnect(){
		connectionThread.interrupt();
	}

	public RunnableConnection getConnect(){
		return con;
	}
}
