package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;

public class PiPokusaj1 {

	public static void main(String[] args) {
		final int NUMBER_OF_SAMPLES = 500_000_000;
		long startTime = System.currentTimeMillis();
		
		double pi = izracunaj(NUMBER_OF_SAMPLES);

		System.out.println("Pi = " + pi);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (stopTime-startTime));
	}

	private static double izracunaj(int numberOfSamples) {
		class Posao implements Runnable {
			int samples;
			int inside;

			public Posao(int samples) {
				super();
				this.samples = samples;
			}

			@Override
			public void run() {
				inside = PiUtil.testNumberOfPointsInCircle(samples, new Random());
			}

		}

		Posao p1 = new Posao(numberOfSamples / 2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples / 2);

		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);

		t1.start();
		t2.start();

		while (true) {
			try {t1.join(); break;}catch(InterruptedException e){}
		}
		while (true) {
			try {t2.join(); break;}catch(InterruptedException e){}
		}

		return 4.0 * (p1.inside + p2.inside) / numberOfSamples;
	}
}
