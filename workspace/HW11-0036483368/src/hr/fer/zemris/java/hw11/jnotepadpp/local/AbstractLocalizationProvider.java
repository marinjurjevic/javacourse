package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract superclass for direct implementation of listeners. All subclass
 * provider must only provide method for fetching Strings.
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of all interested listeners.
	 */
	private List<LocalizationListener> listeners;

	/**
	 * Creates new list of listeners.
	 */
	public AbstractLocalizationProvider() {
		listeners = new LinkedList<>();
	}

	@Override
	public void addLocalizationListener(LocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(LocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Fires signal to all interested listeners that change has occured.
	 */
	public void fire() {
		for (LocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
