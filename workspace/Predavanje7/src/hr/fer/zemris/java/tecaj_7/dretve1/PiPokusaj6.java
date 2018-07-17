package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PiPokusaj6 {

	public static void main(String[] args) {
		final int NUMBER_OF_SAMPLES = 100_000_000;
		long startTime = System.currentTimeMillis();
		
		ForkJoinPool pool = new ForkJoinPool(2);
		double pi = izracunaj(NUMBER_OF_SAMPLES, pool);

		System.out.println("Pi = " + pi);

		pool.shutdown();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (stopTime-startTime));
	}

	private static double izracunaj(int numberOfSamples, ForkJoinPool pool) {
		class Posao extends RecursiveAction{
			int samples;
			int inside;
			
			public Posao(int samples) {
				super();
				this.samples = samples;
			}

			@Override
			protected void compute() {
				//System.out.println(Thread.currentThread().getName()+", samples = " + samples);
				if(samples<1_000_000){
					computeDirect();
					return;
				}
				Posao p1 = new Posao(samples/2);
				Posao p2 = new Posao(samples-samples/2);
				
				invokeAll(p1,p2);
				
				inside = p1.inside+p2.inside;
			}

			private void computeDirect() {
				inside = PiUtil.testNumberOfPointsInCircle(samples, new Random());
			}
			
			
			
		}
		
		Posao p1 = new Posao(numberOfSamples);
		pool.invoke(p1);

		return 4.0 * (p1.inside) / numberOfSamples;
	}
}
