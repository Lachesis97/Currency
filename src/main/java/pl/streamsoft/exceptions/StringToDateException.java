package pl.streamsoft.exceptions;

public class StringToDateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StringToDateException(String msg) {
		super(msg);
	}

	public StringToDateException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
