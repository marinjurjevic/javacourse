package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * BarChart is a model of bar chart for showing 2D graph on screen.
 * 
 * @author Marin Jurjevic
 *
 */
public class BarChart {

	/**
	 * columns used in this char
	 */
	private List<XYValue> columns;

	/**
	 * description displayed below x axis
	 */
	private String xDesc;

	/**
	 * description displayed next to y axis
	 */
	private String yDesc;

	/**
	 * maximum value displayed on y axis
	 */
	private int yMax;

	/**
	 * minimal value displayed on y axis
	 */
	private int yMin;

	/**
	 * difference between two y values
	 */
	private int yDiff;

	/**
	 * @param columns
	 * @param xDesc
	 * @param yDesc
	 * @param yMax
	 * @param yMin
	 * @param yDiff
	 */
	public BarChart(List<XYValue> columns, String xDesc, String yDesc, int yMin, int yMax, int yDiff) {
		super();
		this.columns = columns;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.yMax = yMax;
		this.yMin = yMin;
		this.yDiff = yDiff;
	}

	/**
	 * Columns information used for drawing them on chart.
	 * 
	 * @return information about columns
	 * @see XYValue
	 */
	public List<XYValue> getColumns() {
		return columns;
	}

	/**
	 * Description on to be drawn below x axis.
	 * 
	 * @return text
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * Description on to be next to y axis
	 * 
	 * @return text
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * Maximal value on y axis.
	 * 
	 * @return whole number value
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Minimal value on y axis.
	 * 
	 * @return whole number value
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Difference between two y values.
	 * 
	 * @return whole number value
	 */
	public int getyDiff() {
		return yDiff;
	}

}
