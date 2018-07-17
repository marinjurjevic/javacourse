package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Implementation of a simple <code>IntegerStorage</code> observer. It offers
 * single method valueChanged, which signals change of state to an observer.
 * 
 * @author Marin Jurjevic
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Performs given action (depending on observer).
	 * 
	 * @param istorage
	 *            storage this observer is registered to
	 */
	public void valueChanged(IntegerStorage istorage);
}