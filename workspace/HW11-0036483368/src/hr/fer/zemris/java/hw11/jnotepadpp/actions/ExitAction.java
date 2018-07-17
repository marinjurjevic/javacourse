package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Exits source IEditor. Effectively this action will call IEditor's method
 * {@link IEditor#exit()}
 * 
 * @author Marin Jurjevic
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new ExitAction for closing shutting down IEditor
	 * 
	 * @param editor
	 *            source editor
	 */
	public ExitAction(IEditor editor) {
		super("exit", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		this.putValue(Action.SMALL_ICON, Icons.EXIT_ICON);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		editor.exit();
	}

}
