package pl.streamsoft.exceptions;

public class MappingJsonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MappingJsonException(String msg) {
		super(msg);
	}

	public MappingJsonException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
