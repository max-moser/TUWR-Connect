package data;

import java.sql.Date;

public class ConnectData {

	private double value;
	private Date timestamp;
	
	public ConnectData(double value, Long timestamp){
		this.value = value;
		this.timestamp = new Date(timestamp);
	}
	
	public ConnectData(String unparsedData){
		String[] parsedData = unparsedData.split("|");
		value = Double.parseDouble(parsedData[0]);
		timestamp = new Date(Long.parseLong(parsedData[1]));
	}
	
	public double getValue() {
		return value;
	}

	public Date getTimeStamp() {
		return timestamp;
	}
}
