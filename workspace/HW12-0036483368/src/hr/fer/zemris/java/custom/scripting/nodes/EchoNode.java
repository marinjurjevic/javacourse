package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * EchoNode represents a command which generates some textual output
 * dynamically.
 * 
 * @author Marin Jurjevic
 *
 */
public class EchoNode extends Node {

	/**
	 * Stores elements
	 */
	private final Element[] elements;

	/**
	 * Creates new EchoNode with specified elements
	 * 
	 * @param elements array of elements that Echo consists
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * 
	 * @return elements in this EchoNode
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$=");
		for (int i = 0, size = elements.length; i < size; i++) {
			sb.append(" " + elements[i].asText());
		}
		sb.append(" $}");

		return sb.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
