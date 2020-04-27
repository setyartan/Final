package ua.nure.kortyak.FinalDBase.exception;

/**
 * An exception that provides information on a database access error.
 * 
 * @author E.Kortyak
 * 
 */
public class DBException extends AppException {

	private static final long serialVersionUID = -8165297817122065926L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}
