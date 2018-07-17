package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Creates new ChangeCounter observer which keeps track how many times did the
 * storage value changed since this observer has been registered.
 * 
 * @author Marin Jurjevic
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * internal counter that counts change on storage since this observer is registered
	 */
	private int counter;

	/**
	 * Prints out on standard output number of times storage value has been
	 * changed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
