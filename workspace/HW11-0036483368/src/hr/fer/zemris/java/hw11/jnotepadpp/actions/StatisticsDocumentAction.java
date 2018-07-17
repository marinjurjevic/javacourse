package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * 
 * Pops up new message dialog showing information about currently active
 * FileModel in source editor. Information shown are:
 * <li>number of all characters
 * <li>number of non-blank characters
 * <li>number of lines characters
 * 
 * @author Marin Jurjevic
 *
 */
public class StatisticsDocumentAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new action for showing statistics about currently active
	 * FileModel.
	 * 
	 * @param editor
	 */
	public StatisticsDocumentAction(IEditor editor) {
		super("fileStats", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		super.putValue(Action.SMALL_ICON, Icons.STATS_ICON);
		setEnabled(false);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Document model = editor.getCurrentFileModel().getText().getDocument();
		int chars = model.getLength();
		int lines = editor.getCurrentFileModel().getText().getLineCount();

		int nonBlank = 0;
		try {
			char[] text = model.getText(0, chars).toCharArray();
			for (int i = 0; i < chars; i++) {
				if (!Character.isWhitespace(text[i])) {
					nonBlank++;
				}
				if (text[i] == '\n') {
					lines++;
				}
			}
		} catch (BadLocationException ignorable) {
		}

		ILocalizationProvider lp = editor.getLProvider();
		JOptionPane.showMessageDialog((JFrame) editor,
				lp.getString("statsDocHas") + " " + chars + " " + lp.getString("chars") + ", " + nonBlank + " "
						+ lp.getString("nonBlank") + " " + lp.getString("chars") + " " + lp.getString("and") + " "
						+ lines + " " + lp.getString("lines") + ".",
				editor.getLProvider().getString("fileStats"), JOptionPane.INFORMATION_MESSAGE);
	}
}
