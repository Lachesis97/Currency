package pl.streamsoft.exceptions;

public class EntityToStringException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityToStringException(String msg) {
		super(msg);
	}

	public EntityToStringException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
