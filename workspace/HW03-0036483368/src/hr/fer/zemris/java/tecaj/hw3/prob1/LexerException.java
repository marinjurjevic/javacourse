package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * {@code LexerException} is an exception derived from RuntimeException.
 * LexerException is thrown if an error has occured while lexic analysing.
 * 
 * @author Marin Jurjevic
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new lexer exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructs a new lexer exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link #initCause}.
	 *
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public LexerException(String message) {
		super(message);
	}

}
