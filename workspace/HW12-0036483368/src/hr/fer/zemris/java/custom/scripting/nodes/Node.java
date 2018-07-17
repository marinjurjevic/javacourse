package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;

/**
 * Base class for all graph nodes in Node hierarchy.
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class Node {

	/**
	 * List of children nodes
	 */
	private ArrayList<Node> array;
	
	/**
	 * Adds given child to internally managed collection of children.
	 * 
	 * @param child
	 *            node to be added to collection
	 */
	public void addChildNode(Node child) {
		if (array == null) {
			array = new ArrayList<>();
		}

		array.add(child);

	}

	/**
	 * Returns a number of (direct) children.
	 * 
	 * @return number of children of this node
	 */
	public int numberOfChildren() {
		if (array == null) {
			return 0;
		}

		return array.size();
	}

	/**
	 * Returns child of this node at specified index.
	 * 
	 * @param index index in the parent's array of children
	 * @return selected child
	 */
	public Node getChild(int index) {
		return array.get(index);
	}
	
	/**
	 * Implementation of visitor pattern. This method must implement all 
	 * extended classes.
	 * @param visitor visitor object that can communicate with subclasses
	 */
	public abstract void accept(INodeVisitor visitor);
}
