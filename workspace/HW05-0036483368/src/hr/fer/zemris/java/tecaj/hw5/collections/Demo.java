package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.Iterator;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashTable.TableEntry;

public class Demo {

	public static void main(String[] args) {
		// create collection:
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 3);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		printPairCombinations(examMarks);

	}

	public static void printPairs(SimpleHashTable<String, Integer> hashtable) {
		for (SimpleHashTable.TableEntry<String, Integer> pair : hashtable) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
	}

	public static void printPairCombinations(SimpleHashTable<String, Integer> hashtable) {
		for (SimpleHashTable.TableEntry<String, Integer> pair1 : hashtable) {
			Iterator<TableEntry<String,Integer>> it = hashtable.iterator();
			while(it.hasNext()){
				TableEntry<String,Integer> pair2 = it.next();
				if(pair2.getKey().equals("Kristina"))
					it.remove();
				else
					System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
							pair2.getValue());
			}
			
		}
	}

}
