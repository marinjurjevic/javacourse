package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * TextNode represents a piece of textual data.
 * 
 * @author Marin Jurjevic
 *
 */
public class TextNode extends Node {

	/**
	 * Stores text.
	 */
	private final String text;

	/**
	 * Creates new {@code TextNode} containing text
	 * 
	 * @param text
	 *            text to be stored in TextNode
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns text stored in this node.
	 * 
	 * @return text that this node contains
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		String textReturn = text.replace("\\","\\\\").replace("{", "\\{");
		return textReturn;
	}
}
