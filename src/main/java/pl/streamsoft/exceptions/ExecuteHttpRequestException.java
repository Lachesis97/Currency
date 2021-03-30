package pl.streamsoft.exceptions;

public class ExecuteHttpRequestException extends RuntimeException {

	public ExecuteHttpRequestException(String msg) {
		super(msg);
	}

	public ExecuteHttpRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
