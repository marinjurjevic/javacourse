package hr.fer.zemris.java.gui.charts;

/**
 * <tt>XYValue</tt> represents position on x axis and height of column used in
 * <tt>BarChart</tt> model.
 * 
 * @author Marin Jurjevic
 *
 */
public class XYValue {

	/**
	 * value on x axis
	 */
	private final int x;

	/**
	 * value on y axis
	 */
	private final int y;

	/**
	 * Creates new instance of XYValue used for column hight and position on x
	 * axis.
	 * 
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Value of x axis.
	 * 
	 * @return x axis value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Value of y axis.
	 * 
	 * @return y axis value
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XYValue other = (XYValue) obj;
		if (x != other.x)
			return false;
		return true;
	}

}
