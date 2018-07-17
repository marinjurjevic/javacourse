package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Multithread program for rendering fractals derived using Newton-Raphson
 * iteration.
 * 
 * @author Marin Jurjevic
 *
 */
public class Newton {

	/**
	 * minimal distance between two iterations
	 */
	private static final double CONV_TRESHOLD = 0.001;

	/**
	 * minimal distance for determining index color
	 */
	private static final double ROOT_DISTANCE = 0.002;

	/**
	 * model of complex coefficient-based polynomial used for fractal
	 * calculation
	 */
	private static ComplexPolynomial polynomial;

	/**
	 * model of complex root-based polynomial used for fractal calculation
	 */
	private static ComplexRootedPolynomial rootedPolynomial;

	/**
	 * Entry point of this program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		int i = 1;
		String input;
		List<Complex> roots = new ArrayList<>();

		while (true) {
			System.out.print("Root " + i + "> ");
			input = sc.nextLine();

			if (input.equalsIgnoreCase("done")) {
				sc.close();
				break;
			}

			try {
				roots.add(parseInput(input));
				i++;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid input! Try again!");
				continue;
			}
		}

		Complex[] arrayRoots = new Complex[1];
		arrayRoots = roots.toArray(arrayRoots);
		rootedPolynomial = new ComplexRootedPolynomial(arrayRoots);
		polynomial = rootedPolynomial.toComplexPolynom();

		FractalViewer.show(new MyProducer());
	}

	/**
	 * Implementation of <tt>IFractalProducer</tt> that renders fractal on
	 * screen using instance of <tt>FixedThreadPool</tt> that contains daemon
	 * threads used for splitting the job between them.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	public static class MyProducer implements IFractalProducer {

		/**
		 * instance of <tt>FixedThreadPool</tt> used for multi-threding
		 */
		private ExecutorService pool;

		/**
		 * Creates new producer which is able to render fractal on screen.
		 */
		public MyProducer() {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(true);
					return thread;
				}
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Image of fractal will appear shortly. Thank you.");
			int m = 16 * 16;
			short[] data = new short[width * height];
			final int yRange = 8 * Runtime.getRuntime().availableProcessors();
			int yInterval = height / yRange;
			List<Future<Void>> results = new ArrayList<>();
			for (int i = 0; i < yRange; i++) {
				int yMin = i * yInterval;
				int yMax = (i + 1) * yInterval - 1;
				if (i == yRange - 1) {
					yMax = height - 1;
				}
				Job posao = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data);
				results.add(pool.submit(posao));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			// System.out.println("Racunanje gotovo. Idem obavijestiti
			// promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}

	/**
	 * Implementation of job that will be given to a corresponding thread.
	 * <tt>Job</tt> implements interface Callable, enabling result retrieval,
	 * which in this case is ignored (Void).
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	public static class Job implements Callable<Void> {
		/**
		 * minimal value of real component
		 */
		double reMin;

		/**
		 * maximal value of real component
		 */
		double reMax;

		/**
		 * minimal value of imaginary component
		 */
		double imMin;

		/**
		 * maximal value of imaginary component
		 */
		double imMax;

		/**
		 * raster width
		 */
		int width;

		/**
		 * height
		 */
		int height;

		/**
		 * lower bound of interval of screen height (inclusive)
		 */
		int yMin;

		/**
		 * higher bound of interval of screen height (inclusive)
		 */
		int yMax;

		/**
		 * maximal number of iterations
		 */
		int m;

		/**
		 * array of data, representing screen pixels
		 */
		short[] data;

		/**
		 * Creates new instance of Job, which represent one piece of screen to
		 * be rendered.
		 * 
		 * @param reMin
		 *            {@link Job#reMin}
		 * @param reMax
		 *            {@link Job#reMax}
		 * @param imMin
		 *            {@link Job#imMin}
		 * @param imMax
		 *            {@link Job#imMax}
		 * @param width
		 *            {@link Job#width}
		 * @param height
		 *            {@link Job#height}
		 * @param yMin
		 *            {@link Job#yMin}
		 * @param yMax
		 *            {@link Job#yMax}
		 * @param m
		 *            {@link Job#m}
		 * @param data
		 *            {@link Job#data}
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				int m, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
		}

		@Override
		public Void call() {
			calculateData(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data);

			return null;
		}
	}

	/**
	 * Calculates data to be rendered in given interval of y - coordinates.
	 * 
	 * @param reMin
	 *            minimal value on real axis
	 * @param reMax
	 *            maximal value on real axis
	 * @param imMin
	 *            minimal value on imaginary axis
	 * @param imMax
	 *            maximal value on imaginary axis
	 * @param width
	 *            width of screen on which fractal will be rendered
	 * @param height
	 *            height of screen on which fractal will be rendered
	 * @param maxIterations
	 *            maximal number of iterations
	 * @param ymin
	 *            lower bound of interval (inclusive)
	 * @param ymax
	 *            higher bound of interval (inclusive)
	 * @param data
	 *            array in which calculations will be stored (respecting
	 *            specified interval)
	 */
	private static void calculateData(double reMin, double reMax, double imMin, double imMax, int width, int height,
			int maxIterations, int ymin, int ymax, short[] data) {
		int offset = ymin * width;
		Complex zn;
		Complex zn1;
		for (int y = ymin; y <= ymax; y++) {
			for (int x = 0; x < width; x++) {
				double module = 0;
				int iters = 0;
				// mapping coordinate
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
				zn = new Complex(cre, cim);

				// starting calculation
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = polynomial.derive().apply(zn);
					Complex fraction = numerator.divide(denominator);
					zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;
					iters++;
				} while (iters < maxIterations && module > CONV_TRESHOLD);

				// retrieving value to be stored in data
				int index = rootedPolynomial.indexOfClosestRootFor(zn1, ROOT_DISTANCE);
				if (index == -1) {
					data[offset] = 0;
				} else {
					data[offset] = (short) index;
				}
				offset++;
			}
		}
	}

	/**
	 * Parses given input into a <tt>Complex</tt> number.
	 * 
	 * @param input
	 *            user input retrieved from standard input
	 * @return new Complex number extracted from input
	 */
	private static Complex parseInput(String input) {
		if (input.isEmpty()) {
			throw new IllegalArgumentException("Empty input!");
		}

		if (input.equals("i")) {
			return new Complex(0, 1);
		}

		double real = 0;
		double imaginary = 0;

		if (input.contains("i")) {
			String[] args = input.split("i");
			if (args.length == 2) {
				if (!args[0].isEmpty()) {
					args[0] = args[0].trim();
					args[1] = args[1].trim();
					if (args[0].charAt(args[0].length() - 1) == '-') {
						imaginary = -1 * Double.parseDouble(args[1]);
					} else {
						imaginary = Double.parseDouble(args[1]);
					}

					args[0] = args[0].substring(0, args[0].length() - 1).trim();
					if (!args[0].isEmpty()) {
						real = Double.parseDouble(args[0]);
					}
				} else {
					imaginary = Double.parseDouble(args[1]);
				}
			} else {
				input = input.trim();
				if (input.endsWith("i")) {
					imaginary = 1;
					input = input.substring(0, input.length() - 1);
					if (!input.isEmpty()) {
						if (input.endsWith("-")) {
							imaginary = -1;
						}
						input = input.substring(0, input.length() - 1);
						if (!input.isEmpty()) {
							real = Double.parseDouble(input);
						}
					}
				} else {
					imaginary = Double.parseDouble(args[0].trim());
				}
			}
		} else {
			real = Double.parseDouble(input.trim());
		}

		return new Complex(real, imaginary);
	}

}
