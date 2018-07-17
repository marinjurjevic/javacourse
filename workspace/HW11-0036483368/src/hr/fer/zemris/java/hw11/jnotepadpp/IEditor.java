package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * IEditor is interface for offering editor functionality. Editor must manage
 * creating new documents and opening,saving and closing existing ones. IEditor
 * also must fetch existing file model that is opened in it and localization
 * provider that is using.
 * 
 * @author Marin Jurjevic
 *
 */
public interface IEditor {

	/**
	 * Adds or opens new document in editor workspace. </br>
	 * Last parameter is boolean flag for editor to know is file being opened or
	 * created.
	 * 
	 * @param filePath
	 *            path to the new file, or opened
	 * @param text
	 *            text of file model
	 * @param isNewDocument
	 *            true if new document is created, false if document is being
	 *            opened
	 */
	public void addNewDocument(Path filePath, JTextArea text, boolean isNewDocument);

	/**
	 * Saves currently opened FileModel.
	 * 
	 * @param filePath
	 *            path of current file model
	 */
	public void saveDocument(Path filePath);

	/**
	 * Saves newly created document.
	 */
	public void saveNewDocument();

	/**
	 * Closes current model with or without save.
	 * 
	 * @param save
	 *            true if file will be saved, false otherwise
	 */
	public void closeCurrentDocument(boolean save);

	/**
	 * Fetches current FileModel shown in editor.
	 * 
	 * @return FileModel currently used in editor
	 */
	public FileModel getCurrentFileModel();

	/**
	 * Checks whether FileModel with specified path exists in editor.
	 * 
	 * @param filePath
	 *            path of file
	 * @return true if file exists , false otherwise
	 */
	public boolean containsFile(Path filePath);

	/**
	 * Instance of ILocalizationProvider used by editor.
	 * 
	 * @return instance of ILocalizationProvider
	 */
	public ILocalizationProvider getLProvider();

	/**
	 * Exits editor.
	 */
	public void exit();
}
