package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Simple application for generating prime numbers and listing them on screen.
 * Implementation is based on MVC pattern and it demonstrates usage of one model
 * and two viewers (two instances of <tt>JList</tt> component).
 * 
 * @author Marin Jurjevic
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new JFrame for showing content
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimGenerator");
		setLocation(200, 100);
		setSize(300,450);
		setMinimumSize(new Dimension(75,150));
		initGUI();
	}

	/**
	 * Initialises user interface for interaction with model.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JPanel listPanel = new JPanel(new GridLayout(1, 2));
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		listPanel.add(new JScrollPane(list1));
		listPanel.add(new JScrollPane(list2));
		listPanel.setMinimumSize(new Dimension(200, 100));

		JButton next = new JButton("Next prime");
		next.addActionListener(l -> {
			model.next();
		});

		cp.add(listPanel, BorderLayout.CENTER);
		cp.add(next, BorderLayout.PAGE_END);
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}

}
