package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Changes currently used language in editor's user interface. This action will
 * fetch instance of LocalizationProvider and change language which will
 * effectively change used language in complete UI.
 * 
 * @author Marin Jurjevic
 *
 */
public class ChangeLanguageAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * language to be set
	 */
	private String language;

	/**
	 * Creates new action that changes language in given editor UI.
	 * 
	 * @param language
	 *            language to be set by this action
	 * @param editor
	 *            instance of IEditor
	 */
	public ChangeLanguageAction(String language, IEditor editor) {
		super("langKey" + language.toUpperCase(), editor.getLProvider());
		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
	}

}
