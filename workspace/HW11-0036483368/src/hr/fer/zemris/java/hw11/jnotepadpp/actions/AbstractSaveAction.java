package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.MessageDialogs;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Abstract superclass for saving actions: save and save as.
 * <tt>AbstractSaveAction</tt> offers saveFile method that performs storing data
 * from currently selected file in editor.
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class AbstractSaveAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	protected IEditor editor;

	/**
	 * Creates new action with specified name and editor instance.
	 * 
	 * @param name
	 *            action name
	 * @param editor
	 *            instance of IEditor
	 */
	public AbstractSaveAction(String name, IEditor editor) {
		super(name, editor.getLProvider());
		setEnabled(false);
		this.editor = editor;
	}

	/**
	 * Saves currently active document in editor workspace.
	 */
	protected void saveFile() {
		Path saveFilePath = editor.getCurrentFileModel().getPath();
		JTextArea text = editor.getCurrentFileModel().getText();

		byte[] podatci = text.getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(saveFilePath, podatci);
		} catch (IOException e1) {
			MessageDialogs.getErrorWhileWriting(editor);
			return;
		}

		MessageDialogs.getSaveSuccess(editor);

		editor.getCurrentFileModel().setModified(false);
	}

}
