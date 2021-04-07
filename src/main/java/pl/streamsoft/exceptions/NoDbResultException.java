package pl.streamsoft.exceptions;

public class NoDbResultException extends RuntimeException {

	public NoDbResultException(String msg) {
		super(msg);
	}

	public NoDbResultException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
