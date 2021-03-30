package pl.streamsoft.exceptions;

public class MappingJsonException extends RuntimeException {

	public MappingJsonException(String msg) {
		super(msg);
	}

	public MappingJsonException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
