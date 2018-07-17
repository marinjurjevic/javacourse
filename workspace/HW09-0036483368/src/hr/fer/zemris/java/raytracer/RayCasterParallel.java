package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Simple <b>multi-thread</b> program demonstrating usage of ray-casting
 * technique, implemented using Phong's lighting model.
 * 
 * @author Marin Jurjevic
 *
 */
public class RayCasterParallel {

	/**
	 * constant value used for starting ambient component
	 */
	private static final short AMBIENT_COMPONENT = 15;

	/**
	 * Entry point of main program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);

	}

	/**
	 * Returns new <tt>IRayTracerProducer</tt> which is able to compute object
	 * space and geometry information used for rendering data on screen.
	 * 
	 * @return new instance of IRayTracerProducer used for rendering data on the
	 *         screen
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				// norming the plane
				Point3D OG = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize().sub(OG.scalarMultiply(OG.scalarProduct(viewUp.normalize())))
						.normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				// Point3D zAxis = yAxis.vectorProduct(xAxis).normalize(); - not
				// used for these computations

				// upper-left screen corner
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();

				// measuring calculation time
				double start = System.currentTimeMillis();
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(eye, scene, xAxis, yAxis, screenCorner, width, height, horizontal, vertical, 0,
						height, red, green, blue));
				pool.shutdown();
				double end = System.currentTimeMillis();

				System.out.println("Izračuni gotovi. Izračunato za: " + (end - start) + "ms");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}

		};
	}

	/**
	 * Piece of computation to be executed by one thread. This job takes
	 * specific bounds that form interval in which this job will calculate data.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	@SuppressWarnings("serial")
	public static class Job extends RecursiveAction {

		/**
		 * eye coordinate
		 */
		private Point3D eye;

		/**
		 * current scene
		 */
		private Scene scene;

		/**
		 * normalized i vector of screen xAxis
		 */
		private Point3D xAxis;
		/**
		 * normalized j vector of screen yAxis
		 */
		private Point3D yAxis;

		/**
		 * coordinate of upper-left screen corner
		 */
		private Point3D screenCorner;

		/**
		 * screen width
		 */
		private int width;
		/**
		 * screen height
		 */
		private int height;

		/**
		 * horizontal view component
		 */
		private double horizontal;

		/**
		 * vertical view component
		 */
		private double vertical;

		/**
		 * minimum interval bound (inclusive)
		 */
		private int yMin;

		/**
		 * maximal interval bound (inclusive)
		 */
		private int yMax;

		/**
		 * red component part for each screen pixel
		 */
		private short[] red;
		/**
		 * green component part for each screen pixel
		 */
		private short[] green;

		/**
		 * blue component part for each screen pixel
		 */
		private short[] blue;

		/**
		 * treshold for stopping recursion
		 */
		static final int treshold = 16;

		/**
		 * Creates new <tt>Job</tt> that recursively divides starting job into
		 * many subjobs until treshold is reached.
		 * 
		 * @param eye
		 *            {@link Job#eye}
		 * @param scene
		 *            {@link Job#scene}
		 * @param xAxis
		 *            {@link Job#xAxis}
		 * @param yAxis
		 *            {@link Job#yAxis}
		 * @param screenCorner
		 *            {@link Job#screenCorner}
		 * @param width
		 *            {@link Job#width}
		 * @param height
		 *            {@link Job#height}
		 * @param horizontal
		 *            {@link Job#horizontal}
		 * @param vertical
		 *            {@link Job#vertical}
		 * @param yMin
		 *            {@link Job#yMin}
		 * @param yMax
		 *            {@link Job#yMax}
		 * @param red
		 *            {@link Job#red}
		 * @param green
		 *            {@link Job#green}
		 * @param blue
		 *            {@link Job#blue}
		 */
		public Job(Point3D eye, Scene scene, Point3D xAxis, Point3D yAxis, Point3D screenCorner, int width, int height,
				double horizontal, double vertical, int yMin, int yMax, short[] red, short[] green, short[] blue) {
			super();
			this.eye = eye;
			this.scene = scene;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.yMin = yMin;
			this.yMax = yMax;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= treshold) {
				directCompute();
				return;
			}

			invokeAll(
					new Job(eye, scene, xAxis, yAxis, screenCorner, width, height, horizontal, vertical, yMin,
							yMin + (yMax - yMin) / 2 + 1, red, green, blue),
					new Job(eye, scene, xAxis, yAxis, screenCorner, width, height, horizontal, vertical,
							yMin + (yMax - yMin) / 2 + 1, yMax, red, green, blue));
		}

		/**
		 * Starts direct computation for maximal interval smaller than treshold.
		 */
		public void directCompute() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * 1.0 / (width - 1) * horizontal))
							.sub(yAxis.scalarMultiply(y * 1.0 / (height - 1) * vertical));

					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					// applying color to pixel
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}
		}
	}

	// **********************************
	// **** HELP METHODS FOR 1 PIXEL*****
	// **********************************
	/**
	 * Traces intersection between this ray and closest object it intersects
	 * with if such exists. If no object intersects with given ray, rgb values
	 * will be set to 0.
	 * 
	 * @param scene
	 *            current scene
	 * @param ray
	 *            given ray starting from viewer's eye
	 * @param rgb
	 *            data of rgb values for pixel
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection closest = findClosestIntersection(scene, ray);

		if (closest == null) {
			for (int i = 0; i < 3; i++) {
				rgb[i] = 0;
			}
		} else {
			determineColor(scene, closest, rgb, ray.start);
		}
	}

	/**
	 * Determines color of pixel summing intensities from all lights that light
	 * given intersection.
	 * 
	 * @param scene
	 *            current scene
	 * @param ri
	 *            intersection of ray from viewer's eye and object
	 * @param rgb
	 *            data of rgb values to be computed depending on contribution of
	 *            all light sources
	 * @param eye
	 *            position of viewer's eye
	 */
	private static void determineColor(Scene scene, RayIntersection ri, short[] rgb, Point3D eye) {
		final double PRECISION = 1e-3;

		// ambient component
		for (int i = 0; i < 3; i++) {
			rgb[i] = AMBIENT_COMPONENT;
		}

		// we add to final result contribution from all lights, if light
		// actually lights the object
		for (LightSource ls : scene.getLights()) {
			Ray ray = Ray.fromPoints(ls.getPoint(), ri.getPoint());
			RayIntersection closest = findClosestIntersection(scene, ray);
			double dist = ls.getPoint().sub(ri.getPoint()).norm();

			// if closest exists, skip this light (it's ray is intercepted by
			// other intersection)
			if (closest != null && closest.getDistance() + PRECISION < dist) {
				continue;
			} else {
				
				// diffusion component
				double scalarProduct = Math.max(0,
						ri.getNormal().scalarProduct(ls.getPoint().sub(ri.getPoint()).normalize()));
				rgb[0] += ls.getR() * ri.getKdr() * scalarProduct;
				rgb[1] += ls.getG() * ri.getKdg() * scalarProduct;
				rgb[2] += ls.getB() * ri.getKdb() * scalarProduct;
				
				
				// reflection component
				Point3D n = ri.getNormal();
				Point3D v = eye.sub(ri.getPoint()).normalize();
				Point3D l = ri.getPoint().sub(ls.getPoint());
				Point3D r = l.sub(n.scalarMultiply(2 * n.scalarProduct(l))).normalize();
				scalarProduct = Math.max(0, v.scalarProduct(r));

				rgb[0] += ls.getR() * ri.getKrr() * Math.pow(scalarProduct, ri.getKrn());
				rgb[1] += ls.getG() * ri.getKrg() * Math.pow(scalarProduct, ri.getKrn());
				rgb[2] += ls.getB() * ri.getKrb() * Math.pow(scalarProduct, ri.getKrn());
				 
			}
		}

	}

	/**
	 * Finds closest intersection this ray intersects with. If such doesn't
	 * exists, <b>null</b> is returned!
	 * 
	 * @param scene
	 *            current scene
	 * @param ray
	 *            ray we trace for intersection
	 * @return instance of <tt>RayIntersection</tt> containing intersection data
	 *         if we such exists, <b>null</b> otherwise
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closest = null;
		for (GraphicalObject g : scene.getObjects()) {
			RayIntersection current = g.findClosestRayIntersection(ray);
			if (current == null) {
				continue;
			} else {
				if (closest == null) {
					closest = current;
				} else {
					if (current.getDistance() < closest.getDistance()) {
						closest = current;
					}
				}
			}
		}

		return closest;
	}
}
