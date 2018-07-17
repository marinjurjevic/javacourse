package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Removes all duplicate lines, preserving only first occurences of such lines.
 * 
 * @author Marin Jurjevic
 *
 */
public class UniqueLinesAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new action that removes duplicate lines.
	 * 
	 * @param editor
	 */
	public UniqueLinesAction(IEditor editor) {
		super("unique", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		super.putValue(Action.SMALL_ICON, Icons.UNIQUE_ICON);
		setEnabled(false);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea textArea = editor.getCurrentFileModel().getText();
		Document doc = textArea.getDocument();

		Set<String> lines = new HashSet<>();

		int startPos = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		int endPos = startPos != textArea.getCaret().getDot() ? textArea.getCaret().getDot()
				: textArea.getCaret().getMark();

		try {
			int first = textArea.getLineOfOffset(startPos);
			int last = textArea.getLineOfOffset(endPos);
			JTextArea textAreaCopy = new JTextArea();
			textAreaCopy.setText(textArea.getText());
			for (int i = first; i <= last; i++) {
				int lineStart = textArea.getLineStartOffset(i);
				int lineEnd = textArea.getLineEndOffset(i);
				String lineText = textArea.getText(lineStart, lineEnd - lineStart);
				if (lineText.endsWith("\n")) {
					lineText = lineText.substring(0, lineText.length() - 1);
				}
				if (!lines.add(lineText)) {
					doc.remove(lineStart, lineEnd - lineStart);
					i--;
					last--;
				}
			}

		} catch (BadLocationException ignorable) {
		}

	}

}
