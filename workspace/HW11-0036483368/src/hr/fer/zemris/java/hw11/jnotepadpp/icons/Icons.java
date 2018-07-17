package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Icons class consists of public static Icon objects for fast retrieval of
 * icons for specific actions.
 * 
 * @author Marin Jurjevic
 *
 */
public class Icons {

	/**
	 * new file action icon
	 */
	public static final Icon NEW_FILE_ICON = createIcon("icons/newFile.png");

	/**
	 * open file action icon
	 */
	public static final Icon OPEN_FILE_ICON = createIcon("icons/openFile.png");

	/**
	 * save file action icon
	 */
	public static final Icon SAVE_FILE_ICON = createIcon("icons/saveFile.png");

	/**
	 * save as file action icon
	 */
	public static final Icon SAVE_AS_ICON = createIcon("icons/saveAs.png");

	/**
	 * close file action icon
	 */
	public static final Icon CLOSE_FILE_ICON = createIcon("icons/closeFile.png");

	/**
	 * cut selected text action icon
	 */
	public static final Icon CUT_ICON = createIcon("icons/cut.png");

	/**
	 * copy selected text action icon
	 */
	public static final Icon COPY_ICON = createIcon("icons/copy.png");

	/**
	 * paste text action icon
	 */
	public static final Icon PASTE_ICON = createIcon("icons/paste.png");

	/**
	 * statistics action icon
	 */
	public static final Icon STATS_ICON = createIcon("icons/stats.png");

	/**
	 * exit application action icon
	 */
	public static final Icon EXIT_ICON = createIcon("icons/exit.png");

	/**
	 * tab modified icon action (if file has changed since last save)
	 */
	public static final Icon TAB_MODIFIED_ICON = createIcon("icons/unsaved.png");

	/**
	 * tab saved icon action (if file has been unchanged since last save)
	 */
	public static final Icon TAB_SAVED_ICON = createIcon("icons/saved.png");

	/**
	 * to upper case action icon
	 */
	public static final Icon TO_UPPER_CASE_ICON = createIcon("icons/upperCase.png");

	/**
	 * to lower case action icon
	 */
	public static final Icon TO_LOWER_CASE_ICON = createIcon("icons/lowerCase.png");

	/**
	 * invert case action icon
	 */
	public static final Icon INVERT_CASE_ICON = createIcon("icons/invertCase.png");

	/**
	 * sort ascending action icon
	 */
	public static final Icon SORT_ASC_ICON = createIcon("icons/sortAsc.png");

	/**
	 * sort descending action icon
	 */
	public static final Icon SORT_DESC_ICON = createIcon("icons/sortDesc.png");

	/**
	 * unique lines action icon
	 */
	public static final Icon UNIQUE_ICON = createIcon("icons/unique.png");

	/**
	 * Factory method for loading icons.
	 * 
	 * @param path
	 *            icon path
	 * @return new instance of ImageIcon
	 */
	private static Icon createIcon(String path) {
		return new ImageIcon(JNotepadPP.class.getResource(path));
	}
}
