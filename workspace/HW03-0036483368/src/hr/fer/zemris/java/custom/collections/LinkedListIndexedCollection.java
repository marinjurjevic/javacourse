package hr.fer.zemris.java.custom.collections;

/**
 * LinkedListIndexedCollection is extended class of Collection class. It is an
 * implementation of resizable linked list-backed collection of objects.
 * 
 * <b>Note</b> Duplicate members are allowed but storage of null references is
 * not allowed
 */

public class LinkedListIndexedCollection extends Collection {

	/** Current size of the collection, number of nodes in the list */
	private int size;

	/** Reference to the first node of the linked list */
	private ListNode first;

	/** Reference to the last node of the linked list */
	private ListNode last;

	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;

		ListNode(Object value) {
			this.value = value;
		}
	}

	/**
	 * Default constructor of linked list. It creates an empty collection.
	 */
	LinkedListIndexedCollection() {
		first = last = null;
	}

	LinkedListIndexedCollection(Collection other) {
		this();
		addAll(other);
	}

	@Override
	boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	int size() {
		return size;
	}

	/**
	 * Adds given object to this collection at the end. Newly added element
	 * becomes element at the biggest index. Null values will not accepted.
	 * Complexity is O(1).
	 */
	@Override
	void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}

		ListNode node = new ListNode(value);

		// this will be first element added to the list, we must update
		// reference field

		if (size == 0) {
			first = node;
			last = node;
		} else {
			node.next = null;
			node.previous = last;
			last.next = node;
			last = node;

		}

		size++;

	}

	@Override
	boolean contains(Object value) {
		ListNode node = first;
		while (node != null) {
			if (node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}

		return false;
	}

	@Override
	boolean remove(Object value) {
		ListNode node = first;

		while (node != null) {
			if (node.value.equals(value)) {

				if (size == 1) {
					first = last = null;
				} else if (node == first) {
					first = node.next;
					node.next.previous = node.previous;
				} else if (node == last) {
					last = node.previous;
					node.previous.next = node.next;
				} else {
					node.previous.next = node.next;
					node.next.previous = node.previous;
				}

				size--;
				return true;
			}
			node = node.next;
		}

		return false;
	}

	@Override
	Object[] toArray() throws UnsupportedOperationException {
		Object[] array = new Object[size];
		ListNode node = first;
		for (int i = 0; i < size; i++) {
			array[i] = node.value;
			node = node.next;
		}

		return array;
	}

	@Override
	void forEach(Processor processor) {
		ListNode node = first;

		while (node != null) {
			processor.process(node.value);
			node = node.next;
		}

	}

	@Override
	void clear() {
		first = last = null;
		size = 0;
	}

	// new methods for LinkedListIndexedCollection class

	/**
	 * Returns the object that is stored in the list at position index. Valid
	 * indexes are from <code>0</code> to <code>size-1</code>. Complexity will
	 * always be less than O(n/2+1).
	 * 
	 * @param index
	 *            index of element to be returned
	 * @return value object at specified index
	 */
	Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}

		ListNode node;

		// We choose from which end to search index based on its value
		if (index > size / 2) {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		} else {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		}
		return node.value;
	}

	/**
	 * Inserts the given value at the given position in linked-list. Elements
	 * starting from this position are shifted one position towards the end of
	 * the list. Legal positions are <code>0</code> to <code>size</code>.
	 * 
	 * @param value
	 *            value to be inserted into the list
	 * @param position
	 *            position on which value will be inserted
	 */
	void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException("Invalid position to insert.");
		}

		ListNode node = new ListNode(value);

		if (position == 0) {
			if (size == 0) {
				first = node;
				last = node;
			} else {
				first.previous = node;
				node.previous = null;
				node.next = first;
				first = node;
			}
		} else if (position == size) {
			last.next = node;
			node.next = null;
			node.previous = last;
			last = node;
		} else {
			// node which stands on place we want to insert new node
			ListNode posNode = first;
			if (position > size / 2) {
				posNode = last;
				for (int i = size - 1; i > position; i--) {
					posNode = posNode.previous;
				}
			} else {
				posNode = first;
				for (int i = 0; i < position; i++) {
					posNode = posNode.next;
				}
			}

			// now we can insert node on position of posNode
			node.next = posNode;
			node.previous = posNode.previous;
			posNode.previous.next = node;
			posNode.previous = node;

		}
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurence of
	 * the given value or -1 if the value is not found. The equality is
	 * determined using equals method. Complexity is O(n).
	 * 
	 * @param value
	 *            object valuea that method searches for
	 * @return index ordinal number of the object value in linked list
	 */
	int indexOf(Object value) {
		ListNode node = first;
		for (int i = 0; i < size; i++) {
			if (node.value.equals(value)) {
				return i;
			}
			node = node.next;
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection. Legal indexes are 0
	 * to size-1. Elements are shifted towards begin of the collection.
	 * 
	 * @param index
	 *            position in collection of element to be removed.
	 * @throws IllegalArgumentException
	 *             if given index is out of scope
	 */
	void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IllegalArgumentException("Invalid position to insert.");
		}

		ListNode node;

		// searching element at given index
		if (index > size / 2) {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		} else {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		}

		// removing element at given index
		if (size == 1) {
			first = last = null;
		} else if (node == first) {
			first = node.next;
			node.next.previous = node.previous;
		} else if (node == last) {
			last = node.previous;
			node.previous.next = node.next;
		} else {
			node.previous.next = node.next;
			node.next.previous = node.previous;
		}

		size--;
	}
}
