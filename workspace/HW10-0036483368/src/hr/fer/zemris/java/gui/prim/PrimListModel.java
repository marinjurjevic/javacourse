package hr.fer.zemris.java.gui.prim;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * <tt>PrimListModel</tt> is a simple model for prim number generation. It
 * implements {@link ListModel} interface for MVC pattern. It offers only one
 * method for generating next prime number.
 * 
 * @author Marin Jurjevic
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * currently generated prime numbers
	 */
	private List<Integer> primes;

	/**
	 * registered listeners
	 */
	private List<ListDataListener> listeners;

	/**
	 * Creates new PrimListModel with 1 as default starting number.
	 */
	public PrimListModel() {
		primes = new LinkedList<>();
		listeners = new LinkedList<>();
		primes.add(1);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Generates next prime number and adds it into the model. Afterward it
	 * signals all registered listeners that change has occured.
	 */
	public void next() {
		generateNextPrime();

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primes.size(), primes.size());
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Help method for generating next prime number, starting to look for it
	 * from the last generated one.
	 */
	private void generateNextPrime() {
		int n = primes.get(primes.size() - 1) + 1;
		boolean isPrime;

		while (true) {
			isPrime = true;
			for (int i = 2, max = (int) Math.sqrt(n); i <= max; i++) {
				if (n % i == 0) {
					isPrime = false;
					break;
				}
			}

			if (isPrime) {
				break;
			} else {
				n++;
			}
		}
		primes.add(n);
	}
}
