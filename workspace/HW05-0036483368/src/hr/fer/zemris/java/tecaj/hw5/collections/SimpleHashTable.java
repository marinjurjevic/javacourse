package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SimpleHashtable represents a simple hashing table which offers storage of
 * parameterized set of pairs (key,value). Parameters K and V represent types of
 * key and value respectively. Null keys are not allowed in this collection, but
 * values are.
 * 
 * @author Marin Jurjevic
 *
 * @param <K>
 *            type of key
 * @param <V>
 *            type of value
 */
public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K, V>> {

	/**
	 * initial capacity of hashtable
	 */
	private final static int INITIAL_CAPACITY = 16;

	/**
	 * ratio of used and total slots allowed in hashtable
	 */
	private final static double SLOTS_RATIO = 0.75;
	/**
	 * number of stored pairs (key,value) in this hashtable
	 */
	private int size;

	/**
	 * slots used in this hashtable, created in constructor
	 */
	private TableEntry<K, V>[] table;

	/**
	 * number of slots in this hashtable
	 */
	private int totalSlots;

	/**
	 * number of used slots in hashtable, note that this number will never be
	 * bigger or equal to 75% of total slots.
	 */
	private int usedSlots;

	/**
	 * tracks modifications inside this collection
	 */
	private int modificationCount;

	/**
	 * Creates new hash table with default number of slots which is equal to 16.
	 */
	public SimpleHashTable() {
		this(INITIAL_CAPACITY);
	}

	/**
	 * Creates new hashtable with specified number of slots. Hashtable keeps
	 * slots so that number of slots is equal to the power of 2. This will
	 * result in adjusting actualy capacity to the closest corresponding
	 * potention that is higher or equal to specified capacity.
	 * 
	 * @param capacity
	 *            specified capacity of hashtable
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater than 0");
		}
		this.totalSlots = upperPowerOfTwo(capacity);
		table = new TableEntry[totalSlots];
	}

	/**
	 * Puts given pair in hashtable.
	 * 
	 * @param key
	 *            specified key
	 * @param value
	 *            specified value
	 * @throws IllegalArgumentException
	 *             if key is null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null!");
		}
		int slotIndex = getKeyHash(key, totalSlots);
		TableEntry<K, V> head = table[slotIndex];
		TableEntry<K, V> nextHead;
		TableEntry<K, V> newEntry = new TableEntry<K, V>(key, value);

		if (head == null) { // first entry in this slot
			table[Math.abs(key.hashCode()) % totalSlots] = newEntry;
			size++;
			usedSlots++; //

			if (((double) usedSlots) / totalSlots >= SLOTS_RATIO) {
				resize(this);
			}

			modificationCount++;
			return;
		} else {
			if (head.key.equals(key)) { // check if head of list is maybe the
										// key?
				head.value = value;
				return;
			}
		}

		// we check one element after head so we dont lose reference
		nextHead = head.next;
		while (nextHead != null) { // go to end of list,
			if (nextHead.key.equals(key)) { // check if entry is already in the
											// list
				nextHead.value = value;
				return;
			}
			head = head.next;
			nextHead = head.next;
		}

		head.next = newEntry; // no key in hash, add it to the end of the list
								// in this slot

		modificationCount++;
		size++;
	}

	/**
	 * Returns value stored under specified key. If there is no such key in the
	 * table <code>null</code> is returned.
	 * 
	 * @param key
	 *            valid key in this hashtable
	 * @return value stored under specified key
	 */
	public V get(Object key) {
		if (key != null) {
			int slotIndex = getKeyHash(key, totalSlots);
			TableEntry<K, V> head = table[slotIndex];

			if (head != null) { // if head is null pop out
				while (head != null) {
					if (head.key.equals(key)) { // check for key
						return head.value;
					}

					head = head.next;
				}
			}
		}

		return null;
	}

	/**
	 * @return number of stored pairs (key,value) in this hashtable
	 */
	public int size() {
		return size;
	}

	/**
	 * Searches hashatable for specified key.
	 * 
	 * @param key
	 *            searched key
	 * @return true if key is in hash table, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key != null) {
			int slotIndex = getKeyHash(key, totalSlots);
			TableEntry<K, V> head = table[slotIndex];

			if (head != null) {
				while (head != null) {
					if (head.key.equals(key)) { // check for key
						return true;
					}

					head = head.next;
				}
			}
		}

		return false;
	}

	/**
	 * Searches hashatable for specified value.
	 * 
	 * @param value
	 *            searched value in hashtable
	 * @return true if value is in hash table, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0, cap = table.length; i < cap; i++) {
			TableEntry<K, V> head = table[i];

			if (head != null) {
				while (head != null) {
					if (head.value.equals(value)) { // check for value
						return true;
					}

					head = head.next;
				}
			}
		}

		return false;
	}

	/**
	 * Removes pair (key,value) from this hashtable if the key is present.
	 * 
	 * @param key unique key representing this value.
	 */
	public void remove(Object key) {
		if (key != null) {
			int slotIndex = getKeyHash(key, totalSlots);
			TableEntry<K, V> head = table[slotIndex];
			TableEntry<K, V> headNext;

			if (head != null) {
				headNext = head.next;

				if (head.key.equals(key)) { // if head entry contains key
					if (headNext == null) {
						table[slotIndex] = null;
						usedSlots--;
					} else {
						table[slotIndex] = headNext;
					}

					modificationCount++;
					size--;
					return;
				}

				while (headNext != null) {
					if (headNext.key.equals(key)) {
						head.next = headNext.next;
						size--;
						modificationCount++;
						return;
					}
					head = head.next;
					headNext = head.next;
				}
			}
		}
	}

	/**
	 * @return <code>true</code> if there are no pairs in hashtable, false
	 *         otherwise
	 */
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0, cap = table.length; i < cap; i++) {
			TableEntry<K, V> head = table[i];

			while (head != null) {
				sb.append(head.key.toString() + "=" + head.value.toString() + ", ");
				head = head.next;

			}
		}

		if (sb.length() != 1) { // if there are pairs , remove ", " from last
								// pair appended
			sb.delete(sb.length() - 2, sb.length());
		}
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Clears entire hash table and reset its size to 0. Capacity of table slots
	 * will not change.
	 */
	public void clear() {
		for (int i = 0, slots = table.length; i < slots; i++) {
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}

	// nested static class for list data structure
	/**
	 * TableEntry represents a node(entry) of linked list. Linked list is used
	 * for possible overflows in table slots. Key and value will represent data
	 * of this list elements.
	 * 
	 * @author Marin Jurjevic
	 *
	 * @param <K>
	 *            type of key
	 * @param <V>
	 *            type of value
	 */
	public static class TableEntry<K, V> {
		/**
		 * key used to retrieve stored value
		 */
		private K key;

		/**
		 * stored value in hashtable
		 */
		private V value;

		/**
		 * reference to the next entry in this list
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructs new entry with specified key and value.
		 * 
		 * @param key
		 *            key used to retrieve stored value
		 * @param value
		 *            stored value in hashtable
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * @return value stored in this entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets new value for this entry.
		 * @param value new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * @return key of this entry
		 */
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return "[key=" + key + ", value=" + value + "]";
		}

	}

	// private static methods used for hashtable modification

	/**
	 * Calculates closest potention of number 2 which is higher or equal to this
	 * number.
	 * 
	 * @param number
	 *            number which we convert
	 * @return potention of number 2
	 */
	private static int upperPowerOfTwo(int number) {
		number--;
		number |= number >> 1;
		number |= number >> 2;
		number |= number >> 4;
		number |= number >> 8;
		number |= number >> 16;
		number++;
		return number;
	}

	/**
	 * Calculates hash code of given hashtable key.
	 * 
	 * @param key
	 *            hashtable
	 * @param totalSlots
	 *            number of total slots used in the table
	 * @return hash code of specified key
	 */
	private static <K> int getKeyHash(K key, int totalSlots) {
		return Math.abs(key.hashCode()) % totalSlots;
	}

	/**
	 * Resizes this hashtable to have double more slots in internal table of
	 * list references.
	 * 
	 * @param sh
	 *            instance of <code>SimpleHashtable</code> class
	 */
	@SuppressWarnings("unchecked")
	private static <K, V> void resize(SimpleHashTable<K, V> sh) {
		sh.totalSlots = 2 * sh.totalSlots; // new slot table
		sh.usedSlots = 0;
		sh.size = 0;

		TableEntry<K, V>[] oldTable = sh.table; // remember old table to iterate
												// over
		sh.table = new TableEntry[sh.totalSlots]; // create new table

		for (int i = 0, cap = oldTable.length; i < cap; i++) {
			TableEntry<K, V> head = oldTable[i];

			if (head != null) {
				while (head != null) {
					sh.put(head.key, head.value);
					head = head.next;
				}
			}
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Implementation of an iterator over <code>SimpleHashtable</code>.
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashTable.TableEntry<K, V>> {

		/**
		 * Keeps a copy of a number of modification in hashtable to keep track
		 * if the table has been changed from outside while iterating.
		 */
		private int modSnapShot = modificationCount;

		/**
		 * counter for counting till the end of iteration
		 */
		private int counter = size;

		/**
		 * current entry to be returned by next() method
		 */
		private TableEntry<K, V> currentEntry;

		/**
		 * help pointer to the next entry for easy iterating
		 */
		private TableEntry<K, V> nextEntry;

		/**
		 * help variable for counting out next slot of the list
		 */
		private int slotIndex = -1;

		@Override
		public boolean hasNext() {
			checkForModification();
			return counter > 0;
		}

		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (nextEntry == null) { // find next slot
				slotIndex++;
				while (table[slotIndex] == null) {
					slotIndex++;
				}
				// update entries
				currentEntry = table[slotIndex];
				nextEntry = currentEntry.next;
			} else { // continue search through list and update entries
				currentEntry = nextEntry;
				nextEntry = currentEntry.next;

			}

			counter--;
			return currentEntry;

		}

		/**
		 * Removes from the underlying collection the last element returned by
		 * this iterator. This method can be called only once per call to next.
		 * If the underlying collection is modified while the iteration is in
		 * progress this method will throw ConcurrentModificationException.
		 * 
		 * @throws IllegalStateException
		 *             if this was method was already called for current next()
		 *             call
		 * @throws ConcurrentModificationException
		 *             if this iterator's collection has been changed from
		 *             outside iterator
		 */
		@Override
		public void remove() {
			if (currentEntry == null) {
				throw new IllegalStateException();
			}
			checkForModification();

			SimpleHashTable.this.remove(currentEntry.key);
			currentEntry = null;
			modSnapShot++;
		}

		/**
		 * Checks if there were any modification by anyone else than iterator in
		 * this collection.
		 */
		private void checkForModification() {
			if (modSnapShot != modificationCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
