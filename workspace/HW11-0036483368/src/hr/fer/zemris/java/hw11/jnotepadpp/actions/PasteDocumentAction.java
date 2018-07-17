package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Pastes current content from system clipboard.
 * 
 * @author Marin Jurjevic
 *
 */
public class PasteDocumentAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new action that pastes content from system clipboard on currently
	 * selected document.
	 * 
	 * @param editor
	 *            source editor
	 */
	public PasteDocumentAction(IEditor editor) {
		super("paste", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		super.putValue(Action.SMALL_ICON, Icons.PASTE_ICON);
		setEnabled(false);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		editor.getCurrentFileModel().getText().paste();
	}

}
