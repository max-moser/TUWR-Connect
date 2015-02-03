package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {
	private String dbDriver;
	private String db;
	private Connection conn;
	private InputParser pars;
	private RunnableConnection runCon;

	public SQLConnection(RunnableConnection runCon, InputParser pars){
		this.pars = pars;
		this.runCon = runCon;
		
		try {
			dbDriver = "org.sqlite.JDBC";
    		db = "jdbc:sqlite:racing.db";
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();
		    stat.executeUpdate("CREATE TABLE IF NOT EXISTS data_" + runCon.getCreateDate() + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		    		addDataCreate()
		    		+ "timestamp INTEGER);");
		    stat.executeUpdate("CREATE TABLE IF NOT EXISTS units_" + runCon.getCreateDate() + " (id INTEGER PRIMARY KEY AUTOINCREMENT, units VARCHAR(20), name VARCHAR(30));");
    	} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertUnits() throws SQLException{
		for(int i = 0; i < pars.getNames().size(); i++){
			PreparedStatement prepstat = conn.prepareStatement("INSERT INTO units_" + runCon.getCreateDate() + "(unit, name) VALUES(?, ?);");
			
			prepstat.setString(1, pars.getNames().get(i));
			prepstat.setString(2, pars.getUnits().get(i));
			
			prepstat.executeUpdate();		
		}
	}
	
	public void insertData(ConnectData dat) throws SQLException{
		PreparedStatement prepstat = conn.prepareStatement("INSERT INTO data_" + runCon.getCreateDate() + "(" + addDataInsert() + "timestamp) VALUES(" + addQmData() + ", ?);");
		int i = 0;
		for(i = 0; i < pars.getNames().size(); i++){
			prepstat.setDouble(i, dat.getValueMap().get(pars.getNames().get(i)));
		}

		prepstat.setDate(++i, dat.getTimeStamp());
		prepstat.executeUpdate();
	}
	
	public ResultSet select(String stmt) throws SQLException{
		PreparedStatement prepstat = conn.prepareStatement(stmt);
		return prepstat.executeQuery();
	}
	
	private String addDataCreate(){
		String ret = "";
		
		for(String name : pars.getNames()){
			ret += name + " NUMERIC, ";
		}
		
		return ret;
	}
	
	private String addDataInsert(){
		String ret = "";
		
		for(String name : pars.getNames()){
			ret += name + ", ";
		}
		
		return ret;
	}
	
	private String addQmData(){
		String ret = "";
		
		for(String name : pars.getNames()){
			ret += "?, ";
		}
		
		return ret.substring(0, ret.length() - 2);
	}
}
