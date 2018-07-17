package hr.fer.zemris.java.custom.collections;

/**
 * This class is representation of a simple stack. It has classic methods for
 * working with stack.
 */
public class ObjectStack {

	/**
	 * Internal array which will store elements of stack.
	 */
	private ArrayIndexedCollection array;

	/**
	 * Creates new empty stack.
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection();
	}

	/**
	 * Method to determine if collection is empty or not.
	 * 
	 * @return true if collection contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		if (array.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Returns number of elements.
	 * 
	 * @return number of currently stored objects on this stack
	 */
	public int size() {
		return array.size();
	}

	/**
	 * Pushes value to the top of the stack. Null references are not allowed.
	 * 
	 * @param value
	 *            value to be pushed on top of the stack
	 * @throws IllegalArgumentException
	 *             null reference is passed as argument
	 */
	public void push(Object value) {
		array.add(value);
	}

	/**
	 * Removes last value pushed on stack from stack and returns it. If stack is
	 * empty when method is called, an exception occurs.
	 * 
	 * @return object on the top of the stack
	 * @throws EmptyStackException
	 *             when stack is empty
	 */
	public Object pop() {
		if (array.size() == 0) {
			throw new EmptyStackException("Stack is empty!");
		}

		Object value = array.get(array.size() - 1);
		array.remove(array.size() - 1);

		return value;
	}

	/**
	 * Returns top element on the stack, without actually removing it from the
	 * stack.
	 * 
	 * @return object on the top of the stack
	 * @throws EmptyStackException
	 *             when stack is empty
	 */
	public Object peek() {
		if (array.size() == 0) {
			throw new EmptyStackException("Stack is empty!");
		}

		return array.get(array.size() - 1);
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		array.clear();
	}

}
