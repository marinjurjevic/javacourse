package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;

import hr.fer.zemris.java.tecaj_7.dretve1.ThreadPool.JobInfo;

public class PiPokusaj3 {

	public static void main(String[] args) {
		final int NUMBER_OF_SAMPLES = 100_000_000;

		ThreadPool pool = new ThreadPool(2);
		double pi = izracunaj(NUMBER_OF_SAMPLES, pool);

		System.out.println("Pi = " + pi);
		
		pool.shutdown();
	}

	private static double izracunaj(int numberOfSamples, ThreadPool pool) {
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

		JobInfo info1 = pool.addJob(p1);
		JobInfo info2 = pool.addJob(p2);
		
		info1.waitUntilNotExecuted();
		info2.waitUntilNotExecuted();
		
		return 4.0 * (p1.inside + p2.inside) / numberOfSamples;
	}
}
