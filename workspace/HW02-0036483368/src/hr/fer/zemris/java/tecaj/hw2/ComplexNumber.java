package hr.fer.zemris.java.tecaj.hw2;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ComplexNumber class represents an unmodifiable complex number. It has
 * implemented various methods to work with complex numbers.
 */
public class ComplexNumber {

	/** Real part of complex number */
	private final double real;

	/** Real part of complex number */
	private final double imag;

	/** Real part of complex number */
	private final double magnitude;

	/** Real part of complex number */
	private final double angle;

	/**
	 * Public constructor for ComplexNumber
	 * 
	 * @param real
	 *            value of real part
	 * @param imag
	 *            value of imaginary part
	 */
	public ComplexNumber(double real, double imag) {
		this.real = real;
		this.imag = imag;

		magnitude = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
		
		// angle must € [0,2PI]
		double test_angle = Math.atan2(imag, real);
		angle = test_angle < 0 ? test_angle += Math.PI * 2 : test_angle;
		
	}

	// public static factory methods

	/**
	 * Creates a new instance of ComplexNumber constructed using only real part.
	 * 
	 * @param real
	 *            real part of complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a new instance of ComplexNumber constructed using only imaginary
	 * part.
	 * 
	 * @param imaginary
	 *            imaginary part of complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a new instance of ComplexNumber constructed using magnitude and
	 * angle values of complex number.
	 * 
	 * @param magnitude
	 *            magnitude of complex number
	 * @param angle
	 *            angle of a complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromMagnitutdeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Parses given string and converts it to an ComplexNumber object.
	 * @param number string representation of complex number
	 * @return instance of ComplexNumber class
	 */
	public static ComplexNumber parse(String number) {
		String re,im;
		Pattern pattern = Pattern.compile("([+-]?\\d*(\\.\\d+)?)([+-]?\\d*(\\.\\d+)?)i");
		Matcher matcher = pattern.matcher(number);
		if (matcher.matches()) {
		  re = matcher.group(1);
		  im = matcher.group(3);
		}else{
			throw new IllegalArgumentException("Parsing error!");
		}
		
	    return new ComplexNumber(Double.parseDouble(re),Double.parseDouble(im));
	}

	// getters

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imag;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public double getAngle() {
		return angle;
	}

	// calculation methods
	
	/**
	 * Adds this complex number with some other complex number
	 * @param c complex number we add to this number 
	 * @return new complex number as result of addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imag + c.getImaginary());
	}
	
	/**
	 * Subtracts this complex number with some other complex number
	 * @param c complex number we subtract from this number
	 * @return new complex number as result of subtraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imag - c.getImaginary());
	}
	
	/**
	 * Multiplies this complex number with some other complex number
	 * @param c complex number we multiply this number with
	 * @return new complex number as result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c){
		return new ComplexNumber(real*c.getReal()-imag*c.getImaginary(),
							real*c.getImaginary()+imag*c.getReal());
	}
	
	/**
	 * Divides this complex number by some other complex number
	 * @param c complex number we divide this number with
	 * @return new complex number as result of division
	 */
	public ComplexNumber div(ComplexNumber c){
		double denominator = Math.pow(c.getReal(), 2)+Math.pow(c.getImaginary(), 2);
		return new ComplexNumber(
				(real*c.getReal()+imag*c.getImaginary())/denominator,
				(-real*c.getImaginary()+imag*c.getReal())/denominator
				);
	}
	
	/**
	 * Raises this complex number to specified potention.
	 * @param n potention we raise this complex number to
	 * @return new complex number as result of potentiation
	 */
	public ComplexNumber power(int n){
		if(n<1){
			throw new IllegalArgumentException("Exponent less than 1");
		}
		
		if(n == 1){
			return new ComplexNumber(1,0);
		}
		
		ComplexNumber c = this;
		while(n>1){
			c = c.mul(this);
			n--;
		}
		
		return c;
	}
	
	/**
	 * Calculates n roots of this complex number.
	 * @param n root to calculate
	 * @return array of ComplexNumber consisting all n roots
	 */
	public ComplexNumber[] root(int n){
		ComplexNumber[] roots = new ComplexNumber[n];
		
		for(int i = 0;i<n;i++){
			double arg = (this.angle+2*i*Math.PI) / n;
			roots[i] = new ComplexNumber(Math.pow(this.magnitude, 1.0/n)*Math.cos(arg),
					this.magnitude*Math.sin(arg)
						);
		}
		
		return roots;
	}

	@Override
	public String toString() {
		String s;
		DecimalFormat df = new DecimalFormat("#.##");
		
		if(real == 0 && imag == 0){
			return "0";
		}
		if(real == 0 && imag != 0){
			return df.format(imag)+"i";
		}
		if(imag == 0){
				return df.format(real);
		}

		// real and imag != 0	
		
		if(imag > 0){
			s = df.format(real) + " + " + df.format(imag) + "i";
		}
		else{
			s = df.format(real) + " - " + df.format(Math.abs(imag)) + "i";
		}
			
		
		return s;
	}	
	
}
