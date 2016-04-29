package webServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class HttpServer {
	ServerSocket serverS;
	ArrayList<String> header = new ArrayList<>();
	
	public HttpServer(int port){
		try {
			serverS = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void init(){
		
		
		try {
			while(true){
			//ACCEPT CONNECTION
			System.out.println("Waiting for connections");
			Socket con =serverS.accept();
			
			//READ HEADER
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while (!(inputLine = in.readLine()).equals("")){
				header.add(inputLine);
				System.out.println(inputLine);
			}
			System.out.println("header recieved");
			//TEST
			//convert to string arr for Request class
			String[] headerStr = new String[header.size()];
			for(int i=0; i< header.size();i++){
				headerStr[i] = header.get(i);
			}
			Request nextRequest = new Request(headerStr);
			System.out.println("METHOD: " + nextRequest.getMethod());
			System.out.println("HOST: " + nextRequest.getHeaderParts().get("Host"));
			
			//PROCESS HEADER
			//	method
			//	
			
			//RETURN RESPONSE
			//sendDummyResponse(con);
			Response myResp = new Response();
			File f = new File("/home/raphi/javaTests/testRessource.html");
			FileInputStream fileIn = new FileInputStream(f);
			String myHeader = "HTTP/1.0 200 OK" + "\nContent-Type: text/html" + "\nServer: Bot";
			InputStream fullResponse = myResp.setResponse(fileIn);
			fullResponse = myResp.addHeader(fullResponse, myHeader);
			myResp.sendResponse(fullResponse, con);
			
			//close connection
			in.close(); 
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendDummyResponse(Socket con){
		try {
			PrintWriter out = new PrintWriter(con.getOutputStream());
			out.println("HTTP/1.0 200 OK");
	        out.println("Content-Type: text/html");
	        out.println("Server: Bot");
	        // this blank line signals the end of the headers
	        out.println("");
	        // Send the HTML page
	        out.println("<H1>Welcome to the Ultra Mini-WebServer</H2>");
	        out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendResponse(Request req){
		
		
	}
	
	

}
