package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * SimpleDrawingModel is implementation of {@link DrawingModel} that works with {@link GeometricalObject} objects.
 * It is convient for storing objects that will be drawn on screen.
 * @author Marin Jurjevic
 *
 */
public class SimpleDrawingModel implements DrawingModel {

	/**
	 * List of currently stored geometrical objects.
	 */
	private List<GeometricalObject> objects;
	
	/**
	 * List of currently registered listeners.
	 */
	private List<DrawingModelListener> listeners;
	
	/**
	 * Creates new empty SimpleDrawModel ready to be use.
	 */
	public SimpleDrawingModel() {
		objects = new ArrayList<>();
		listeners = new LinkedList<>();
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		checkValidIndex(index);
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		if(object == null){
			throw new IllegalArgumentException("Model does not support null objects.");
		}
		
		objects.add(object);
		
		for(DrawingModelListener l : listeners){
			int index = objects.size();
			l.objectsAdded(this, index, index);
		}
	}
	
	

	@Override
	public void remove(int index) {
		checkValidIndex(index);
		
		System.out.println("Izbrisao sam: " + objects.get(index).toString());
		objects.remove(index);
		
		for(DrawingModelListener l : listeners){
			l.objectsRemoved(this, index, index);
		}
	}

	@Override
	public void update(int index) {
		checkValidIndex(index);
		objects.get(index).update();
		for(DrawingModelListener l : listeners){
			l.objectsChanged(this, index, index);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	/**
	 * Checks valid index for object retrieval.
	 * @param index index to be checked
	 */
	private void checkValidIndex(int index){
		if(index<0 || index>= objects.size()){
			throw new IllegalArgumentException("Invalid index. Only " + objects.size() + " are stored in model.");
		}
	}
}
