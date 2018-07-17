package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Abstract class for modelling various geometrical objects.
 * @author Marin Jurjevic
 *
 */
public abstract class GeometricalObject {

	/**
	 * object name
	 */
	protected String name;

	/**
	 * object starting point
	 */
	protected Point startPoint;
	
	/**
	 * object end point
	 */
	protected Point endPoint;

	/**
	 * object primary color
	 */
	protected Color color;

	/**
	 * 
	 * Creates new GeometricalObject with appropriate parameters as follows
	 * @param name object name
	 * @param startPoint object start point (user first click)
	 * @param endPoint object end point (user second click)
	 * @param color primary color used for rendering object's shape
	 */
	public GeometricalObject(String name, Point startPoint, Point endPoint, Color color) {
		super();
		this.name = name;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}

	/**
	 * Draws object using given graphic object on parent component.
	 * @param g graphics used to draw this object
	 * @param box bounding box for exporting
	 */
	public abstract void draw(Graphics g, Rectangle box);
	
	/**
	 * Updates information about this geometrical object like coordinates and size.
	 */
	public abstract void update();
	
	/**
	 * Gets information about this geometrical object like coordinates and size etc. 
	 * Content can depending which object is implemented.
	 * @return text information about object
	 */
	public abstract String getInfo();
	
	/**
	 * Returns minimal bounding box determined on object size.
	 * @return bounding box
	 */
	public abstract Rectangle getBoundingBox();
	
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Returns starting point of this object.
	 * @return object starting point
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Sets new starting point of this object.
	 * @param startPoint object starting point
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * Returns ending point of this object.
	 * @return object ending point
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * Sets new ending point of this object.
	 * @param endPoint ending point
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Returns current primary color used for this object.
	 * @return current color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets new primary color used for this object.
	 * @param color new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
