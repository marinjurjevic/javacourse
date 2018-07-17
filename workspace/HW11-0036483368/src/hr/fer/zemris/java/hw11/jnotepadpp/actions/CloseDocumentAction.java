package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.FileModel;
import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.MessageDialogs;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Closes currently active file in IEditor. If file, in the time of calling this
 * action, has been modified, user will be asked whether he wants to save it or
 * not.
 * 
 * @author Marin Jurjevic
 *
 */
public class CloseDocumentAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new CloseDocumentAction for closing currently active file.
	 * 
	 * @param editor
	 *            source editor
	 */
	public CloseDocumentAction(IEditor editor) {
		super("closeFile", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		super.putValue(Action.SMALL_ICON, Icons.CLOSE_FILE_ICON);
		setEnabled(false);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileModel current = editor.getCurrentFileModel();
		if (current.getModified()) {
			int odluka = MessageDialogs.getSaveConfirm(editor);
			if (odluka == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (odluka == JOptionPane.YES_OPTION) {
				editor.closeCurrentDocument(true);
				return;
			} else {
				MessageDialogs.getFileNotSaved(editor);
			}
		}
		editor.closeCurrentDocument(false);
	}

}
