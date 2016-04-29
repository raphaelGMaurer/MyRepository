package plugins;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class Plugin implements BIF.SWE1.interfaces.Plugin {
	
	Request myRequest;

	public Request getMyRequest() {
		return myRequest;
	}

	public void setMyRequest(Request myRequest) {
		this.myRequest = myRequest;
	}
	
	/*
	 * CODES
	 * 1= GET
	 * 2= POST
	 * 0 = CANNOT HANDLE
	 */

	@Override
	public float canHandle(Request arg0) {
		// TODO Auto-generated method stub
		if(arg0.getMethod().equals("GET")) return 1;
		else return 0;
	}

	@Override
	public Response handle(Request arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
