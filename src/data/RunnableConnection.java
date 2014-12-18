package data;

public abstract class RunnableConnection implements Runnable{
	//run(); durch runnable
	protected static int conCnt = 0;
	protected int id;
	protected DataCenter dc;
	
	public RunnableConnection(DataCenter dc){
		id = conCnt++;
		this.dc = dc;
	}
	
	public int getId(){
		return id;
	}
}
