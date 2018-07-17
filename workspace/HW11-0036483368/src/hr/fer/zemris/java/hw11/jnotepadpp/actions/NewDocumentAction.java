package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Creates new document with name of locale word for new.
 * 
 * @author Marin Jurjevic
 *
 */
public class NewDocumentAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * counter for indexing newly created files
	 */
	private int counter = 1;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new action for creating new document with default name. Default
	 * name is determined by currently used language in source editor.
	 * 
	 * @param editor
	 */
	public NewDocumentAction(IEditor editor) {
		super("newFile", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		this.putValue(Action.SMALL_ICON, Icons.NEW_FILE_ICON);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = editor.getLProvider().getString("new") + " " + counter++;

		editor.addNewDocument(Paths.get(name), new JTextArea(), true);

	}

}
