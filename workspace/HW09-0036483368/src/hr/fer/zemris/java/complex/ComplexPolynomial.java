package hr.fer.zemris.java.complex;

/**
 * <tt>ComplexPolynomial</tt> represents a model of coefficient-based complex
 * polynomial.
 * 
 * @author Marin Jurjevic
 *
 */
public class ComplexPolynomial {

	/**
	 * array of coefficients corresponding to power of each member
	 */
	private final Complex[] factors;

	/**
	 * Creates new <tt>ComplexPolynomial</tt> with adequate cofficients.
	 * Cofficients are given starting from zero power up to polymom order. If
	 * coefficient is not present, you must give 0 or further dependent
	 * operations will fail. For example, if you want to create 3*z^3 - 2*z^2 +
	 * 1, you would call constructor as following:
	 * 
	 * <pre>
	 * new ComplexPolynomial(3, 2, 0, 1);
	 * </pre>
	 * 
	 * @param factors
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Factors can not be null!");
		}

		this.factors = factors;
	}

	/**
	 * Returns order of this complex polynomial
	 * 
	 * @return order of this polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Computes new polynomial by multiplying this complex polynomial to given
	 * complex polynomial p
	 * 
	 * @param p
	 *            given complex polynomial p to be multiplied
	 * @return new computed complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];

		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
		}

		for (int i = 0, n = this.order(); i <= n; i++) {
			for (int j = 0, m = p.order(); j <= m; j++) {
				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes first derivative of this complex polynomial.
	 * 
	 * @return first derivative
	 */
	public ComplexPolynomial derive() {
		if (this.order() == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}

		Complex[] newFactors = new Complex[factors.length - 1];

		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z
	 *            given point z
	 * @return value of polynom computed in given value
	 */
	public Complex apply(Complex z) {
		Complex value = Complex.ZERO;

		for (int i = 0; i < factors.length; i++) {
			value = value.add(factors[i].multiply(z.power(i)));
		}

		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = factors.length - 1; i >= 0; i--) {
			if (i == 0) {
				sb.append(factors[i]);
			} else {
				if (factors[i].module() != 0) {
					sb.append(factors[i]).append("z^").append(i).append(" + ");
				}
			}

		}

		return sb.toString();
	}
}
