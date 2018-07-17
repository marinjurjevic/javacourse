package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * 
 * @author Marin Jurjevic
 *
 */
public class StringRasterView implements RasterView {

	private final char PIXEL_ON;

	private final char PIXEL_OFF;

	/**
	 * Creates new StringRasterView with specified characters for visualization.
	 * 
	 * @param pIXEL_ON
	 *            character representing pixel turned on
	 * @param pIXEL_OFF
	 *            character representing pixel turned off
	 */
	public StringRasterView(char pIXEL_ON, char pIXEL_OFF) {
		super();
		PIXEL_ON = pIXEL_ON;
		PIXEL_OFF = pIXEL_OFF;
	}

	/**
	 * Creates new StringRasterView with default set of characters for pixel
	 * visualisation. When pixel is turned on, '*' is printed, if it's off '_'
	 * is printed.
	 */
	public StringRasterView() {
		this('*', '.');
	}

	@Override
	public Object produce(BWRaster raster) {
		int rWidth = raster.getWidth();
		int rHeight = raster.getHeight();

		StringBuilder sb = new StringBuilder(rWidth * rHeight + rHeight);

		for (int i = 0; i < rHeight; i++) {
			for (int j = 0; j < rWidth; j++) {
				if (raster.isTurnedOn(j, i)) {
					sb.append(PIXEL_ON);
				} else {
					sb.append(PIXEL_OFF);
				}
			}
			sb.append('\n');
		}

		return sb.toString();
	}

}
