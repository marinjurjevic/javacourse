package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * LocalizableAction is a superclass and all actions in GUI must extend it to
 * achieve full application localization
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * key for fetching action name and short description
	 */
	private String key;

	/**
	 * Creates new LocalizableAction with specified key and
	 * ILocalizationProvider
	 * 
	 * @param key
	 * @param lp
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		update(lp);

		lp.addLocalizationListener(() -> {
			update(lp);
		});
	}

	/**
	 * Updates action parameters.
	 * 
	 * @param lp
	 *            instance of ILocalizationProvider
	 */
	private void update(ILocalizationProvider lp) {
		this.putValue(Action.NAME, lp.getString(key));
		this.putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "Desc"));
	}
}
