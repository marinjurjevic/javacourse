package hr.fer.zemris.java.complex;

/**
 * <tt>ComplexRootedPolynomial</tt> represents a model of root-based complex
 * polynomial.
 * 
 * @author Marin Jurjevic
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * array of roots of this polynom
	 */
	private final Complex[] roots;

	/**
	 * Creates new polynomial using roots as arguments
	 * 
	 * @param roots
	 *            roots of polynom to be created
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("No roots given.");
		}
		if (roots.length < 2) {
			throw new IllegalArgumentException("You must enter at least 2 roots.");
		}

		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z
	 *            given point z
	 * @return value of polynom computed in given value
	 */
	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException("Given point can not be null!");
		}
		Complex value = Complex.ONE;
		for (Complex c : roots) {
			value = value.multiply(z.sub(c));
		}
		return value;
	}

	/**
	 * Converts this model to <tt>ComplexPolynomial</tt> model.
	 * 
	 * @return coefficient-based complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynom = new ComplexPolynomial(roots[0].negate(), Complex.ONE);

		for (int i = 1; i < roots.length; i++) {
			polynom = polynom.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}

		return polynom;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Complex c : roots) {
			sb.append("(z-").append(c).append(")");
		}

		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold. If there is no such root, -1 is returned.
	 * 
	 * @param z
	 *            given complex number
	 * @param treshold
	 *            given threshold
	 * @return closest root if such exists, -1 otherwise
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int min = -1;
		double minValue = Double.MAX_VALUE;
		for (int i = 0; i < roots.length; i++) {
			double value = roots[i].sub(z).module();
			if (value < treshold && value < minValue) {
				min = i;
				minValue = value;
			}
		}

		return min + 1;
	}
}
