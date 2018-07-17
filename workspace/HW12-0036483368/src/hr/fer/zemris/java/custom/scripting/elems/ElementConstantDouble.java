package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which representates a double constant element.
 * 
 * @author Marin Jurjevic
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Stores one double value.
	 */
	private final double value;

	/**
	 * Creates new instance of this class storing one double number.
	 * 
	 * @param value
	 *            double number.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * Returns value of this double constant.
	 * 
	 * @return value of double constant
	 */
	public double getValue() {
		return value;
	}
}
