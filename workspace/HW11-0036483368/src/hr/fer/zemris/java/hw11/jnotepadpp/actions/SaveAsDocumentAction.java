package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.IEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.MessageDialogs;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * Creates new saveAs action. Save as action always ask user to specify new
 * location of file on disc. After save, file model path will changed to newly
 * specified location.
 * 
 * @author Marin Jurjevic
 *
 */
public class SaveAsDocumentAction extends AbstractSaveAction {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source editor
	 */
	private IEditor editor;

	/**
	 * Creates new saveAs action.
	 * 
	 * @param editor
	 */
	public SaveAsDocumentAction(IEditor editor) {
		super("saveFileAs", editor);
		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		super.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		this.putValue(Action.SMALL_ICON, Icons.SAVE_AS_ICON);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (f.exists() && getDialogType() == SAVE_DIALOG) {
					int result = MessageDialogs.getFileExists(editor);
					switch (result) {
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
				super.approveSelection();
			}
		};
		jfc.setDialogTitle(editor.getLProvider().getString("saveFileAs"));
		if (jfc.showSaveDialog((JFrame) editor) != JFileChooser.APPROVE_OPTION) {
			MessageDialogs.getFileNotSaved(editor);
			return;
		}

		Path savedFilePath = jfc.getSelectedFile().toPath();
		editor.getCurrentFileModel().setPath(savedFilePath);
		saveFile();
		editor.saveDocument(savedFilePath);
	}

}
