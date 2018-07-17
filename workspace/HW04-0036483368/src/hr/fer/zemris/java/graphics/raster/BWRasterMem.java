package hr.fer.zemris.java.graphics.raster;

/**
 * BWRasterMem is an implementation of a Black-and-White raster. It keeps all of
 * its data memory, thus enabling manipulation of its pixels. The coordinate
 * system for raster has (0,0) at the top-left corner of raster; positive x-axis
 * is to the right and positive y-axis is toward the bottom.
 * 
 * @author Marin Jurjevic
 *
 */
public class BWRasterMem implements BWRaster {

	/**
	 * Width of a raster. Number of pixels set horizontally.
	 */
	private final int width;

	/**
	 * Height of a raster. Number of pixels set vertically.
	 */
	private final int height;

	/**
	 * Internal array of characters representing raster.
	 */
	private boolean[][] raster;

	/**
	 * Flag for keeping track is flipMode enabled or disabled.
	 */
	private boolean flipMode;

	public BWRasterMem(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalAccessError("Raster's width and height must be natural numbers.");
		}

		this.raster = new boolean[height][width];
		this.width = width;
		this.height = height;
		this.flipMode = false;

	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				turnOff(j, i);
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		checkDimensions(x, y);
		if (flipMode == true) {
			raster[y][x] = raster[y][x] ? false : true;
		} else {
			raster[y][x] = true;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		checkDimensions(x, y);
		raster[y][x] = false;
	}

	@Override
	public void enableFlipMode() {
		flipMode = true;
	}

	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		checkDimensions(x, y);
		return raster[y][x];
	}

	/**
	 * Checks if method arguments are within raster dimensions.
	 * 
	 * @param x
	 *            pixel width
	 * @param y
	 *            pixel height
	 */
	private void checkDimensions(int x, int y) {
		if (x > width - 1 || y > height - 1 || x < 0 || y < 0) {
			throw new IllegalArgumentException("Wrong pixel coordinates!");
		}
	}

}
