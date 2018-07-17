package hr.fer.zemris.java.graphics.shapes;

/**
 * Representation of circle. Circle has
 * 
 * @author Marin Jurjevic
 *
 */
public class Circle extends OvalShape {

	/**
	 * Creates new circle with specified position (x,y) of center and radius.
	 * 
	 * @param x
	 *            x coordinate of center
	 * @param y
	 *            y coordinate of center
	 * @param radius
	 *            radius of a circle
	 */
	public Circle(int x, int y, int radius) {
		super(x, y, radius, radius);

		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be greater than 0");
		}

	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (Math.pow(x - super.x, 2) + Math.pow(y - super.y, 2) > Math.pow(super.vAxis, 2)) { // or
																								// hAxis
			return false;
		}
		return true;
	}

	/**
	 * @return circle's radius
	 */
	public int getRadius() {
		return super.vAxis; // or hAxis
	}

	/**
	 * Changes circle's radius to a whole number grater than 0.
	 * 
	 * @param radius circle radius
	 * @throws IllegalArgumentException
	 *             if given radius is smaller than 1
	 */
	public void setRadius(int radius) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be greater than 0");
		}
		super.hAxis = radius;
		super.vAxis = radius;
	}

}
