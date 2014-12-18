package datacenter;

public abstract class RunnableConnection implements Runnable{
	//run(); durch runnable
	protected static int conCnt = 0;
	protected int id;
	protected SQLConnection db;
	protected DataCenter dc;
	
	public RunnableConnection(DataCenter dc){
		id = conCnt++;
		this.dc = dc;
		db = new SQLConnection();
	}
	
	public SQLConnection getSQLConnection(){
		return db;
	}
}
