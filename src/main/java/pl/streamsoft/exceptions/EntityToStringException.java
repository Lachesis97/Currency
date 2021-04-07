package pl.streamsoft.exceptions;

public class EntityToStringException extends RuntimeException {

	public EntityToStringException(String msg) {
		super(msg);
	}

	public EntityToStringException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
