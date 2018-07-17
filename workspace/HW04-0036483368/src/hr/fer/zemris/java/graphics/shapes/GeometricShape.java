package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * GeometricShape class represents all geometric shapes in geometry.
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class GeometricShape {

	/**
	 * Draws this geometric shape on given raster.
	 * 
	 * @param raster raster object where element will be drawn
	 */
	public void draw(BWRaster raster) {
		int rWidth = raster.getWidth();
		for (int i = 0, rHeight = raster.getHeight(); i < rHeight; i++) {
			for (int j = 0; j < rWidth; j++) {
				if (containsPoint(j, i) == true) {
					raster.turnOn(j, i);
				}
			}
		}
	}

	/**
	 * Checks if given point is contained inside certain geometric shape.
	 * 
	 * @param x
	 *            reference x coordinate of the specific shape
	 * @param y
	 *            reference y coordinate of the specific shape
	 * @return true if point is inside the shape, false otherwise
	 */
	public abstract boolean containsPoint(int x, int y);
}
