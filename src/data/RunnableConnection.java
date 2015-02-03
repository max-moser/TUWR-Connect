package data;

import java.util.Date;

public abstract class RunnableConnection implements Runnable{
	//run(); durch runnable
	protected static int conCnt = 0;
	protected int id;
	protected DataCenter dc;
	protected InputParser pars;
	private Date createDate;
	
	public RunnableConnection(DataCenter dc){
		id = conCnt++;
		createDate = new Date();
		this.dc = dc;
		pars = new InputParser();
	}
	
	public int getId(){
		return id;
	}
	
	protected InputParser getParser(){
		return pars;
	}

	public String getCreateDate() {
		return Double.toString(createDate.getTime());
	}
}
