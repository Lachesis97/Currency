package pl.streamsoft.exceptions;

import java.io.IOException;

public class EntityToStringException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntityToStringException(String msg) {
		super(msg);
	}
	
}
