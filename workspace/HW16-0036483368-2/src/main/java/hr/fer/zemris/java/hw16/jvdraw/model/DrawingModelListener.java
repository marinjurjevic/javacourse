package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Listener for tracking changes in drawing model such as adding,removing and modifying objects.
 * All methods have same parameters, <tt>source</tt>, <tt>index0</tt> and <tt>index1</tt>. <tt>index0</tt>
 * and <tt>index1</tt> represent interval in which changes occured (inclusive on both sides).
 * @author Marin Jurjevic
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Called when an object or more of them has been added.
	 * @param source source where change occured
	 * @param index0 interval start
	 * @param index1 interval end
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Called when an object or more of them has been removed.
	 * @param source source where change occured
	 * @param index0 interval start
	 * @param index1 interval end
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Called when an object or more of them has been modified.
	 * @param source source where change occured
	 * @param index0 interval start
	 * @param index1 interval end
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
