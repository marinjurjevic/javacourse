package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes in Node hierarchy.
 * 
 * @author Marin Jurjevic
 *
 */
public class Node {

	private ArrayIndexedCollection array;
	private boolean initFlag = false;

	/**
	 * Adds given child to internally managed collection of children.
	 * 
	 * @param child
	 *            node to be added to collection
	 */
	public void addChildNode(Node child) {
		if (initFlag == false) {
			array = new ArrayIndexedCollection();
			initFlag = true;
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
		return (Node) array.get(index);
	}
}
