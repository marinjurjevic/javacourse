package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Cuts selected text.
 * 
 * @author Marin Jurjevic
 *
 */

public class CutDocumentAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new instance fo CutDocumentAction
	 * 
	 * @param editor
	 *            source editor
	 */
	public CutDocumentAction(IEditor editor) {
		super("cut", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		super.putValue(Action.SMALL_ICON, Icons.CUT_ICON);
		setEnabled(false);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		editor.getCurrentFileModel().getText().cut();
	}

}
