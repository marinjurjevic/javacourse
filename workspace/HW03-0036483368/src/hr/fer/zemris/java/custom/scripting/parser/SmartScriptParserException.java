package hr.fer.zemris.java.custom.scripting.parser;

/**
 * {@code SmartScriptParserException} is an exception derived from Runtime
 * Exception. It is thrown if an error occurs while parsing text.
 * 
 * @author Marin Jurjevic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {
		super();
	}

	/**
	 * Creates new SmartScriptParserException with a message.
	 * 
	 * @param message
	 *            additional info about exception.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
