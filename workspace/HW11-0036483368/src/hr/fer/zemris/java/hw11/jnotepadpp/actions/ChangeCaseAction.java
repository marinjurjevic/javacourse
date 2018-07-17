package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.function.Function;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Abstract superclass for changing and inverting case actions.
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class ChangeCaseAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new action with specified name key and editor instance.
	 * 
	 * @param key
	 *            key for action name
	 * @param editor
	 *            instance of IEditor
	 */
	public ChangeCaseAction(String key, IEditor editor) {
		super(key, editor.getLProvider());
		this.editor = editor;
	}

	/**
	 * Modifies selected text in currently active document in editor workspace.
	 * 
	 * @param modifier
	 *            wanted modification
	 */
	protected void modifyText(Function<Character, Character> modifier) {
		JTextArea textArea = editor.getCurrentFileModel().getText();
		Document doc = textArea.getDocument();

		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		try {
			String text = doc.getText(offset, len);
			text = changeCase(text, modifier);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Applies modifier on selected text.
	 * 
	 * @param text
	 *            selected text
	 * @param modifier
	 *            modifier to apply
	 * @return modified text
	 */
	private String changeCase(String text, Function<Character, Character> modifier) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			znakovi[i] = modifier.apply(znakovi[i]);
		}

		return new String(znakovi);
	}

}
