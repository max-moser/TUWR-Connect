package datacenter;

import java.util.LinkedList;
import java.util.List;

public abstract class RunnableConnection implements Runnable{
	//run(); durch runnable
	protected static int conCnt = 0;
	protected int id;
	protected SQLConnection db;
	protected List<ConnectData> data = new LinkedList<>(); 
	
	public RunnableConnection(){
		id = conCnt++;
		db = new SQLConnection();
	}
	
	public List<ConnectData> getData(){
		return data;
	}
	
	public SQLConnection getSQLConnection(){
		return db;
	}
}
