package pl.streamsoft.exceptions;

public class DateValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateValidationException(String msg) {
		super(msg);
	}

	public DateValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
