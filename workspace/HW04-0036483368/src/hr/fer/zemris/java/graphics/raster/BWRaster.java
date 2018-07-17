package hr.fer.zemris.java.graphics.raster;

/**
 * Interface BWRaster represents Black-and-White raster. It's an abstraction for
 * all raster devices of fixed width and height for which each pixel can be
 * painted with only two colors: black (when pixel is turned off) and white
 * (when pixel is turned on).
 * 
 * @author Marin Jurjevic
 *
 */
public interface BWRaster {

	/**
	 * @return raster's width
	 */
	public int getWidth();

	/**
	 * @return raster's height
	 */
	public int getHeight();

	/**
	 * Turns off all raster pixels.
	 */
	public void clear();

	/**
	 * Turns on raster pixel if flipping mode is disabled. If flipping mode is
	 * enabled it reverts pixel's visibility. (If pixel was on, it turns it off
	 * and vice-versa)
	 * 
	 * @param x
	 *            pixel width
	 * @param y
	 *            pixel height
	 */
	public void turnOn(int x, int y);

	/**
	 * Turns pixel off.
	 * 
	 * @param x
	 *            pixel width
	 * @param y
	 *            pixel height
	 */
	public void turnOff(int x, int y);

	/**
	 * Enables flip mode. When flip mode is enabled, pixels state
	 * is inversed.
	 * @see BWRaster#turnOn(int, int)
	 */
	public void enableFlipMode();

	/**
	 * Disables flip mode.
	 * @see BWRaster#enableFlipMode()
	 * @see BWRaster#turnOn(int, int)
	 */
	public void disableFlipMode();

	/**
	 * Checks is specified pixel turned on.
	 * 
	 * @param x
	 *            pixel width
	 * @param y
	 *            pixel height
	 * @return true if pixel is turned on, false otherwise
	 */
	public boolean isTurnedOn(int x, int y);
}
