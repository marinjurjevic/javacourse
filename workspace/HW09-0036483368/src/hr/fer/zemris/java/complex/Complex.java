package hr.fer.zemris.java.complex;

import java.util.LinkedList;
import java.util.List;

/**
 * <tt>Complex</tt> represents model of a complex number. It's instance are
 * immutable objects and such can not be modified after creation so they are
 * safe to use in multithread environment.</br>
 * Default constructor creates new complex number with value equivalent to zero,
 * has same effect as calling <tt>new Complex(0,0)</tt>. Main constructor
 * recieves two arguments, value of real and imaginary part of given complex
 * number. </br>
 * It offers basic arithmetic operations, power operation and root method which
 * provides adequate number of roots. Also there are 5 basic static fields for
 * faster retrieval of often used numbers (0,1,-1,i and -i).
 * 
 * @author Marin Jurjevic
 *
 */
public class Complex {

	/**
	 * value of real part of complex number
	 */
	private final double real;

	/**
	 * number of imaginary part of complex number
	 */
	private final double imaginary;

	/**
	 * module of this complex numbers
	 */
	private final double module;

	/**
	 * real number from interval [-pi,pi] (radians)
	 */
	private final double angle;

	/**
	 * Creates new complex number equivalent to number zero. This call is equal
	 * to calling <tt>new Complex(0,0)<tt>
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Creates new complex number equivalent to number one. This call is equal
	 * to calling <tt>new Complex(1,0)<tt>
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Creates new complex number equivalent to number minus one. This call is
	 * equal to calling <tt>new Complex(-1,0)<tt>
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Creates new complex number equivalent to one imaginary unit. This call is
	 * equal to calling <tt>new Complex(0,1)<tt>
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Creates new complex number equivalent to minus imaginary unit. This call
	 * is equal to calling <tt>new Complex(0,-1)<tt>
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor for creating complex number 0. It's effectively
	 * creating a complex number whose real and imaginary part are equal to 0.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates new complex number based on given value for real and imaginary
	 * part.
	 * 
	 * @param re
	 *            value of real part
	 * @param im
	 *            value of imaginary part
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;

		module = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		angle = Math.atan2(imaginary, real);
	}

	/**
	 * Returns module of this complex number.
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return module;
	}

	/**
	 * Returns value of real part of this complex number.
	 * 
	 * @return value of real part
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns value of imaginary part of this complex number.
	 * 
	 * @return value of imaginary part
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns new complex number that is result of this complex number
	 * multiplied by given complex number c.
	 * 
	 * @param c
	 *            given complex number to be multiplied with this complex number
	 * @return new instance of Complex which is result of multiplication
	 */
	public Complex multiply(Complex c) {
		double newReal = this.real * c.real - this.imaginary * c.imaginary;
		double newImag = this.real * c.imaginary + this.imaginary * c.real;
		return new Complex(newReal, newImag);
	}

	/**
	 * Returns new complex number that is result of this complex number divided
	 * by given complex number c.
	 * 
	 * @param c
	 *            given complex number to divide this complex number
	 * @return new instance of Complex which is result of division
	 */
	public Complex divide(Complex c) {
		double den = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2); // denominator
		double newReal = (this.real * c.real + this.imaginary * c.imaginary) / den;
		double newImag = (-this.real * c.imaginary + this.imaginary * c.real) / den;
		return new Complex(newReal, newImag);
	}

	/**
	 * Returns new complex number that is result of adding this complex number
	 * to given complex number
	 * 
	 * @param c
	 *            given complex number to be added to this complex number
	 * @return new instance of Complex which is result of adding
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Returns new complex number that is result of subtracting given complex
	 * number from this complex number.
	 * 
	 * @param c
	 *            given complex number to be subtracted to this complex number
	 * @return new instance of Complex which is result of subtraction
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Returns new complex number that is negation of this complex number.
	 * 
	 * @return new instance of Complex which is result of negating this number
	 */
	public Complex negate() {
		return new Complex(-this.real, -this.imaginary);
	}

	/**
	 * Calculates power of this complex number.
	 * 
	 * @param n
	 *            power of this complex number
	 * @return new instance of Complex which is calculated as power of this
	 *         complex number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must be greater than or equal to 0!");
		}

		if (n == 0)
			return new Complex(1, 0);
		if (n == 1)
			return this;

		double newReal = Math.pow(module, n) * Math.cos(n * angle);
		double newImag = Math.pow(module, n) * Math.sin(n * angle);

		return new Complex(newReal, newImag);
	}

	/**
	 * Calculates n-th root of this complex numbers and returns roots in a list.
	 * 
	 * @param n
	 *            n-th root to be calculated
	 * @return list of roots
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Power must be greater than 0!");
		}

		// preparing structure
		List<Complex> roots = new LinkedList<>();
		double newReal;
		double newImag;

		for (int i = 0; i < n; i++) {
			newReal = Math.pow(module, 1.0 / n) * Math.cos((angle + 2 * i * Math.PI) / n);
			newImag = Math.pow(module, 1.0 / n) * Math.sin((angle + 2 * i * Math.PI) / n);
			roots.add(new Complex(newReal, newImag));
		}

		return roots;
	}

	/**
	 * Returns angle of this complex number normed to [-pi,pi]
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		String string = "(";

		boolean sign = true;
		if (real != 0) {
			string += real;
		} else {
			sign = false;
		}

		if (imaginary < 0) {
			string += "-" + Math.abs(imaginary) + "i";
		} else if (imaginary == 0) {
			if (!sign) {
				return "0";
			}
		} else {
			if (sign) {
				string += " + ";
			}
			string += Math.abs(imaginary) + "i";
		}

		return string + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

}