package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

public abstract class AbstractRectangle extends GeometricShape {

	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * Constructs new rectangle with position (x,y) and specified dimensions.
	 * 
	 * @param x
	 *            x coordinate of upper-left corner
	 * @param y
	 *            y coordinate of upper-left corner
	 * @param width
	 *            rectangle width
	 * @param height
	 *            rectangle height
	 */
	protected AbstractRectangle(int x, int y, int width, int height) {
		super();
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Invalid dimensions");
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(BWRaster raster) {
		int rWidth = raster.getWidth();
		int rHeight = raster.getHeight();
		// check if not on raster at all
		if (x + width < 0 || x >= rWidth)
			return;
		if (y + width < 0 || y >= rHeight)
			return;

		// calculate bounds of rectangle's vertices
		int xStart = x < 0 ? 0 : x;
		int yStart = y < 0 ? 0 : y;
		int xEnd = x + width > rWidth ? rWidth : x + width;
		int yEnd = y + height > rHeight ? rHeight : y + height;

		for (int i = yStart; i < yEnd; i++) {
			for (int j = xStart; j < xEnd; j++) {
				if (containsPoint(j, i) == true) {
					raster.turnOn(j, i);
				}
			}
		}

	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x)
			return false;
		if (x >= this.x + width)
			return false;
		if (y < this.y)
			return false;
		if (y >= this.y + height)
			return false;

		return true;
	}

	/**
	 * @return x x coordinate of upper-left corner
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @param x
	 *            sets new x coordinate for upper-left corner
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @return y coordinate of upper-left corner
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param y
	 *            sets new y coordinate for upper-left corner
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 
	 * @return rectangle's width
	 */
	protected int getWidth() {
		return width;
	}

	/**
	 * Sets rectangle's new width (only whole number greater than 0)
	 * 
	 * @param width
	 *            new width of rectangle
	 */
	protected void setWidth(int width) {
		if (height < 1) {
			throw new IllegalArgumentException("Width  must be greater than 0");
		}
		this.width = width;
	}

	/**
	 * 
	 * @return rectangle's height
	 */
	protected int getHeight() {
		return height;
	}

	/**
	 * Sets rectangle's new height (only whole number greater than 0)
	 * 
	 * @param height
	 *            new height of rectangle
	 */
	protected void setHeight(int height) {
		if (height < 1) {
			throw new IllegalArgumentException("Height must be greater than 0");
		}
		this.height = height;
	}

}
