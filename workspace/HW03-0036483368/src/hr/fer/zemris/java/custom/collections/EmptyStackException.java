package hr.fer.zemris.java.custom.collections;

/**
 * EmptyStackException is thrown when methods peek and pop are called.
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyStackException(String message) {
		super(message);
	}
}
