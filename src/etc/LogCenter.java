package etc;

import java.util.logging.Logger;

public class LogCenter {
	
	private static LogCenter instance;
	private Logger logger;
	
	private LogCenter(){
		logger = Logger.getLogger("root");
	}
	
	/**
	 * Returns the logger for this instance.
	 * @return Logger
	 */
	public Logger getLogger(){
		return logger;
	}
	
	public static LogCenter getInstance(){
		if(instance == null){
			instance = new LogCenter();
		}
		return instance;
	}

}
