package hr.fer.zemris.java.gui.calc.display;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Implementation of simple display for calculator application. It is extending
 * JLabel as label will "simulate" display.
 * 
 * @author Marin Jurjevic
 *
 */
public class Display extends JLabel {

	/**
	 * internal memory, a double type
	 */
	private double storage;
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * flag for tracking is an operation underway
	 */
	private boolean operation;

	/**
	 * Creates new display which is used for displaying result and user input.
	 */
	public Display() {
		super("0", SwingConstants.RIGHT);
		setBackground(Color.ORANGE);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255)));
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
	}

	/**
	 * Stores current value on <tt>Display</tt> in internal storage.
	 */
	public void store() {
		storage = Double.parseDouble(getText() == "" ? "0" : getText());
	}

	/**
	 * Stores given number into internal memory.
	 * 
	 * @param number
	 *            number to be stored
	 */
	public void store(double number) {
		storage = number;
	}

	/**
	 * Resets display and stored number.
	 */
	public void reset() {
		setText("0");
		storage = 0;
		operation = false;
	}

	/**
	 * Concatenates given string with current text on screen.
	 * 
	 * @param text
	 */
	public void addText(String text) {
		if (getText().equals("0") && !text.equals(".")) {
			setText("");
		}
		if (operation) {
			store();
			setText("");
			operation = false;
		}

		setText(getText() + text);
	}

	/**
	 * Changes sign on display.
	 */
	public void changeSign() {
		if (getText().startsWith("-")) {
			setText(getText().substring(1));
		} else {
			setText("-" + getText());
		}
	}

	/**
	 * Returns currently stored number from internal memory.
	 * 
	 * @return current value
	 */
	public double getStored() {
		return storage;
	}

	/**
	 * Raises flag for operation mode.
	 */
	public void startOperation() {
		operation = true;
	}

	/**
	 * Turns down flag for operation mode.
	 */
	public void stopOperation() {
		operation = false;
	}
}
