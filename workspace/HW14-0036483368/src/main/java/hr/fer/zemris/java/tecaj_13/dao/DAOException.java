package hr.fer.zemris.java.tecaj_13.dao;

/**
 * DAOException represents exception encapsulating all exception occuring while
 * working with DAO object.
 * 
 * @author Marin Jurjevic
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DAOException() {
	}

	/**
	 * Creates new DAO Exception with given parameters.
	 * 
	 * @param message
	 *            exceptin message
	 * @param cause
	 *            exception cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Creates new DAO Exception with given parameters.
	 * 
	 * @param message
	 *            exception message
	 * @param cause
	 *            exception cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates new DAO Exception with specified message.
	 * 
	 * @param message
	 *            exception message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Creates new DAO Exception with specific cause.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}