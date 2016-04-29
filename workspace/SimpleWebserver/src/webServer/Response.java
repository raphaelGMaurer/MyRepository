package webServer;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Response {

	public InputStream addHeader(InputStream inRessource, String header){
		try{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer;
			
			//read in header
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(header);
			writer.println();
			//add inRessource
			do{
			buffer = new byte[512];
			int pos = inRessource.read(buffer);
			if(pos==0) break;
			outStream.write(buffer);
			}
			while(inRessource.read(buffer) != -1);
			
			ByteArrayInputStream readInStream = new ByteArrayInputStream(outStream.toByteArray());
			return readInStream;
		} 
		catch(IOException e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public InputStream setResponse(InputStream ressource){
		try{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer;
			
			do{
			buffer = new byte[512];
			int pos = ressource.read(buffer);
			if(pos==0) break;
			outStream.write(buffer);
			}
			while(ressource.read(buffer) != -1);
			
			ByteArrayInputStream readInStream = new ByteArrayInputStream(outStream.toByteArray());
			return readInStream;
		} 
		catch(IOException e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public void sendResponse(InputStream inRessource, Socket dest){
		try {
			OutputStream outStream = dest.getOutputStream();
			byte[] buffer;
			
			do{
				buffer = new byte[512];
				int pos = inRessource.read(buffer);
				if(pos==0) break;
				outStream.write(buffer);
				}
				while(inRessource.read(buffer) != -1);
			
			outStream.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
