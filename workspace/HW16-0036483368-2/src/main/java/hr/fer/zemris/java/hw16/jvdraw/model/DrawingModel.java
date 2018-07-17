package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * DrawingModel represents a model for drawing various geometrical objects such as line and circle on JCanvas.
 * It encapsulates all drawn models and handles all actions performed on them.
 * @author Marin Jurjevic
 *
 */
public interface DrawingModel {

	/**
	 * Returns number of geometrical objects.
	 * @return number of geometrical objects
	 */
	public int getSize();
	
	/**
	 * Returns geometrical object stored under given index. If there is no such element, exception might occur.
	 * @param index index where geometrical object is stored
	 * @return geometrical object
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Adds given geometrical object to drawing model.
	 * @param object geometrical object
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes given geometrical object from drawing model.
	 * @param index index of object to remove
	 */
	public void remove(int index);
	
	/**
	 * Updates given geometrical object in the drawing model.
	 * @param index index of object to update
	 */
	public void update(int index);
	
	/**
	 * Registers instance of DrawingModelListener onto DrawingModel.
	 * @param l instance of DrawingModelListener
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Deregisters instance of DrawingModelListener onto DrawingModel.
	 * @param l instance of DrawingModelListener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
