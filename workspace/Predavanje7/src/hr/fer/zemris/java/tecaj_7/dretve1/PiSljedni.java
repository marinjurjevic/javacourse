package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;

public class PiSljedni {

	public static void main(String[] args) {
		final int NUMBER_OF_SAMPLES = 500_000_000;
		long startTime = System.currentTimeMillis();
		
		double pi = izracunajSlijedno(NUMBER_OF_SAMPLES);
		
		System.out.println("Pi = " + pi);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (stopTime-startTime));
		
	}
	
	private static double izracunajSlijedno(int numberOfSamples){
		int inside = PiUtil.testNumberOfPointsInCircle(numberOfSamples, new Random());
		
		return 4.0*inside/numberOfSamples;
	}

}
