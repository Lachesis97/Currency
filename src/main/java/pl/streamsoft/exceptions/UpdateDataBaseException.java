package pl.streamsoft.exceptions;

public class UpdateDataBaseException extends RuntimeException {

	public UpdateDataBaseException(String msg) {
		super(msg);
	}

	public UpdateDataBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
