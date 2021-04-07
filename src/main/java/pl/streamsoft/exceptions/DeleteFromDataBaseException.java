package pl.streamsoft.exceptions;

public class DeleteFromDataBaseException extends RuntimeException {

	public DeleteFromDataBaseException(String msg) {
		super(msg);
	}

	public DeleteFromDataBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
