package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	private String attributeLine;
	private String dataTypeLine;
	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		String line=" ";
		try{
			if(!getAttribute){
				attributeLine = br.readLine();
				dataTypeLine = br.readLine();
				getAttribute=true;
			}
			
		line = br.readLine();	
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(line==null){
			return null;
		}
		tuple = new Tuple(attributeLine,dataTypeLine,line);
		tuple.setAttributeName();
		tuple.setAttributeType();
		tuple.setAttributeValue();
		return tuple;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}