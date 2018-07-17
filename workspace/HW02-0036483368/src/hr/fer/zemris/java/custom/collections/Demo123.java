package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Program for testing and demonstrating work of collection classes.
 */
public class Demo123 {

	public static void main(String[] args) {
//		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
//		col.add(new Integer(20));
//		col.add("New York");
//		col.add("San Francisco"); // here the internal array is reallocated to 4
//		printElements(col);
//
//		System.out.println("Is New York inside collection: " + col.contains("New York")); // writes true
//
//		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
//		printElements(col);
//
//		System.out.println("Element at index = 1: " + col.get(1)); // writes: San Francisco
//		System.out.println("Collection size: " + col.size()); // writes: 2
//		col.add("Los Angeles");
//		System.out.println();
//		
//		col.add("Zagreb");
//		col.add("Split");
//		col.add(3.14);
//		printElements(col);
//		
//		col.remove(0);
//		col.remove("Split");
//		col.remove(3);
//		printElements(col);
//		
//		col.insert("Osijek", 2);
//		col.insert("End", col.size());
//		col.insert("Begin", 0);
//		printElements(col);
//		
		
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		
		col2.add("ele1");
		col2.add("ele2");
		col2.add("ele3");

		col2.remove("ele3");
		printElements(col2);
		//fullInfo(col);
		//fullInfo(col2);
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
