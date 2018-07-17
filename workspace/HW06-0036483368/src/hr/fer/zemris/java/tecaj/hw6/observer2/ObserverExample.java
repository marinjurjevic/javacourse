package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Demonstration program for IntegerStorageChange class.
 * 
 * @author Marin Jurjevic
 *
 */

public class ObserverExample {

	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);

		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

	}
}