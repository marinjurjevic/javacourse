package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * Action for inverting case. Selected text case will be inverted.
 * 
 * @author Marin Jurjevic
 *
 */
public class InvertCaseAction extends ChangeCaseAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new InvertCaseAction
	 * 
	 * @param editor
	 *            source editor
	 */
	public InvertCaseAction(IEditor editor) {
		super("invertCase", editor);
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		super.putValue(Action.SMALL_ICON, Icons.INVERT_CASE_ICON);
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		modifyText(c -> {
			if (Character.isUpperCase(c)) {
				return Character.toLowerCase(c);
			} else {
				return Character.toUpperCase(c);
			}
		});
	}

}
