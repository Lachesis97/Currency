package pl.streamsoft.exceptions;

public class CloseConnectionException extends RuntimeException {

	public CloseConnectionException(String msg) {
		super(msg);
	}

	public CloseConnectionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
