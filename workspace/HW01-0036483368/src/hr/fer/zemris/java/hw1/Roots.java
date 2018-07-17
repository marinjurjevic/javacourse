package hr.fer.zemris.java.hw1;

import java.text.DecimalFormat;

/**
 * Calculates n roots of complex number.
 * Program takes parameters from command line.
 * First parameter is real part and second parameter is imaginary
 * part of complex number. Third and last parameter is required root
 * to calculate.
 */
public class Roots {

	/**
	 * Entry point of this progam and only method.
	 * To perform given tasks methods from Math package are used.
	 * For formating output DecimalFormat class has been used.
	 * @param args command line arguments ( program parameters)
	 */
	public static void main(String[] args) {
		Double realPart = Double.parseDouble(args[0]);
		Double imagPart = Double.parseDouble(args[1]);
		int root = Integer.parseInt(args[2]);
		
		double angle = Math.atan(imagPart / realPart);
		double value = Math.sqrt(Math.pow(realPart, 2) + Math.pow(imagPart, 2));
		value = Math.pow(value, 1.0/root);
		
		for(int i = 0; i < root; i++){
			double arg = (angle+2*i*Math.PI) / root;
			DecimalFormat df = new DecimalFormat("#.##");
			double re = value*Math.cos(arg);
			double im = value*Math.sin(arg);
			
			String fRe = df.format(re);
			
			if(im > 0)
				System.out.println( (i+1)+") " + fRe + " + " + df.format(im) + "i");
			else
				System.out.println( (i+1)+") " + fRe + " - " + df.format(Math.abs(im)) + "i" );
		}
	}
	
	
}
