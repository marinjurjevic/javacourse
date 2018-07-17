package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents a single string.
 * 
 * @author Marin Jurjevic
 *
 */
public class ElementString extends Element {

	/**
	 * Stores value of string.
	 */
	private final String value;

	/**
	 * Creates new instance of this class containing value of a string.
	 * 
	 * @param value value of a string element
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return "\""+value.replace("\\","\\\\")
						 .replace("\"", "\\\"")+"\"";
	}

	/*
	 * 
						 .replace("\\\\n","\\n")
						 .replace("\\\\r","\\r")
						 .replace("\\\\r","\\r")
	 */
	
	/**
	 * Returns value of this string.
	 * 
	 * @return value of this string
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
