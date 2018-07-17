package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * Interface for providing geomtrical object creation. Geometrical object must be built from two points and can use two colors.
 * @author Marin Jurjevic
 *
 */
public interface ObjectCreator {

	/**
	 * Creates new geometrical object based on two points and two colors (usually foreground and background).
	 * @param startPoint object starting point
	 * @param endPoint object ending point
	 * @param fgColor object foreground color
	 * @param bgColor object backgorund color
	 * @return new instance of geometrical object
	 */
	public GeometricalObject create(Point startPoint, Point endPoint, Color fgColor, Color bgColor);
}
