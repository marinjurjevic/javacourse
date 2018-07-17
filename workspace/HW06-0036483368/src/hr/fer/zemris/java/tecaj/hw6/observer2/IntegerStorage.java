package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of a simple storage that keeps primitive int as value. This
 * storage has feature to register observers onto itself and notifies them
 * whenever current value is changed.
 * 
 * @author Marin Jurjevic
 *
 */
public class IntegerStorage {

	/**
	 * value this storage keeps
	 */
	private int value;

	/**
	 * private list of observers that are actively registered on this storage
	 */
	private List<IntegerStorageObserver> observers;

	private Iterator<IntegerStorageObserver> it;

	/**
	 * Creates new storage that keeps integer values inside.
	 * 
	 * @param initialValue
	 *            initial value of this storage
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new LinkedList<>();
	}

	/**
	 * Registers given observer, if it's not already registered.
	 * 
	 * @param observer
	 *            observer to be registered
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * De-registers given observer from this integer storage.
	 * 
	 * @param observer
	 *            obsever to be de-registered
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (it == null) {
			observers.remove(observer);
		} else {
			it.remove();
		}
	}

	/**
	 * De-registers all observers from this integer storage.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns value from integer storage.
	 * 
	 * @return value stored in this storage
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets new value only if it's difference from the one currently stored in
	 * this storage.
	 * 
	 * @param value
	 *            value to be set if it differs from currently stored value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {

			int oldValue = this.value;
			this.value = value;

			IntegerStorageChange isc = new IntegerStorageChange(this, oldValue);

			// Notify all registered observers
			if (observers != null) {
				it = observers.iterator();
				IntegerStorageObserver o;

				while (it.hasNext()) {
					o = it.next();
					o.valueChanged(isc);
				}
			}
		}
	}
}