package pl.streamsoft.exceptions;

public class CloseConnectionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CloseConnectionException(String msg) {
		super(msg);
	}

	public CloseConnectionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
