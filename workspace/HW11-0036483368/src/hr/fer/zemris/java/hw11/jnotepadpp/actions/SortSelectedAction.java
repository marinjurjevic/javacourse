package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Abstract superclass for sorting selected text in current document in source
 * editor. Extended actions must determine in which order text will be sorted.
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class SortSelectedAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Collator for language-specific comparison
	 */
	Collator collator;

	/**
	 * Creates new action for sorting selected text.
	 * 
	 * @param key
	 *            action name key
	 * @param editor
	 *            source editor
	 */
	public SortSelectedAction(String key, IEditor editor) {
		super(key, editor.getLProvider());
		this.editor = editor;
		collator = Collator.getInstance(new Locale(LocalizationProvider.getInstance().getLanguage()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea textArea = editor.getCurrentFileModel().getText();
		Document doc = textArea.getDocument();

		List<String> lines = new LinkedList<>();

		int startPos = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		int endPos = startPos != textArea.getCaret().getDot() ? textArea.getCaret().getDot()
				: textArea.getCaret().getMark();

		try {
			int first = textArea.getLineOfOffset(startPos);
			int last = textArea.getLineOfOffset(endPos);
			for (int i = first; i <= last; i++) {
				int lineStart = textArea.getLineStartOffset(i);
				int lineEnd = textArea.getLineEndOffset(i);
				String lineText = textArea.getText(lineStart, lineEnd - lineStart);
				if (!lineText.endsWith("\n")) {
					lineText += "\n";
				}
				lines.add(lineText);
			}

			if (isAscending()) {
				lines.sort(collator);
			} else {
				lines.sort(collator.reversed());
			}

			for (int i = first; i <= last; i++) {
				String line = lines.get(i - first);
				int lineStart = textArea.getLineStartOffset(i);
				int lineEnd = textArea.getLineEndOffset(i);
				doc.remove(lineStart, lineEnd - lineStart);
				doc.insertString(lineStart, line, null);

			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Tells what
	 * 
	 * @return true for ascending order, false for descending
	 */
	protected abstract boolean isAscending();
}
