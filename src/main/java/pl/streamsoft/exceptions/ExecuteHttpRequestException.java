package pl.streamsoft.exceptions;

public class ExecuteHttpRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExecuteHttpRequestException(String msg) {
		super(msg);
	}

	public ExecuteHttpRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
