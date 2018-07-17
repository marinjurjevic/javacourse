package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Listener for localization changes.
 * 
 * @author Marin Jurjevic
 *
 */
public interface LocalizationListener {
	/**
	 * performs when localization changes (e.g. language). It will commonly be
	 * used by actions to update it's parameters.
	 */
	public void localizationChanged();
}
