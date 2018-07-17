package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents an integer constant value.
 * 
 * @author Marin Jurjevic
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Stores one integer value.
	 */
	private final int value;

	/**
	 * Creates new instance of this class storing one integer number.
	 * 
	 * @param value
	 *            integer number
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Returns value of this integer constant.
	 * 
	 * @return value of integer constant
	 */
	public int getValue() {
		return value;
	}
}
