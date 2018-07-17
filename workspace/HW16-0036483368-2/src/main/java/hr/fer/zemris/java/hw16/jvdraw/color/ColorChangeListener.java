package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Implementation of observers used for tracking changes in IColorProvider's current color.
 * @author Marin Jurjevic
 *
 */
public interface ColorChangeListener {

	/**
	 * 
	 * @param source source color provider
	 * @param oldColor old color 
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
