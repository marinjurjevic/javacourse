package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration program for using {@link BarChartComponent} component
 * implementation.
 * 
 * @author Marin Jurjevic
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of BarChart model for passing it to BarCHartComponent
	 */
	private static BarChart model;

	/**
	 * Creates new frame for showing BarChart information,
	 * 
	 * @param path
	 *            path to the file containing info
	 */
	public BarChartDemo(String path) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChart");
		setLocation(200, 100);
		setSize(500, 400);
		setMinimumSize(new Dimension(500, 300));
		initGUI(path);
	}

	/**
	 * Initialises GUI which will draw BarChar on screen.
	 * 
	 * @param path
	 */
	private void initGUI(String path) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JLabel label = new JLabel(Paths.get(path).toAbsolutePath().toString());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(label, BorderLayout.PAGE_START);

		cp.add(new BarChartComponent(model), BorderLayout.CENTER);
		cp.setBackground(Color.WHITE);
	}

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            path to the text file where char info is stored
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}

		processData(args[0]);
		SwingUtilities.invokeLater(() -> new BarChartDemo(args[0]).setVisible(true));
	}

	/**
	 * Processes data from text file and retrieves info and encapsulates it into
	 * BarChart model object.
	 * 
	 * @param path
	 *            path to formatted text file contaning info necessary to draw
	 *            BarChart
	 */
	private static void processData(String path) {

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(path))))) {

			List<XYValue> list = new LinkedList<>();

			String xDesc = br.readLine();
			String yDesc = br.readLine();
			String[] intervals = br.readLine().split(" ");
			int yMin = Integer.parseInt(br.readLine());
			int yMax = Integer.parseInt(br.readLine());
			int diff = Integer.parseInt(br.readLine());

			// add columns
			for (String s : intervals) {
				String[] i = s.split(",");
				list.add(new XYValue(Integer.parseInt(i[0]), Integer.parseInt(i[1])));
			}

			model = new BarChart(list, xDesc, yDesc, yMin, yMax, diff);
		} catch (FileNotFoundException e) {
			System.out.println("File not found! \n Shutting down application");
			System.exit(-1);
		} catch (RuntimeException | IOException e) {
			System.out.println("Error while processing data file. \n Shutting down application");
			System.exit(-1);
		}
	}

}
