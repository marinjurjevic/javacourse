package hr.fer.zemris.java.custom.collections;

/**
 * ArrayIndexedCollection is extended class of Collection class. It is an
 * implementation of resizable array-backed collection of objects.
 * 
 * <b>Note</b> Duplicate members are allowed but storage of null references is
 * not allowed
 */
public class ArrayIndexedCollection extends Collection {

	/** Default capacity used for default constructor */
	private static final int DEFAULT_CAPACITY = 16;

	/** Number of current elements in the array */
	private int size;

	/** Number of maximum elements in the currently allocated array */
	private int capacity;

	/** Structure array is used for internal storage of elements */
	private Object[] elements;

	/**
	 * Main constructor for ArrayIndexed collection. This constructor will
	 * create an instance from an existing collection and set its capacity to
	 * initialCapacity paramter except if existing collection is bigger than
	 * initialCapacity value. In that case, capacity is set to current capacity
	 * of accepted collection.
	 * 
	 * @param other
	 *            collection whose object members will be copied to this array
	 * @param initialCapacity
	 *            starting capacity to which array will be allocated
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) {
			capacity = initialCapacity;
			elements = new Object[capacity];
		} else {
			if (initialCapacity < other.size()) {
				capacity = other.size();
				size = capacity;

			} else {
				capacity = initialCapacity;
				size = other.size();
			}

			elements = other.toArray();
		}
	}

	/**
	 * Creates new ArrayIndexed collection and puts members from given
	 * collection into it. Collection on creation has a minimum of 16 elements.
	 * If collection has more than 16 elements, array will adjust to that size.
	 * 
	 * @param other
	 *            collection with object member which will be added to this new
	 *            collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Creates empty ArrayIndexed collection set to default capacity of 16.
	 * Array is preallocated to the default capacity size.
	 */
	public ArrayIndexedCollection() {
		this(null);
	}

	/**
	 * Creates empty ArrayIndexed collection and sets it to specified initial
	 * capacity.
	 * 
	 * @param initialCapacity
	 *            capacity to which array will be allocated
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}

	// overriden methods with proper implementations

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection. Reference is added into first
	 * empty place in the internal array. Array is growing linear by 2, each
	 * time it reaches max current capacity. Complexity is O(1).
	 * 
	 * @param value
	 *            value to be added
	 * @throws IllegalArgumentException
	 *             if null reference is passed as argument
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Null references are not allowed");
		}

		// Array is full, so we reallocate it and double its size
		if (size == capacity) {
			capacity *= 2;
			Object[] newArray = new Object[capacity];
			for (int i = 0; i < size; i++) {
				newArray[i] = elements[i];
			}

			elements = newArray;
		}

		// put value to first empty space and increment size
		elements[size++] = value;
	}

	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				size--;
				for (int j = i; j < size; j++) {
					elements[j] = elements[j + 1];
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public Object[] toArray() throws UnsupportedOperationException {
		Object[] array = new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}

		return array;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void clear() {
		size = 0;
		elements = null;
	}

	// new methods for ArrayIndexedCollection class

	/**
	 * Returns the object that is stored in the array at position index. Valid
	 * indexes are from <code>0</code> to <code>size-1</code>. Complexity is
	 * O(1).
	 * 
	 * @param index
	 *            index of element which method will return
	 * @return returns the element positioned at specified index
	 * @throws IndexOutOfBoundsException
	 *             if index is invalid
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}

		return elements[index];
	}

	/**
	 * Inserts given element on specified position. All elements are shifted one
	 * place toward the end of the array. Legal positions are <code>0</code> to
	 * <code>size</code>.
	 * 
	 * @param value
	 *            value to be inserted
	 * @param position
	 *            position to insert value at
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException("Invalid position to insert.");
		}

		if (size == capacity) {
			capacity *= 2;
			Object[] newArray = new Object[capacity];
			for (int i = 0; i < size; i++) {
				newArray[i] = elements[i];
			}

			elements = newArray;
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		size++;
		elements[position] = value;
	}

	/**
	 * Searches the collection and returns the index of the first occurence of
	 * the given value or -1 if the value is not found.
	 * 
	 * @param value
	 *            value to search
	 * @return index in the array where value is positioned, or -1 if there is
	 *         no such value
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection. Legal indexes are 0
	 * to size-1. Elements are shifted towards begin of the collection.
	 * 
	 * @param index
	 *            position of element to be removed
	 * @throws IllegalArgumentException
	 *             if given index is out of scope
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IllegalArgumentException("Invalid index of object to remove");
		}

		size--;
		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
	}
}
