package hr.fer.zemris.java.tecaj.p3d;

public class Demonstracija {

	static interface Function {
		double valueAt(double x);
	}

	static class Kvadriranje implements Function {
		@Override
		public double valueAt(double x) {
			return x * x;
		}
	}

	static class Korijenovanje implements Function {

		@Override
		public double valueAt(double x) {
			return Math.sqrt(x);
		}
	}

	public static void main(String[] args) {
		double[] polje = { 0.0, 3.14, 2.71, 15.0, 25.0 };

		ispisi(polje, x -> Math.pow(Math.sin(x) + 0.45, 1.3));
	}

	public static void ispisi(double[] polje, Function funkcija) {
		for (Double d : polje) {
			double r = funkcija.valueAt(d);
			System.out.printf("f(%f) = %f%n", d, r);
		}
	}

}
