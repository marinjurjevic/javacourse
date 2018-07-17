package hr.fer.zemris.java.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Program which counts area and perimeter of a rectangle.
 * If program doesn't recieve parameters through command line, 
 * user will have to specify them on standard input(keyboard).
 * Program takes parameters through command line if both 
 * parameters are given. Any other number of parameters 
 * will be considered invalid.
 * 
 * @author Marin Jurjeviæ
 */
public class Rectangle {

	/**
	 * Starting point of this program, main method.
	 * @param args command line arguments
	 * @throws IOException If reader encounters problem
	 */
	public static void main(String[] args) throws IOException {
		if(args.length == 2){
			double height = Double.parseDouble(args[0]);
			double width = Double.parseDouble(args[1]);
			message(height, width);
		}
		
		else if(args.length == 0){
			BufferedReader reader= new BufferedReader(
					new InputStreamReader(
							new BufferedInputStream(System.in))
			);
			
			double height = readFromKeyboard(reader, "height");
			double width = readFromKeyboard(reader, "width");
			message(height, width);
			
			reader.close();
		}
		else 
			System.out.println("Invalid number of arguments was provided");
	}
	
	/**
	 * Calculates area of given rectangle.
	 * @param x height of rectangle
	 * @param y width of rectangle
	 * @return area of rectangle
	 */
	public static double area(double x, double y){
		return x*y;
	}
	
	/**
	 * Calculates perimeter of given rectangle.
	 * @param x height of rectangle
	 * @param y width of rectangle
	 * @return perimeter of given rectangle
	 */
	public static double perimeter(double x, double y){
		return 2*x + 2*y;
	}
	
	/**
	 * Prints out specified dimensions of given rectangle, and
	 * area and perimeter on standard output.
	 * @param x height of rectangle
	 * @param y width of rectangle
	 */
	public static void message(double x, double y){
		System.out.println("You have specified a rectangle with width " + x + " and height " + y + "."
	  + "Its area is " + area(x,y) + " and its perimeter is " +  perimeter(x,y));
	}
	
	/**
	 * Takes reader to read from standard input, and based on side reads required value.
	 * @param reader reader object used to extract data from standard input
	 * @param side control argument used so method knows what specific dimension is it reading
	 * @return value of given dimension specified by {@link param side}
	 * @throws IOException 
	 */
	private static double readFromKeyboard(BufferedReader reader, String side) throws IOException {
		double value = -1;
		String line;
		
		while(true){
			System.out.println("Please provide " + side +": ");
			
			line = reader.readLine();
			line.trim();
			
			if(line!=null)
				value = Double.parseDouble(line);
			
			if(value<0.0)
				System.out.println(side + " is negative. Please try again.");
			else break;
		}
		
		return value;
	}
}
