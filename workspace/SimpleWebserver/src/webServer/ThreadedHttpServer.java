package webServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadedHttpServer {
	
	final int QUEUE_CAPACITY = 10;
	final int THREAD_POOL_SIZE = 5;
	ArrayBlockingQueue<Socket> connectionQueue;
	ServerSocket serverS;
	
	public ThreadedHttpServer(int port){
		try {
			serverS = new ServerSocket(port);
			connectionQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
			System.out.println("Server started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init(){
		
		//INIT WORKER THREADS
		for(int i=0; i< THREAD_POOL_SIZE;i++){
			Thread nextThread = new Thread(new ConnectionHandler());
			nextThread.setDaemon(true);
			nextThread.start();
			
		}
		System.out.println("Threadpool started");
		//ACCEPT CONNECTIONS
		while(true){
			try {
				Socket nextConnection = serverS.accept();
				//PUT IN QUEUE
				connectionQueue.put(nextConnection);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	private void processGet(Request request){
		
	}
	
	private void processPost(Request request){
		
	}
	
	class ConnectionHandler implements Runnable{
		Socket con;

		@Override
		public void run() {
			
			
			while(true){ //keep executing worker thread (threadpool)
				try {
					this.con = connectionQueue.take();
					ArrayList<String> header = new ArrayList<>();
					//READ HEADER -> CREATE REQUEST OBJECT
					
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					while (!(inputLine = in.readLine()).equals("")){
						header.add(inputLine);
						//System.out.println(inputLine);
					}
					
					String[] headerStr = new String[header.size()];
					for(int i=0; i< header.size();i++){
						headerStr[i] = header.get(i);
					}
					Request nextRequest = new Request(headerStr);
					
					//CREATE RESPONSE OBJECT
					Response myResp = new Response();
					
					//PROCESS REQUEST
					//sendDummyResponse(con);
					File f = new File("/home/raphi/javaTests/testRessource.html");
					FileInputStream fileIn = new FileInputStream(f);
					String myHeader = "HTTP/1.0 200 OK" + "\nContent-Type: text/html" + "\nServer: Bot";
					InputStream fullResponse = myResp.setResponse(fileIn);
					fullResponse = myResp.addHeader(fullResponse, myHeader);
					
					
					//SEND RESPONSE
					myResp.sendResponse(fullResponse, con);
					
					//close connection
					con.close();
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue; //keep executing worker thread (threadpool)
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
		}
		
	}

}
