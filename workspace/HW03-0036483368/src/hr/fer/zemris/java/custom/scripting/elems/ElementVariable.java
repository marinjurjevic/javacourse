package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents variable.
 * 
 * @author Marin Jurjevic
 *
 */
public class ElementVariable extends Element {

	/**
	 * Stores variable name.
	 */
	private final String name;

	/**
	 * Creates new instance of this class storing variable name.
	 * 
	 * @param name name of variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Returns name of this variable.
	 * 
	 * @return name of variable
	 */
	public String getName() {
		return name;
	}
}
