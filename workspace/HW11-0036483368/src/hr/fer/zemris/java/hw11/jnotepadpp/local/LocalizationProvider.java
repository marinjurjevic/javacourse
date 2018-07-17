package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * LocalizationProvider is a singleton class acting as a central unit for
 * providing localized environment.
 * 
 * @author Marin Jurjevic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * currently used language
	 */
	private String language;

	/**
	 * bundle object for fetching Strings
	 */
	private ResourceBundle bundle;

	/**
	 * singleton instance
	 */
	private static LocalizationProvider instance;

	/**
	 * private constructor for creating instance
	 */
	private LocalizationProvider() {
		// default croatian
		language = "hr";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
	}

	/**
	 * Returns reference to an instance object.
	 * 
	 * @return reference to singleton object
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}

		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Sets language and fires event to notify all listeners.
	 * 
	 * @param language
	 *            new language to be set
	 */
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
		fire();
	}

	/**
	 * Returns currently used language.
	 * 
	 * @return currently used language
	 */
	public String getLanguage() {
		return language;
	}

}
