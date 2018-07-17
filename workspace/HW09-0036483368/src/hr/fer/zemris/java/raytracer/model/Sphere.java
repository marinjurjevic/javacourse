package hr.fer.zemris.java.raytracer.model;

/**
 * Implementation of sphere graphical object that will be used in ray tracing
 * rendering technique.
 * 
 * @author Marin Jurjevic
 *
 */
public class Sphere extends GraphicalObject {

	/** sphere center */
	private Point3D center;

	/** sphere radius */
	double radius;

	// koefficients kd* determine object parameters for diffuse component
	private double kdr;
	private double kdg;
	private double kdb;

	// koefficients kr* determine object parameters for reflected component
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	/**
	 * Creates new sphere object containing info about it's position in space,
	 * radius and info about material for color calculations.
	 * 
	 * @param center
	 *            sphere's center
	 * @param radius
	 *            sphere's radius
	 * @param kdr
	 *            diffuse coefficient for red component
	 * @param kdg
	 *            diffuse coefficient for green component
	 * @param kdb
	 *            diffuse coefficient for blue component
	 * @param krr
	 *            reflection coefficient for red component
	 * @param krg
	 *            reflection coefficient for green component
	 * @param krb
	 *            reflection coefficient for blue component
	 * @param krn
	 *            reflection coefficient (property of material)
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D i = ray.direction;
		Point3D o_c = ray.start.sub(center);
		double d = Math.pow(i.scalarProduct(o_c), 2) - Math.pow(o_c.norm(), 2) + Math.pow(radius, 2);
		double a = i.scalarProduct(o_c);

		if (d >= 0) {
			double dist1 = -a + Math.sqrt(d);
			double dist2 = -a - Math.sqrt(d);
			if (dist1 < 0 && dist2 < 0) {
				return null;
			}
			double minDist = Math.min(dist1, dist2);
			return new SphereIntersection(ray.start.add(i.scalarMultiply(minDist)), minDist, true);
		} else {
			return null;
		}
	}

	/**
	 * Implementation of ray intersection for sphere objects.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	private class SphereIntersection extends RayIntersection {

		/**
		 * Creates new SphereIntersection containing info about intersection on
		 * sphere's surface. Ray intersection contains intersection coordinates,
		 * distance from ray start point and intersection and flag for
		 * determining is reflection outer or inner.
		 * 
		 * @param point
		 *            intersection coordinates
		 * @param distance
		 *            distance between ray start point and intersection
		 * @param outer
		 *            true if reflection is outer, false if inner
		 * 
		 * @see RayIntersection
		 */
		public SphereIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		@Override
		public Point3D getNormal() {
			return super.getPoint().sub(Sphere.this.center).normalize();
		}

		@Override
		public double getKdr() {
			return Sphere.this.kdr;
		}

		@Override
		public double getKdg() {
			return Sphere.this.kdg;
		}

		@Override
		public double getKdb() {
			return Sphere.this.kdb;
		}

		@Override
		public double getKrr() {
			return Sphere.this.krr;
		}

		@Override
		public double getKrg() {
			return Sphere.this.krg;
		}

		@Override
		public double getKrb() {
			return Sphere.this.krb;
		}

		@Override
		public double getKrn() {
			return Sphere.this.krn;
		}

	}
}
