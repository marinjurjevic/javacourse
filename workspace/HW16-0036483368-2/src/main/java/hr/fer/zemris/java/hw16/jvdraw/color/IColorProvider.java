package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Interface for color providing. All class implementing this IColorProvider must offer
 * method for providing their currently used color.
 * @author Marin Jurjevic
 *
 */
public interface IColorProvider {

	/**
	 * Returns currently used color.
	 * @return currently used color.
	 */
	public Color getCurrentColor();
}
