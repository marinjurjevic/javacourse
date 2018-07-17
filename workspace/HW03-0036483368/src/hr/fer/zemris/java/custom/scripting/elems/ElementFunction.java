package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single function.
 * 
 * @author Marin Jurjevic
 *
 */
public class ElementFunction extends Element {

	/**
	 * Stores name of this function.
	 */
	private final String name;

	/**
	 * Creates new instance of this class storing a function name.
	 * 
	 * @param name
	 *            function name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

	/**
	 * Returns name of this function.
	 * 
	 * @return function name
	 */
	public String getName() {
		return name;
	}
}
