package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * <tt>ILocalizationProvider</tt> provides API for dynamic localization change.
 * It offers {@link #getString} for fetching localized value, and it utilizes
 * Observer pattern.
 * 
 * @author Marin Jurjevic
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds localization listener to this provider.
	 * 
	 * @param l
	 *            LocalizationListener
	 */
	public void addLocalizationListener(LocalizationListener l);

	/**
	 * Removes localization listener from this provider.
	 * 
	 * @param l
	 *            LocalizationListener
	 */
	public void removeLocalizationListener(LocalizationListener l);

	/**
	 * Fetches localized string identified by it's unique key.
	 * 
	 * @param key
	 *            unique key for fetching localized value
	 * @return fetched value by key
	 */
	public String getString(String key);
}
