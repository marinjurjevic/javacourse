package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.MessageDialogs;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Opens new JFileChooser dialog and asks user to choose which file he wants to
 * open. If file is already opened, editor will be sent null reference as text
 * of document.
 * 
 * @author Marin Jurjevic
 *
 */
public class OpenDocumentAction extends LocalizableAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new action for opening existing documents on disc.
	 * 
	 * @param editor
	 *            source editor
	 */
	public OpenDocumentAction(IEditor editor) {
		super("openFile", editor.getLProvider());
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		this.putValue(Action.SMALL_ICON, Icons.OPEN_FILE_ICON);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(editor.getLProvider().getString("openFile"));
		if (fc.showOpenDialog((JFrame) editor) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();

		// alrady opened
		if (editor.containsFile(filePath)) {
			editor.addNewDocument(filePath, null, false);
			return;
		}

		if (!Files.isReadable(filePath)) {
			MessageDialogs.getFileNotExistsError(editor);
		}

		byte[] okteti;
		try {
			okteti = Files.readAllBytes(filePath);
		} catch (Exception ex) {
			MessageDialogs.getErrorWhileReading(editor);
			return;
		}

		JTextArea text = new JTextArea(new String(okteti, StandardCharsets.UTF_8));

		editor.addNewDocument(filePath, text, false);
	}

}
