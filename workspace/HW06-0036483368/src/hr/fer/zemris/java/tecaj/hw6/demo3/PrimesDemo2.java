package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Demonstration program for testing multiple iteration.
 * 
 * @author Marin Jurjevic
 *
 */
public class PrimesDemo2 {

	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(4);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				for (Integer prime3 : primesCollection) {
					System.out.println("Got prime pair: " + prime + ", " + prime2 + ", " + prime3);
				}
			}
		}
	}

}
