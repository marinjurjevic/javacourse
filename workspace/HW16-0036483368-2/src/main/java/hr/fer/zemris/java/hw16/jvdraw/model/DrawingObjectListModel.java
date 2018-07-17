package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * DrawingObjectListModel represents list object for listing out all current objects in canvas area.
 * @author Marin Jurjevic
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener{

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * list model
	 */
	private DrawingModel model;
	
	/**
	 * Creates new DrawingObjectListModel for listing all elements in given model.
	 * @param model model whose elements will be listed
	 */
	public DrawingObjectListModel(DrawingModel model) {
		super();
		this.model = model;
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

	
}
