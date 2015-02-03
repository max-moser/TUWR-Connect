package data;

import java.util.HashMap;
import java.util.List;

public class InputParser {
// TODO: ALLES
	
	//variablen für xml-daten -> nicht immer einlesen. nur beim erstellen einer neuen connection -> einem neuen inpurparser objekt;
	private List<String> namen; 	//position == position der daten udp
	private List<String> einheiten;	//position == position der daten udp
	
	public InputParser(){
		
		//udp-xml read
		
	}
	
	public HashMap<String, Double> pars(byte[] data){
		Double.parseDouble(new String(data));
		//TODO
		return null;
	}
	
	public List<String> getNames(){
		return namen;
	}
	
	public List<String> getUnits(){
		return einheiten;
	}
}
