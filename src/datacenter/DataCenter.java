package datacenter;

import java.sql.SQLException;
import java.util.List;

public class DataCenter{
	
	private RunnableConnection con;
	private SQLConnection db = new SQLConnection();
	private static DataCenter instance;
	
	private DataCenter(){}
	
	public static DataCenter getInstance(){
		if(instance == null){
			return (instance = new DataCenter());
		}
		else{
			return null;
		}
	}
	
	public void select(String stmt) throws SQLException{
		db.select(stmt);
	}
	
	public List<ConnectData> getPuffer(){
		return con.getData();
	}
	
	public void connect(RunnableConnection con){
		Thread udpThread = new Thread(con);
		udpThread.start();
	}
}
