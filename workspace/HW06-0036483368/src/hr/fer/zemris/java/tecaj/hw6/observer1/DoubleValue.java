package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Creates new DoubleValue observer which prints out double the value that is
 * stored in storage for <b>n</b> times. After <b>n</b> times, this observer
 * de-register itself from storage.
 * 
 * @author Marin Jurjevic
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * keeps track of how many more times storage can change 
	 */
	private int n;

	/**
	 * Creates new DoubleValue observer.
	 * @param n - how many times will this observer react
	 */
	public DoubleValue(int n) {
		this.n = n;
	}

	/**
	 * Prints out double of the value stored in istorage. This method guarantees
	 * to be executed only <b>n</b> times as specified in constructor of this
	 * class.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + istorage.getValue() * 2);

		n--;
		if (n == 0) {
			istorage.removeObserver(this);
			;
		}
	}

}
