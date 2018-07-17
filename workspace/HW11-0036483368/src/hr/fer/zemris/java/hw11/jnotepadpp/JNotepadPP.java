package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortSelectedAscendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortSelectedDescendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToLowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToUpperCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueLinesAction;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * JNotepadPP is a simple version of Notepad++ text editor. JNotepaddPP offers
 * file management used to:
 * 
 * <pre>
 * <li> create new document
 * <li> open existing document
 * <li> save/save as document
 * <li> close document
 * </br>
 * </pre>
 * <p>
 * Furthermore, it offers standard document manipulation tools like cut,copy and
 * paste text. It also offers few utility functions like removing duplicate
 * lines and sorting lines according to appropriate language.
 * </p>
 * 
 * @author Marin Jurjevic
 *
 */
public class JNotepadPP extends JFrame implements IEditor {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * tabbed pane for supporting work on multiple documents
	 */
	private JTabbedPane tabbedPane;

	/**
	 * status bar for tracking text length, current caret position and selection
	 * length
	 */
	private JStatusBar statusBar;

	/**
	 * list of file models used by each opened tab
	 */
	private List<FileModel> files;

	/**
	 * index of currently focused tab
	 */
	private static int focusIndex;

	/**
	 * instance of FormLocalizationProvider for stopping memory leaks
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Creates new instance of JNotepadPP and initializes GUI.
	 */
	public JNotepadPP() {
		files = new LinkedList<>();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepadPP");
		setLocation(100, 100);
		setSize(800, 500);
		initGUI();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				statusBar.stopClock();
			}

		});
	}

	/**
	 * Initializes GUI. It adds menus and toolbar and gives them appropriate
	 * actions to perform when activated.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		// setup listener on tab switching
		tabbedPane.addChangeListener(l -> {
			focusIndex = tabbedPane.getSelectedIndex();
			if (focusIndex == -1) {
				setTitle("JNotepadPP");
			} else {
				FileModel fm = files.get(focusIndex);
				setTitle(fm.getPath().toString() + " - JNotepadPP");
				tabbedPane.setTitleAt(focusIndex, fm.getPath().getFileName().toString());
				if (fm.getModified()) {
					saveDocumentAction.setEnabled(true);
				} else {
					saveDocumentAction.setEnabled(false);
				}
				statusBar.setLength(fm.getText().getDocument().getLength());

				if (fm.getText().getCaretListeners().length == 0) {
					return;
				}

				// when tabs switch, must update status bar caret information
				fm.getText().getCaretListeners()[0].caretUpdate(new CaretEvent(this) {
					private static final long serialVersionUID = 1L;

					@Override
					public int getMark() {
						return fm.getText().getCaret().getDot();
					}

					@Override
					public int getDot() {
						return fm.getText().getCaret().getMark();
					}
				});
			}

		});

		// add new panel so that tool bar can float freely
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(tabbedPane, BorderLayout.CENTER);
		createStatusBar(panel);
		cp.add(panel, BorderLayout.CENTER);

		createMenus();
		createToolbars();
	}

	/**
	 * Creates and adds menus to GUI ready for user interaction.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		// FILE MENU
		JMenu fileMenu = createMenu("fileMenu");
		fileMenu.add(newDocumentAction);
		fileMenu.add(openDocumentAction);
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveAsDocumentAction);
		fileMenu.add(getStatsDocumentAction);
		fileMenu.add(closeDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		menuBar.add(fileMenu);

		// EDIT MENU
		JMenu editMenu = createMenu("editMenu");
		editMenu.add(cutDocumentAction);
		editMenu.add(copyDocumentAction);
		editMenu.add(pasteDocumentAction);
		menuBar.add(editMenu);

		// LANGUAGES MENU
		JMenu langMenu = createMenu("langMenu");
		langMenu.add(new ChangeLanguageAction("hr", this));
		langMenu.add(new ChangeLanguageAction("en", this));
		langMenu.add(new ChangeLanguageAction("de", this));
		menuBar.add(langMenu);

		// TOOL MENU
		JMenu toolMenu = createMenu("toolMenu");

		JMenu changeCaseMenu = createMenu("changeCaseMenu");
		changeCaseMenu.add(toUpperCaseAction);
		changeCaseMenu.add(toLowerCaseAction);
		changeCaseMenu.add(invertCaseAction);
		toolMenu.add(changeCaseMenu);

		JMenu sortMenu = createMenu("sortMenu");
		sortMenu.add(sortSelectedAscendingAction);
		sortMenu.add(sortSelectedDescendingAction);
		toolMenu.add(sortMenu);

		toolMenu.add(uniqueLinesAction);
		menuBar.add(toolMenu);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates new JMenu and registers it on localization provider for dynamic
	 * language change.
	 * 
	 * @param key
	 *            key in properties file
	 * @return new instance of JMenu
	 */
	private JMenu createMenu(String key) {
		JMenu menu = new JMenu(flp.getString(key));
		flp.addLocalizationListener(() -> {
			menu.setText(flp.getString(key));
		});
		return menu;
	}

	/**
	 * Creates and adds tool bar to GUI ready for user interaction.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(newDocumentAction);
		toolBar.add(openDocumentAction);
		toolBar.add(saveDocumentAction);
		toolBar.add(saveAsDocumentAction);
		toolBar.add(closeDocumentAction);
		toolBar.add(getStatsDocumentAction);
		toolBar.addSeparator();

		toolBar.add(cutDocumentAction);
		toolBar.add(copyDocumentAction);
		toolBar.add(pasteDocumentAction);
		toolBar.addSeparator();

		toolBar.add(toUpperCaseAction);
		toolBar.add(toLowerCaseAction);
		toolBar.add(invertCaseAction);
		toolBar.addSeparator();

		toolBar.add(sortSelectedAscendingAction);
		toolBar.add(sortSelectedDescendingAction);
		toolBar.add(uniqueLinesAction);

		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(exitAction);
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates new instance of <tt>JStatusBar</tt> for tracking document info.
	 * 
	 * @param panel
	 */
	private void createStatusBar(JPanel panel) {
		statusBar = new JStatusBar();
		panel.add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Application starting point.
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

	/**
	 * Registers listeners on given instance of JTextArea for triggering wanted
	 * actions in appropriate way.
	 * 
	 * @param textArea
	 */
	private void addListeners(JTextArea textArea) {
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				execute(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				execute(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				execute(e);
			}

			private void execute(DocumentEvent e) {
				// when change on document occurs -> change tab icon, enable
				// save action icon, update length
				tabbedPane.setIconAt(focusIndex, Icons.TAB_MODIFIED_ICON);
				files.get(focusIndex).setModified(true);
				saveDocumentAction.setEnabled(true);
				statusBar.setLength(e.getDocument().getLength());
			}
		});

		// on caret change, update status bar, and if selection happened,
		// enable/disable appropriate tools
		textArea.addCaretListener(l -> {
			int caret = l.getDot();
			int len = Math.abs(caret - l.getMark());

			try {
				int line = textArea.getLineOfOffset(caret);
				statusBar.setLine(line + 1);
				statusBar.setCol(caret - textArea.getLineStartOffset(line));
			} catch (Exception ignorable) {
			}

			statusBar.setSel(len);
			if (len > 0) {
				setAvailableTools(true);
			} else {
				setAvailableTools(false);
			}
		});
	}

	/**
	 * Sets availability of tools that work on selected text.
	 * 
	 * @param value
	 *            true for tools to become available, false for not available
	 */
	private void setAvailableTools(boolean value) {
		cutDocumentAction.setEnabled(value);
		copyDocumentAction.setEnabled(value);
		toUpperCaseAction.setEnabled(value);
		toLowerCaseAction.setEnabled(value);
		invertCaseAction.setEnabled(value);
		sortSelectedAscendingAction.setEnabled(value);
		sortSelectedDescendingAction.setEnabled(value);
		uniqueLinesAction.setEnabled(value);
	}

	/*
	 * -----------------------------------------------------
	 * -------------- CREATING ACTIONS ---------------------
	 * -----------------------------------------------------
	 */

	/**
	 * creates new document and tab in JTabbedPane
	 */
	private Action newDocumentAction = new NewDocumentAction(this);

	/**
	 * triggers JFileChooser dialog for user to choose which file to open
	 */
	private Action openDocumentAction = new OpenDocumentAction(this);

	/**
	 * saves currently focused document
	 */
	private Action saveDocumentAction = new SaveDocumentAction(this);

	/**
	 * saves currently focused document on user-specified location
	 */
	private Action saveAsDocumentAction = new SaveAsDocumentAction(this);

	/**
	 * closes currently focused document, triggers confirm dialog if file hasn't
	 * been saved
	 */
	private Action closeDocumentAction = new CloseDocumentAction(this);

	/**
	 * calculates statistics of current document and shows it in pop-up message
	 */
	private Action getStatsDocumentAction = new StatisticsDocumentAction(this);

	/**
	 * cuts selected text
	 */
	private Action cutDocumentAction = new CutDocumentAction(this);

	/**
	 * copy selected text
	 */
	private Action copyDocumentAction = new CopyDocumentAction(this);

	/**
	 * pastes text from clipboard
	 */
	private Action pasteDocumentAction = new PasteDocumentAction(this);

	/**
	 * exits application, triggering confirm dialogs for unsaved files
	 */
	private Action exitAction = new ExitAction(this);

	/**
	 * switches selected text to upper case
	 */
	private Action toUpperCaseAction = new ToUpperCaseAction(this);

	/**
	 * switches selected text to lower case
	 */
	private Action toLowerCaseAction = new ToLowerCaseAction(this);

	/**
	 * inverts selected text's case
	 */
	private Action invertCaseAction = new InvertCaseAction(this);

	/**
	 * sorts selected lines in ascending order
	 */
	private Action sortSelectedAscendingAction = new SortSelectedAscendingAction(this);

	/**
	 * sorts selected lines in descending order
	 */
	private Action sortSelectedDescendingAction = new SortSelectedDescendingAction(this);

	/**
	 * removes line duplicates
	 */
	private Action uniqueLinesAction = new UniqueLinesAction(this);

	/*
	 * -----------------------------------------------------
	 * ---------- IMPLEMENTING IEDITOR INTERFACE -----------
	 * -----------------------------------------------------
	 */

	@Override
	public void addNewDocument(Path filePath, JTextArea text, boolean isNewDocument) {
		if (text == null) {
			tabbedPane.setSelectedIndex(indexOfFile(filePath));
			return;
		}

		files.add(new FileModel(filePath, text));

		tabbedPane.add(filePath.getFileName().toString(), new JScrollPane(text));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		tabbedPane.setIconAt(focusIndex, Icons.TAB_SAVED_ICON);

		if (isNewDocument) {
			tabbedPane.setToolTipTextAt(tabbedPane.getTabCount() - 1, filePath.toString());
		} else {
			tabbedPane.setToolTipTextAt(tabbedPane.getTabCount() - 1, filePath.toAbsolutePath().toString());
		}
		addListeners(text);
		statusBar.reset();
		setAvailableFilesActions(true);
	}

	@Override
	public boolean containsFile(Path filePath) {
		for (FileModel fm : files) {
			if (fm.getPath().equals(filePath)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public FileModel getCurrentFileModel() {
		return files.get(focusIndex);
	}

	@Override
	public void saveDocument(Path filePath) {
		if (filePath != null) {
			setTitle(filePath + " - JNotepadPP");
			tabbedPane.setTitleAt(focusIndex, filePath.getFileName().toString());
			tabbedPane.setToolTipTextAt(focusIndex, filePath.toAbsolutePath().toString());
		}
		tabbedPane.setIconAt(focusIndex, Icons.TAB_SAVED_ICON);
		saveDocumentAction.setEnabled(false);
	}

	@Override
	public void saveNewDocument() {
		saveAsDocumentAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		saveDocumentAction.setEnabled(false);
	}

	@Override
	public void closeCurrentDocument(boolean save) {
		if (save) {
			saveDocumentAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}

		files.remove(focusIndex);
		tabbedPane.remove(focusIndex);

		if (files.isEmpty()) {
			saveDocumentAction.setEnabled(false);
			setAvailableFilesActions(false);
		}

		statusBar.reset();
	}

	@Override
	public ILocalizationProvider getLProvider() {
		return flp;
	}

	@Override
	public void exit() {
		for (int i = 0; i < files.size(); i++) {
			FileModel current = files.get(i);
			if (current.getModified()) {
				tabbedPane.setSelectedIndex(i);
				int odluka = MessageDialogs.getSaveConfirm(this);
				if (odluka == JOptionPane.CANCEL_OPTION) {
					return;
				} else {
					if (odluka == JOptionPane.YES_OPTION) {
						closeCurrentDocument(true);
					} else {
						closeCurrentDocument(false);
					}
					i--;
				}
			}
		}
		dispose();
	}

	/**
	 * Returns index of file with specified path if it's open. If file is not
	 * open -1 will be returned.
	 * 
	 * @param path
	 *            path to be searched in currently opened files
	 * @return index of tab, -1 if no tab shows file with this path
	 */
	private int indexOfFile(Path path) {
		int index = -1;
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).getPath().equals(path)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Sets availiability of actions that manage file actions.
	 * 
	 * @param value
	 *            true if action is available, false if not
	 */
	private void setAvailableFilesActions(boolean value) {
		saveAsDocumentAction.setEnabled(value);
		closeDocumentAction.setEnabled(value);
		getStatsDocumentAction.setEnabled(value);
		pasteDocumentAction.setEnabled(value);
	}
}
