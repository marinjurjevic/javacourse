package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * Saves currently active document in source editor.
 * 
 * @author Marin Jurjevic
 *
 */
public class SaveDocumentAction extends AbstractSaveAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new save action.
	 * 
	 * @param editor
	 *            source editor
	 */
	public SaveDocumentAction(IEditor editor) {
		super("saveFile", editor);
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		this.putValue(Action.SMALL_ICON, Icons.SAVE_FILE_ICON);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!editor.getCurrentFileModel().getPath().isAbsolute()) {
			editor.saveNewDocument();
			return;
		}
		saveFile();
		editor.saveDocument(null);
	}

}
