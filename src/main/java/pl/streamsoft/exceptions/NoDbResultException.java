package pl.streamsoft.exceptions;

public class NoDbResultException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public NoDbResultException(String msg) {
		super(msg);
	}
}
