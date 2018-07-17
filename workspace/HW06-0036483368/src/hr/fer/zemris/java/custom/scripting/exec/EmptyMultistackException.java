package hr.fer.zemris.java.custom.scripting.exec;

/**
 * EmptyMultistackException is thrown when methods peek and pop are called on
 * empty stack.
 */
public class EmptyMultistackException extends RuntimeException {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Throws new EmptyMultistackException without message.
	 */
	public EmptyMultistackException() {
		super();
	}

	/**
	 * Throws new EmptyMultistackException with specified message.
	 * @param message detailed message about exception
	 */
	public EmptyMultistackException(String message) {
		super(message);
	}
}
