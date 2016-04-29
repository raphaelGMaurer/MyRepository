package webServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Request {
	
	ArrayList<String> header = new ArrayList<>();
	
	public Request(String headerStr, Socket con){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while (!(inputLine = in.readLine()).equals("")){
				header.add(inputLine);
				//System.out.println(inputLine);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	String[] requestString;
	//method localpath/ressource HTTP/1.0
	
	public Request(String[] requestString){
		this.requestString = requestString;
	}
	
	public String getInitialLine(){
		return requestString[0];
	
	}
	
	public String getMethod(){
		return getInitialLine().substring(0,getInitialLine().indexOf(' '));
	}
	
	public InputStream getRequestBody(){
		String method = getMethod();
		if(method.equals("GET")) return null;
		
		//TO DO
		return null;
	}
	
	public String getRequestedRessourcePath(){
		return null;
	}
	
	public HashMap<String,String> getHeaderParts(){
		/**
		The Desciption of the method to explain what the method does
		@param the parameters used by the method
		@return the value returned by the method
		@throws what kind of exception does this method throw
		*/
		
		HashMap<String,String>header = new HashMap<>();
		String[] headerParts = new String[this.requestString.length-1];
		for(int i=1; i< this.requestString.length-1;i++){
			headerParts[i-1] = this.requestString[i];
		
		}
		for(String str : headerParts){
			if(str == null) break;
			if(str.indexOf(':') == -1) break;
			String key = str.substring(0,str.indexOf(':'));
			//System.out.println("KEY= " + key);
			String value = str.substring(str.indexOf(':')+1, str.length());
			//System.out.println("VALUE= " + value);
			header.put(key, value);
		}
		
		return header;
	}
	


}
