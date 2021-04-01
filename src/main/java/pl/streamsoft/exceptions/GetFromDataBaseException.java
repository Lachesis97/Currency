package pl.streamsoft.exceptions;

public class GetFromDataBaseException extends RuntimeException {

	public GetFromDataBaseException(String msg) {
		super(msg);
	}

	public GetFromDataBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
