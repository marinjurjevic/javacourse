package hr.fer.zemris.java.graphics;

import java.util.Scanner;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Demonstration program for rendering shapes on raster.
 * Raster content is printed on standard input.
 * 
 * @author Marin Jurjevic
 *
 */
public class Demo {

	/**
	 * Main accepts 1 or 2 arguments. If 2 arguments are given, they will
	 * represent width and height, in case 1 argument is given, it will
	 * represent both width and height.
	 * 
	 * @param args
	 *            dimensions of raster
	 */
	public static void main(String[] args) {
		int width, height;
		BWRaster raster = null;

		// check cmd arguments
		if (args.length > 2 || args.length == 0) {
			System.out.println("You must specify 1 or 2 arguments.");
			System.exit(-1);
		} else {
			try {
				width = Integer.parseInt(args[0]);

				if (args.length == 2) {
					height = Integer.parseInt(args[1]);
				} else {
					height = width;
				}

				raster = new BWRasterMem(width, height);

			} catch (NumberFormatException e) {
				System.out.println("Invalid number format specified.");
				System.exit(-1);
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid number values. Cannot create raster.");
			}
		}
		// read user input and render shapes
		try (Scanner sc = new Scanner(System.in)) {
			int lines = Integer.parseInt(sc.nextLine());
			GeometricShape[] shapes = readInput(lines, sc);
			drawShapes(raster, shapes);
		} catch (RuntimeException e) {
			System.out.println("An error has occured. Shuting down.");
			e.printStackTrace();
			System.exit(-1);
		}

		// print raster out
		RasterView view = new SimpleRasterView();
		view.produce(raster);
	}

	/**
	 * Reads all given user input and builds shapes from specified commands.
	 * 
	 * @param lines
	 *            how many lines will be taken into consideration
	 * @param sc
	 *            builder to help extract input text
	 * @return array of shapes ( null if FLIP command was given)
	 */
	private static GeometricShape[] readInput(int lines, Scanner sc) {
		GeometricShape[] shapes = new GeometricShape[lines];
		String[] args;
		for (int i = 0; i < lines; i++) {
			args = sc.nextLine().split("\\s+");
			switch (args[0].toUpperCase()) {
			case "FLIP":
				shapes[i] = null;
				break;
			case "RECTANGLE":
				shapes[i] = new Rectangle(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						Integer.parseInt(args[3]), Integer.parseInt(args[4]));
				break;
			case "SQUARE":
				shapes[i] = new Square(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
				break;
			case "ELLIPSE":
				shapes[i] = new Ellipse(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]),
						Integer.parseInt(args[4]));
				break;
			case "CIRCLE":
				shapes[i] = new Circle(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
				break;
			default:
				throw new IllegalArgumentException("Invalid shape name");
			}
		}

		return shapes;
	}

	/**
	 * Draws shapes on raster. It accepts an array of shapes and a raster
	 * object. Each element of array draws itself on the raster or if FLIP
	 * command was given it will change mode of rendering.
	 * 
	 * @param raster
	 *            raster object where elements will be drawn
	 * @param shapes
	 *            array containing shapes or null representing FLIP command
	 */
	private static void drawShapes(BWRaster raster, GeometricShape[] shapes) {
		boolean flip = false;

		for (int i = 0, size = shapes.length; i < size; i++) {
			if (shapes[i] == null) {
				if (flip == true) {
					flip = false;
					raster.disableFlipMode();
				} else {
					flip = true;
					raster.enableFlipMode();
				}
			} else {
				shapes[i].draw(raster);
			}
		}
	}

}
