package ua.nure.kortyak.FinalDBase.exception;

/**
 * An exception that provides information on an application error. 
 * 
 * @author E.Kortyak
 * 
 */
public class AppException extends Exception {

	private static final long serialVersionUID = -3994043495440299682L;

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
