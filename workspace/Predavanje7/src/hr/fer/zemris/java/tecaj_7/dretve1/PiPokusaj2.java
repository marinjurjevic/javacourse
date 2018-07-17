package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PiPokusaj2 {

	public static void main(String[] args) {
		final int NUMBER_OF_SAMPLES = 100_000_000;
		long startTime = System.currentTimeMillis();
		
		ThreadPoolSimple pool = new ThreadPoolSimple(2);
		double pi = izracunaj(NUMBER_OF_SAMPLES, pool);

		System.out.println("Pi = " + pi);
		
		pool.shutdown();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (stopTime-startTime));
	}

	private static double izracunaj(int numberOfSamples, ThreadPoolSimple pool) {
		Semaphore sem = new Semaphore(0);
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
				sem.release();
			}

		}

		Posao p1 = new Posao(numberOfSamples / 2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples / 2);

		pool.addJob(p1);
		pool.addJob(p2);
		
		sem.acquireUninterruptibly(2);
		
		return 4.0 * (p1.inside + p2.inside) / numberOfSamples;
	}
}
