package hr.fer.zemris.java.graphics.shapes;

/**
 * Representation of an ellipse. An ellipse is a curve on a plane that surrounds
 * two focal points such that the sum of the distances to the two focal points
 * is constant for every point on the curve.
 * 
 * @author Marin Jurjevic
 *
 */
public class Ellipse extends OvalShape {

	/**
	 * Creates new ellipse with specified position (x,y) in raster space and
	 * horizontal and vertical axis size.
	 * 
	 * @param x
	 *            x coordinate of center
	 * @param y
	 *            y coordinate of center
	 * @param hAxis
	 *            size of horizontal axis
	 * @param vAxis
	 *            size of vertical axis
	 */
	public Ellipse(int x, int y, int hAxis, int vAxis) {
		super(x, y, hAxis, vAxis);

		if (hAxis < 1 || vAxis < 1) {
			throw new IllegalArgumentException("Radius has to be greater than 0");
		}
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (Math.pow(super.x - x, 2) / Math.pow(super.hAxis, 2)
				+ Math.pow(super.y - y, 2) / Math.pow(super.vAxis, 2) > 1) {
			return false;
		}

		return true;
	}

}
