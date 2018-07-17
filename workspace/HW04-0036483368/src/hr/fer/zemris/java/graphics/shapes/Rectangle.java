package hr.fer.zemris.java.graphics.shapes;

/**
 * Rectangle is a quadrilateral with four right angles. Rectangle has width and
 * height specified in constructor with (x,y) coordinates of upper left corner.
 * 
 * @author Marin Jurjevic
 *
 */
public class Rectangle extends AbstractRectangle {

	/**
	 * @see AbstractRectangle#AbstractRectangle(int, int, int, int)
	 */
	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public int getWidth() {
		return super.getWidth();
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
	}

	@Override
	public int getHeight() {
		return super.getHeight();
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
	}

}
