package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * Sorts selected text ascending.
 * 
 * @author Marin Jurjevic
 *
 */
public class SortSelectedAscendingAction extends SortSelectedAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new action for sorting selected text ascending.
	 * 
	 * @param editor
	 */
	public SortSelectedAscendingAction(IEditor editor) {
		super("sortAsc", editor);
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift A"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		super.putValue(Action.SMALL_ICON, Icons.SORT_ASC_ICON);
		setEnabled(false);
	}

	@Override
	protected boolean isAscending() {
		return true;
	}

}
