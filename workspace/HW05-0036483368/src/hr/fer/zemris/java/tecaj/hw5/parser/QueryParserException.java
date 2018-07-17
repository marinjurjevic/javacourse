package hr.fer.zemris.java.tecaj.hw5.parser;

/**
 * {@code QueryParserException} is an exception derived from Runtime Exception.
 * It is thrown if an error occurs while parsing text.
 * 
 * @author Marin Jurjevic
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QueryParserException() {
		super();
	}

	/**
	 * Creates new SmartScriptParserException with a message.
	 * 
	 * @param message
	 *            additional info about exception.
	 */
	public QueryParserException(String message) {
		super(message);
	}

}
