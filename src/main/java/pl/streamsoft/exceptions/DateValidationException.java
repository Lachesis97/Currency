package pl.streamsoft.exceptions;

public class DateValidationException extends RuntimeException {

	public DateValidationException(String msg) {
		super(msg);
	}

	public DateValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
