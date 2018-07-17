package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * LikeMedian is a simple class that keeps storage of objects and counts median
 * when method get() is called.
 * 
 * @author Marin Jurjevic
 *
 */
public class LikeMedian<T extends Comparable<T>> implements Iterable<T> {

	/**
	 * internal storage of elements
	 */
	private List<T> elements;

	/**
	 * Creates new empty instance of LikeMedia class
	 */
	public LikeMedian() {
		elements = new ArrayList<T>();
	}

	/**
	 * Adds new element to the list.
	 * @param element element to be added
	 */
	public void add(T element) {
		elements.add(element);
	}

	/**
	 * Returns instance of Optional class. If there are no elements,
	 * optional object will be empty, if there are elements, median will
	 * be calculated and returned packed wrapped in Optional class.
	 * @return median of the elements in this class
	 */
	public Optional<T> get() {
		if (elements.isEmpty()) {
			return Optional.empty();
		}

		List<T> sorted = new ArrayList<>(elements);
		Collections.sort(sorted, Collections.reverseOrder());

		return Optional.of(sorted.get(sorted.size() / 2));
	}
	
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
