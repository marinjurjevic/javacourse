package hr.fer.zemris.java.dao;

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
}