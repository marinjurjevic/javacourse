package hr.fer.zemris.java.graphics.shapes;

/**
 * OvalShape represents an abstract representation of oval shapes. OvalShape in
 * this context will be defined by two axes of symmetry (x and y axe).
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class OvalShape extends GeometricShape {

	/**
	 * x-coordinate of oval's center
	 */
	protected int x;

	/**
	 * y-coordinate of oval's center
	 */
	protected int y;

	/**
	 * horizontal axis size
	 */
	protected int hAxis;

	/**
	 * vertical axis size
	 */
	protected int vAxis;

	protected OvalShape(int x, int y, int hAxis, int vAxis) {
		this.x = x;
		this.y = y;
		this.vAxis = vAxis;
		this.hAxis = hAxis;
	}

	/**
	 * 
	 * @return value of center's x-axis coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Moves center along the x axis on specified value.
	 * 
	 * @param x
	 *            new center's x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return value of center's y-axis coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Moves center along the y axis on specified value.
	 * 
	 * @param y
	 *            new center's y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return size of horizontal axis
	 */
	protected int gethAxis() {
		return hAxis;
	}

	/**
	 * Sets new size of horizontal axis
	 * 
	 * @param hAxis
	 *            horizontal axis size
	 */
	protected void sethAxis(int hAxis) {
		this.hAxis = hAxis;
	}

	/**
	 * @return size of vertical axis
	 */
	protected int getvAxis() {
		return vAxis;
	}

	/**
	 * Sets new size of vertical axis
	 * 
	 * @param vAxis
	 *            vertical axis size
	 */
	protected void setvAxis(int vAxis) {
		this.vAxis = vAxis;
	}

}
