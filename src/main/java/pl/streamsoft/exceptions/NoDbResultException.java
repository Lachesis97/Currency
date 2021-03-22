package pl.streamsoft.exceptions;

public class NoDbResultException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDbResultException(String msg) {
		super(msg);
	}

	public NoDbResultException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
