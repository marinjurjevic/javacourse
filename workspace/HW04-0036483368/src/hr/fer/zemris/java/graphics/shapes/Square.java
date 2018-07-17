package hr.fer.zemris.java.graphics.shapes;

/**
 * Square is a rectangle with four equal sides. It offers has public getters and
 * setters for retrieving and setting a side.
 * 
 * @author Marin Jurjevic
 *
 */
public class Square extends AbstractRectangle {

	/**
	 * Creates new square with specified position and proper size.
	 * 
	 * @param x
	 *            upper-left corner x-coordinate
	 * @param y
	 *            upper-left corner y-coordinate
	 * @param size
	 *            value of square side
	 */
	public Square(int x, int y, int size) {
		super(x, y, size, size);
	}

	/**
	 * 
	 * @return value of square's size
	 */
	public int getSize() {
		return super.getHeight();
		// or super.getHeight()
	}

	/**
	 * Sets squre size.
	 * 
	 * @param size
	 *            of square size
	 */
	public void setSize(int size) {
		super.setHeight(size);
		super.setWidth(size);
	}
}
