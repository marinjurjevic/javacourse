package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.display.Display;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Implementation of a calculator using Swing framework.
 * 
 * @author Marin Jurjevic
 *
 */
public class Calculator extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5016122500695877672L;

	/**
	 * Main panel for laying components on content pane.
	 */
	private static JPanel panel;

	/**
	 * Display object used for printing results and user input.
	 */
	private static Display display;

	/**
	 * current two-parameter operation ready to be executed
	 */
	private static BiFunction<Double, Double, Double> curOperation;

	/**
	 * Java's implementation of stack used as calculator's feature
	 */
	private static Stack<String> stack;

	/**
	 * status of JCheckBox component for applying invers operations
	 */
	private static boolean invers;

	/**
	 * Constructor for setting up frame.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setMinimumSize(new Dimension(450, 275));
		setLocation(500, 250);
		setTitle("Kalkulator");
		initGUI();
	}

	/**
	 * Initialising graphical user interface for interaction.
	 */
	private void initGUI() {
		panel = new JPanel(new CalcLayout(5));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		display = new Display();

		panel.add(display, "1,1");

		addDigits();
		addTrigonometryFunctions();
		addBasicOperations();
		addAdditionalOperatins();
		addControls();
		addStack();

		getContentPane().add(panel);
	}

	/**
	 * Application starting point.
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

	/**
	 * Adds digit buttons to the panel. Also, it adds decimal point button and
	 * sign button.
	 */
	private void addDigits() {
		JButton[] digits = new JButton[10];
		digits[0] = addButton("0", "5,3");
		digits[1] = addButton("1", "4,3");
		digits[2] = addButton("2", "4,4");
		digits[3] = addButton("3", "4,5");
		digits[4] = addButton("4", "3,3");
		digits[5] = addButton("5", "3,4");
		digits[6] = addButton("6", "3,5");
		digits[7] = addButton("7", "2,3");
		digits[8] = addButton("8", "2,4");
		digits[9] = addButton("9", "2,5");

		for (int i = 0; i < digits.length; i++) {
			String digit = digits[i].getText();
			digits[i].addActionListener(l -> {
				display.addText(digit);
			});
		}
		// add +/- and dot
		addButton("+/-", "5,4").addActionListener(l -> {
			display.changeSign();
		});
		addButton(".", "5,5").addActionListener(l -> {
			display.addText(".");
		});

	}

	/**
	 * Adds trigonometry functions to panel.
	 */
	private void addTrigonometryFunctions() {
		addButton("sin", "2,2").addActionListener(l -> {
			if (!invers) {
				executeSingleParameter(Math::sin);
			} else {
				executeSingleParameter(Math::asin);
			}
		});
		addButton("cos", "3,2").addActionListener(l -> {
			if (!invers) {
				executeSingleParameter(Math::cos);
			} else {
				executeSingleParameter(Math::acos);
			}
		});
		addButton("tan", "4,2").addActionListener(l -> {
			if (!invers) {
				executeSingleParameter(Math::tan);
			} else {
				executeSingleParameter(v -> Math.atan(1 / v));
			}
		});
		addButton("ctg", "5,2").addActionListener(l -> {
			executeSingleParameter(v -> 1 / Math.tan(v));
		});
	}

	/**
	 * Adds basic operation buttons to the main panel. It also adds equal sign
	 * to perform previously selected operation.
	 */
	private void addBasicOperations() {
		addButton("+", "5,6").addActionListener(l -> {
			executeDoubleParameter((a, b) -> a + b);
		});
		addButton("-", "4,6").addActionListener(l -> {
			executeDoubleParameter((a, b) -> a - b);
		});
		addButton("/", "3,6").addActionListener(l -> {
			executeDoubleParameter((a, b) -> a / b);
		});
		addButton("*", "2,6").addActionListener(l -> {
			executeDoubleParameter((a, b) -> a * b);
		});

		addButton("=", "1,6").addActionListener(l -> {
			executeDoubleParameter(curOperation);
			curOperation = null;
		});
	}

	/**
	 * Adds additional operations to the main panel.
	 */
	private void addAdditionalOperatins() {
		addButton("1/x", "2,1").addActionListener(l -> {
			executeSingleParameter(x -> 1 / x);
		});
		addButton("log", "3,1").addActionListener(l -> {
			if (!invers) {
				executeSingleParameter(Math::log10);
			} else {
				executeSingleParameter(x -> Math.pow(10, x));
			}
		});
		addButton("ln", "4,1").addActionListener(l -> {
			if (!invers) {
				executeSingleParameter(Math::log);
			} else {
				executeSingleParameter(x -> Math.pow(Math.E, x));
			}
		});
		addButton("x^n", "5,1").addActionListener(l -> {
			if (!invers) {
				executeDoubleParameter((x, n) -> Math.pow(x, n));
			} else {
				executeDoubleParameter((x, n) -> Math.pow(x, 1 / n));
			}
		});
	}

	/**
	 * Adds stack buttons to the main panel.
	 */
	private void addStack() {
		stack = new Stack<>();
		addButton("push", "3,7").addActionListener(l -> {
			stack.push(display.getText());
		});
		addButton("pop", "4,7").addActionListener(l -> {
			try {
				display.setText(stack.pop());
			} catch (EmptyStackException e) {
				JOptionPane.showMessageDialog(panel, "Stog je prazan!", "Greška!", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	/**
	 * Adds simple controls for controlling program flow. clr button - clears
	 * current input res button - resets calculator inv checkbox - if checked,
	 * calculator applies invers operations
	 */
	private void addControls() {
		addButton("clr", "1,7").addActionListener(l -> {
			display.setText("0");
		});

		addButton("res", "2,7").addActionListener(l -> {
			display.reset();
			curOperation = null;
		});

		JCheckBox inv = new JCheckBox("inv");
		inv.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255)));
		inv.setBackground(new Color(102, 178, 255));
		inv.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		inv.setPreferredSize(new Dimension(50, 50));
		inv.addActionListener(l -> {
			invers = invers ? false : true;
		});
		panel.add(inv, "5,7");
	}

	/**
	 * Creates and adds new button to main panel. Created button is then
	 * returned to the caller.
	 * 
	 * @param text
	 *            button text
	 * @param position
	 *            button position, constraint in String representation
	 * @return created JButton
	 */
	private JButton addButton(String text, String position) {
		JButton digit = new JButton(text);
		digit.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255)));
		digit.setBackground(new Color(102, 178, 255));
		digit.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		digit.setPreferredSize(new Dimension(50, 50));
		panel.add(digit, position);
		return digit;
	}

	/**
	 * Executes given operation with single parameter and stores result in
	 * internal display storage.
	 * 
	 * @param operation
	 *            operation to be executed
	 */
	private void executeSingleParameter(Function<Double, Double> operation) {
		Double result = operation.apply(Double.parseDouble(display.getText()));
		display.setText(String.valueOf(result));
		display.store();
	}

	/**
	 * Executed given operation with two parameters and stores result in
	 * internal display storage.
	 * 
	 * @param operation
	 *            operation to be executed
	 */
	private void executeDoubleParameter(BiFunction<Double, Double, Double> operation) {
		display.startOperation();
		if (curOperation == null) {
			display.store(Double.parseDouble(display.getText()));
		} else {
			Double result = curOperation.apply(display.getStored(), Double.parseDouble(display.getText()));
			display.store(result);
			display.setText(String.valueOf(result));
		}
		curOperation = operation;
	}
}
