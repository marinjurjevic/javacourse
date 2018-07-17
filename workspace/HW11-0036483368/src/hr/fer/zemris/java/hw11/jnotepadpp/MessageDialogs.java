package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * MessageDialogs is a set of preformatted JOptionPane messages that display
 * information in given editor.
 * 
 * @author Marin Jurjevic
 *
 */
public class MessageDialogs {

	/**
	 * Pops confirm that asks to confirm or discard file saving.
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 * @return new confirm dialog
	 */
	public static int getSaveConfirm(IEditor editor) {
		return JOptionPane.showConfirmDialog((JFrame) editor,
				editor.getLProvider().getString("askToSave") + " " + editor.getCurrentFileModel().getPath() + "?",
				editor.getLProvider().getString("saveFile"), JOptionPane.YES_NO_CANCEL_OPTION);
	}

	/**
	 * Pops confirm that asks user for overwriting existing file
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 * @return new confirm dialog
	 */
	public static int getFileExists(IEditor editor) {
		return JOptionPane.showConfirmDialog((JFrame) editor, editor.getLProvider().getString("fileExists"),
				editor.getLProvider().getString("fileExistsTitle"), JOptionPane.YES_NO_CANCEL_OPTION);

	}

	/**
	 * Pops message dialog when file has not been saved.
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 */
	public static void getFileNotSaved(IEditor editor) {
		JOptionPane.showMessageDialog((JFrame) editor, editor.getLProvider().getString("notSaved"),
				editor.getLProvider().getString("warning"), JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Pops message dialog when error while writing occurs.
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 */
	public static void getErrorWhileWriting(IEditor editor) {
		JOptionPane.showMessageDialog((JFrame) editor,
				editor.getLProvider().getString("errorWriting") + editor.getCurrentFileModel().getPath(),
				editor.getLProvider().getString("error"), JOptionPane.ERROR_MESSAGE);
		return;
	}

	/**
	 * Pops message dialog when error while reading occurs.
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 */
	public static void getErrorWhileReading(IEditor editor) {
		JOptionPane.showMessageDialog((JFrame) editor,
				editor.getLProvider().getString("errorReading") + editor.getCurrentFileModel().getPath(),
				editor.getLProvider().getString("error"), JOptionPane.ERROR_MESSAGE);
		return;
	}

	/**
	 * Pops message dialog when file does not exists.
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 */
	public static void getFileNotExistsError(IEditor editor) {
		JOptionPane.showMessageDialog((JFrame) editor,
				editor.getLProvider().getString("fileNotExists1") + " " + editor.getCurrentFileModel().getPath() + " "
						+ editor.getLProvider().getString("fileNotExists"),
				editor.getLProvider().getString("error"), JOptionPane.ERROR_MESSAGE);
		return;
	}

	/**
	 * Pops message dialog when file has been saved succesfully
	 * 
	 * @param editor
	 *            editor in which dialog is displayed
	 */
	public static void getSaveSuccess(IEditor editor) {
		JOptionPane.showMessageDialog((JFrame) editor, editor.getLProvider().getString("fileSaved"),
				editor.getLProvider().getString("information"), JOptionPane.INFORMATION_MESSAGE);
	}
}
