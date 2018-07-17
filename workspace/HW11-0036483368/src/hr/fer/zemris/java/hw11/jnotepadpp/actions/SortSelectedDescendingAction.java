package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * Sorts selected text descending.
 * 
 * @author Marin Jurjevic
 *
 */
public class SortSelectedDescendingAction extends SortSelectedAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new action for sorting selected text ascending.
	 * 
	 * @param editor
	 */
	public SortSelectedDescendingAction(IEditor editor) {
		super("sortDesc", editor);
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift D"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		super.putValue(Action.SMALL_ICON, Icons.SORT_DESC_ICON);
		setEnabled(false);
	}

	@Override
	protected boolean isAscending() {
		return false;
	}

}
