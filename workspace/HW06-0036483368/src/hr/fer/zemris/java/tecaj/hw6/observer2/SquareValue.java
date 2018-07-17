package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Creates new SquareValue observer. It is responsible for printing out new
 * value squared to the standard output.
 * 
 * @author Marin Jurjevic
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Squares the new value in storage and prints it out on the standard
	 * output.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.println("Provided new value: " + value + ", square is " + (int) Math.pow(value, 2));
	}

}
