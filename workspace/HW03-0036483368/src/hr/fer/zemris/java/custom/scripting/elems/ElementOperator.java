package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents an operator.
 * 
 * @author Marin Jurjevic
 *
 */
public class ElementOperator extends Element {

	/**
	 * Stores one symbol representing an operator
	 */
	private final String symbol;

	/**
	 * Creates new instance of this class containing one symbol.
	 * 
	 * @param symbol
	 *            representation of an operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Returns operator's symbol.
	 * 
	 * @return operator's symbol
	 */
	public String getSymbol() {
		return symbol;
	}
}
