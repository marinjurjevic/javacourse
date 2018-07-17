package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;

/**
 * Collection of prime numbers. It recieve a positive integer through
 * constructor and creates a simple collection that holds up to n prime numbers.
 * 
 * @author Marin Jurjevic
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * number of prime numbers in this collection
	 */
	private int n;

	/**
	 * Creates new collection of n prime numbers.
	 * @param n
	 */
	public PrimesCollection(int n) {
		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Implementation of prime numbers iterator. This iterator does not support
	 * remove method, if called it will throw UnsupportedOperation.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {
		/**
		 * numbers left to iterate over
		 */
		private int left = n;

		/**
		 * current prime number
		 */
		private int current = 2;

		@Override
		public boolean hasNext() {
			return left > 0;
		}

		@Override
		public Integer next() {
			while (true) {
				// assume number is prime
				boolean prime = true;

				for (int j = 2; j <= (int) Math.sqrt(current); j++) {
					if (current % j == 0) {
						prime = false;
						break;
					}
				}

				if (prime == true) {
					break;
				}
				current++;
			}

			left--;
			return current++;
		}
	}
}
