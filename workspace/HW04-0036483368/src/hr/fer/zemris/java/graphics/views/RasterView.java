package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * RasterView offer one method for visualization of given raster.
 * 
 * @author Marin Jurjevic
 *
 */
public interface RasterView {

	/**
	 * Produces current state of raster in memory.
	 * 
	 * @param raster
	 *            raster to be produced
	 * @return raster content
	 */
	Object produce(BWRaster raster);
}
