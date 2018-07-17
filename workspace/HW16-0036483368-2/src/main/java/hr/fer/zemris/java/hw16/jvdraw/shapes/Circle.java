package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;

/**
 * Circle class for modelling a circular shape in canvas. First user clicks determines starting point, in
 * this context it will be circle center. From that moment, user determines next point which will be used for 
 * calculating radius.
 * @author Marin Jurjevic
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * number of instances created
	 */
	private static long instanceCounter = 1L;

	/**
	 * circle radius
	 */
	protected int radius;
	
	/**
	 * Creates new circle based on two points. First point is circle center, and second point is used for determining
	 * circle radius. 
	 * @param center circle center
	 * @param point any circular point
	 * @param color circle color
	 */
	public Circle(Point center, Point point, Color color) {
		super("Circle " + instanceCounter++, center, point, color);
		radius = (int)Math.sqrt( Math.pow(center.x - point.x, 2) +   Math.pow(center.y-point.y, 2));
	}
	
	/**
	 * Creates new circle based on one point(center) and radius. Color represents color for rendering circle outline.
	 * @param center circle center
	 * @param radius circle radius length
	 * @param color circle color
	 */
	public Circle(Point center, int radius, Color color) {
		super("Circle " + instanceCounter++, center, new Point(center.x+radius,center.y), color);
		this.radius = radius;
	}

	@Override
	public void draw(Graphics g, Rectangle box) {
		g.setColor(color);
		g.drawOval(startPoint.x-radius/2- box.x, startPoint.y-radius/2- box.y, radius, radius);
	}

	@Override
	public void update() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		JTextField cX = new JTextField(Integer.toString(startPoint.x));
		JTextField cY = new JTextField(Integer.toString(startPoint.y));
		
		JTextField rad = new JTextField(Integer.toString(radius));
		
		panel.add(new JLabel("center point X: "));
		panel.add(cX);
		panel.add(new JLabel("center point Y: "));
		panel.add(cY);
		
		panel.add(new JLabel("radius: "));
		panel.add(rad);
		
		JColorArea newColor = new JColorArea(color);
		panel.add(new JLabel("Color: "));
		panel.add(newColor);
		int status = JOptionPane.showConfirmDialog(null, panel, "Change parameters:", JOptionPane.OK_CANCEL_OPTION);
		if (status == JOptionPane.OK_OPTION) {
			Point newStartPoint = null;
			try {
				newStartPoint = new Point(
						cX.getText().isEmpty()?0:Integer.parseInt(cX.getText()),
						cY.getText().isEmpty()?0:Integer.parseInt(cY.getText())
				);
				radius = rad.getText().isEmpty()?0:Integer.parseInt(rad.getText());
			} catch (NumberFormatException ex) {
				return;
			}
			setColor(newColor.getCurrentColor());
			setStartPoint(newStartPoint);
			setEndPoint(new Point(newStartPoint.x+radius,newStartPoint.y));
		}
	}
	
	@Override
	public void setEndPoint(Point endPoint) {
		// TODO Auto-generated method stub
		super.setEndPoint(endPoint);
		radius = (int)Math.sqrt( Math.pow(startPoint.x - endPoint.x, 2) +   Math.pow(startPoint.y-endPoint.y, 2));
	}
	
	@Override
	public String getInfo() {
		return "CIRCLE " + startPoint.x + " " +
				startPoint.y + " " + 
			     radius + " " +
			     color.getRed() + " " +
			     color.getGreen() + " " + 
			     color.getBlue();
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(startPoint.x-radius,startPoint.y-radius, 2*radius,2*radius);
	}
}
