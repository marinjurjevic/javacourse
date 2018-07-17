package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * <tt>FileModel</tt> encapsulates information about a file and it's content.
 * File is represented by it's <tt>Path</tt>, <tt>JTextArea</tt> and flag
 * modified.
 * 
 * @author Marin Jurjevic
 *
 */
public class FileModel {

	/**
	 * current file path
	 */
	private Path path;

	/**
	 * current file text
	 */
	private JTextArea text;

	/**
	 * current modification status
	 */
	private boolean modified;

	/**
	 * Creates new file model with specified path and text.
	 * 
	 * @param path
	 *            file path
	 * @param text
	 *            file text
	 */
	public FileModel(Path path, JTextArea text) {
		super();
		this.path = path;
		this.text = text;
	}

	/**
	 * Returns file path.
	 * 
	 * @return file path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Returns file text.
	 * 
	 * @return file text
	 */
	public JTextArea getText() {
		return text;
	}

	/**
	 * Tells if file has been modified.
	 * 
	 * @return true if file has been modified, false if not
	 */
	public boolean getModified() {
		return modified;
	}

	/**
	 * Sets new file path
	 * 
	 * @param path
	 *            new file path
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Sets new modification status
	 * 
	 * @param modified
	 *            modification status
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
}
