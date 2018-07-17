package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * SimpleRasterView is class for representing raster content on standard output.
 * 
 * @author Marin Jurjevic
 *
 */
public class SimpleRasterView implements RasterView {

	private final char PIXEL_ON;

	private final char PIXEL_OFF;

	/**
	 * Prints out raster content on standard output.
	 * 
	 * @param pIXEL_ON
	 *            character representing pixel turned on
	 * @param pIXEL_OFF
	 *            character representing pixel turned off
	 */
	public SimpleRasterView(char pIXEL_ON, char pIXEL_OFF) {
		super();
		PIXEL_ON = pIXEL_ON;
		PIXEL_OFF = pIXEL_OFF;
	}

	/**
	 * Prints out raster content on standard output with default characters for
	 * visualising pixels. When pixel is turned on, '*' is printed, if it's off
	 * '_' is printed.
	 */
	public SimpleRasterView() {
		this('*', '.');
	}

	@Override
	public Object produce(BWRaster raster) {
		int width = raster.getWidth();
		for (int i = 0, height = raster.getHeight(); i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (raster.isTurnedOn(j, i)) {
					System.out.print(PIXEL_ON);
				} else {
					System.out.print(PIXEL_OFF);
				}
			}
			// newline
			System.out.println();
		}
		return null;
	}

}
