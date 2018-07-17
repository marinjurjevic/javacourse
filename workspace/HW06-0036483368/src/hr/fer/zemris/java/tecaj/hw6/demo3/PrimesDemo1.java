package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Demonstration program for testing PrimesCollection class.
 * 
 * @author Marin Jurjevic
 *
 */
public class PrimesDemo1 {

	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);

		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}

}
