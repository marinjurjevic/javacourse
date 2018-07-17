package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * Changes all currently selected characters to lower case.
 * 
 * @author Marin Jurjevic
 *
 */
public class ToLowerCaseAction extends ChangeCaseAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new action for switching all character's to lower case.
	 * 
	 * @param editor
	 *            source editor
	 */
	public ToLowerCaseAction(IEditor editor) {
		super("toLowerCase", editor);
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		super.putValue(Action.SMALL_ICON, Icons.TO_LOWER_CASE_ICON);
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		modifyText(Character::toLowerCase);
	}

}
