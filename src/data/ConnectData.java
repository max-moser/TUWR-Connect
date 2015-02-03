package data;

import java.sql.Date;
import java.util.HashMap;

public class ConnectData {

	private Date timestamp;
	private HashMap<String, Double> data;
	
	public ConnectData(HashMap<String, Double> data, Long timestamp){
		this.data = data;
		this.timestamp = new Date(timestamp);
	}
	
	public HashMap<String, Double> getValueMap() {
		return data;
	}

	public Date getTimeStamp() {
		return timestamp;
	}
}
