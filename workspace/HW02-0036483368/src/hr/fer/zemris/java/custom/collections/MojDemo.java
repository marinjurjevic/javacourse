package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Program for testing and demonstrating work of collection classes.
 */
public class MojDemo{

	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);
		
		col.add("ele1");
		col.add("ele2");
		col.add("ele3");
		col.add("ele4");
		col.add("ele5");
		
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(col);
		LinkedListIndexedCollection list2 = new LinkedListIndexedCollection();
		
		list2.insert("L", 0);
		list2.add("L1");
		printElements(list2);
	}

	/**
	 * Prints out elements using Arrays.toString() method.
	 * 
	 * @param col
	 *            collection whose elements will be printed on standard output
	 */
	private static void printElements(Collection col) {
		System.out.println(Arrays.toString(col.toArray()));
	}

	/**
	 * Method for printing detailed additional info about collection for testing
	 * purposes.
	 * 
	 * @param col
	 *            collection whose info will be printed on standard output
	 */
	private static void fullInfo(Collection col) {

		System.out.println("--- COLLECTION INFO --- ");
		System.out.println("Collection type: " + col.getClass());
		System.out.println("Collection size: " + col.size());
		System.out.println("Collection elements :");

		class P extends Processor {
			@Override
			public void process(Object o) {
				System.out.println(o);
			}
		}

		col.forEach(new P());
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println();
	}

}
