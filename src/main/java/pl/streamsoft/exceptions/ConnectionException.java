package pl.streamsoft.exceptions;

public class ConnectionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionException(String msg) {
		super(msg);
	}

	public ConnectionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
