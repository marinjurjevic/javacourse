package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * Demo class for demonstrating calculations with complex numbers.
 */
public class ComplexDemo {

	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2,3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitutdeAndAngle(2, 1.57))
						.div(c2).power(3).root(2)[1];
		
		 /* nesto ovdje nije u redu s parse metodom, trazio sam po internetu,
		 ali nikako naci pravi regex, svaki ima manu. Ostavio sam ovaj 
		 trenutni, jer je koliko toliko najbolji. Sve ostale metode bi trebale
		 potpuno ispravno raditi */
		
		System.out.println(c3);
		
		
		
		
	}
	
}
