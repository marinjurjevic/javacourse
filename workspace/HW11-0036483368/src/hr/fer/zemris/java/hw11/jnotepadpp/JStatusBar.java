package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * <tt>JStatusBar</tt> is a model of simple status bar that encapsulates
 * information about some opened document.
 * 
 * @author Marin Jurjevic
 *
 */
public class JStatusBar extends JPanel {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * label for showing document total length
	 */
	private JLabel lLength;

	/**
	 * label for showing current caret line position
	 */
	private JLabel lLine;

	/**
	 * label for showing current caret column position
	 */
	private JLabel lColumn;

	/**
	 * label for showing current selected text length
	 */
	private JLabel lSelection;

	/**
	 * label for showing current date and time
	 */
	private JLabel clock;

	/**
	 * current document total length
	 */
	private int length;

	/**
	 * current document caret line position
	 */
	private int line;

	/**
	 * current document caret column position
	 */
	private int col;

	/**
	 * current document selected text length
	 */
	private int sel;

	/**
	 * flag for stopping thread that updates clock label
	 */
	private boolean stop;

	/**
	 * Creates new JStatusBar. Note that by creating new JStatusBar, new daemon
	 * thread is created for updating clock used in this status bar.
	 */
	public JStatusBar() {
		lLength = new JLabel(" length: " + length);
		lLine = new JLabel(" Ln: " + line);
		lLine.setBorder(new EmptyBorder(0, 0, 0, 10));
		lColumn = new JLabel("Col: " + col);
		lColumn.setBorder(new EmptyBorder(0, 0, 0, 10));
		lSelection = new JLabel("Sel: " + sel);

		runClock();
		clock.setBorder(new EmptyBorder(0, 0, 0, 10));

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(lLength);
		this.add(Box.createHorizontalGlue());
		this.add(lLine);
		this.add(lColumn);
		this.add(lSelection);
		this.add(Box.createHorizontalGlue());
		this.add(clock);
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
	}

	/**
	 * Creates new clock label for displaying current date and time. Date and
	 * time are formatted using pattern "yyyy/MM/dd HH:mm:ss" in
	 * SimpleDateFormat.
	 */
	private void runClock() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		clock = new JLabel(df.format(Calendar.getInstance().getTime()));
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				if (stop) {
					break;
				}
				SwingUtilities.invokeLater(() -> {
					clock.setText(df.format(Calendar.getInstance().getTime()));
				});
			}
		});

		thread.setDaemon(true);
		thread.start();

	}

	/**
	 * Sets new document total length
	 * 
	 * @param length
	 *            document total length
	 */
	public void setLength(int length) {
		this.length = length;
		lLength.setText(" length: " + length);
	}

	/**
	 * Sets current caret line position.
	 * 
	 * @param line
	 *            caret line position
	 */
	public void setLine(int line) {
		this.line = line;
		lLine.setText(" Ln: " + line);
	}

	/**
	 * Sets current caret line position.
	 * 
	 * @param col
	 *            caret column position
	 */
	public void setCol(int col) {
		this.col = col;
		lColumn.setText(" Col: " + col);
	}

	/**
	 * Sets current document text selection length.
	 * 
	 * @param sel
	 *            selection length
	 */
	public void setSel(int sel) {
		this.sel = sel;
		lSelection.setText(" Sel: " + sel);
	}

	/**
	 * Stops clock. It's advised you call this method when editor is closed.
	 */
	public void stopClock() {
		stop = true;
	}

	/**
	 * Resets status bar.
	 */
	public void reset() {
		setLength(0);
		setLine(0);
		setCol(0);
		setSel(0);
		this.validate();
	}

}
