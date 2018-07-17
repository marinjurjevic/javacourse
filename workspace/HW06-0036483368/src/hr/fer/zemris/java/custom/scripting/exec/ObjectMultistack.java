package hr.fer.zemris.java.custom.scripting.exec;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ObjectMultistack represents a special kind of map. It combines standard map
 * structure that maps keys to stacks. By putting a value in the map, you're
 * actually putting it on top of the stack that is mapped by the key.
 * 
 * @author Marin Jurjevic
 *
 */
public class ObjectMultistack {

	/**
	 * private map object to help build object multistack
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * empty constructor which initialises the map
	 */
	public ObjectMultistack() {
		map = new LinkedHashMap<>();
	}

	/**
	 * Pushes gives value on top of the stack determined by the key
	 * 
	 * @param name
	 *            determines which stack we push value on
	 * @param valueWrapper
	 *            value to push
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry newPeek = new MultistackEntry(valueWrapper);
		MultistackEntry oldPeek = map.put(name, newPeek);

		newPeek.next = oldPeek;
	}

	/**
	 * Pops one element from the stack.
	 * 
	 * @param name
	 *            key to stack we pop value from
	 * @return element on top of the stack or exception is thrown
	 * @throws EmptyMultistackException
	 *             if stack is empty
	 */
	public ValueWrapper pop(String name) {
		if (isEmpty()) {
			throw new EmptyMultistackException();
		}

		MultistackEntry oldPeek = map.get(name);
		map.put(name, oldPeek.next);

		return oldPeek.value;
	}

	/**
	 * Returns one element from the top of the stack.
	 * 
	 * @param name
	 *            key to stack we return value from
	 * @return element on top of the stack or exception is thrown
	 * @throws EmptyMultistackException
	 *             if stack is empty
	 */
	public ValueWrapper peek(String name) {
		if (isEmpty()) {
			throw new EmptyMultistackException();
		}

		return map.get(name).value;
	}

	/**
	 * Checks if this structure is empty.
	 * 
	 * @return true if this collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * Private class for creating stack. Stacks are going to be values,
	 * implemented as single-linked list. Multistack entry is modeling a one
	 * list node. List node contains value and reference to the next node in the
	 * list.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	private static class MultistackEntry {

		/**
		 * entry value
		 */
		private ValueWrapper value;

		/**
		 * reference to the next node in the list
		 */
		private MultistackEntry next;

		/**
		 * Creates new instance of MultistackEntry, modeling a list node.
		 * 
		 * @param value
		 *            value of this node
		 * @param next
		 *            pointer to the next node
		 */
		private MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Creates new node with no next node.
		 * 
		 * @param value value for new element on stack
		 */
		private MultistackEntry(ValueWrapper value) {
			this(value, null);
		}
	}
}
