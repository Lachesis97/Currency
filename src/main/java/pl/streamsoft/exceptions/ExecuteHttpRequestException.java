package pl.streamsoft.exceptions;

import org.apache.http.client.ClientProtocolException;

public class ExecuteHttpRequestException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExecuteHttpRequestException(String msg) {
		super(msg);
	}
}
