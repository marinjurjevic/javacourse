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
 * Line models a simple line. Line is determined by two points, on canvas user will click two times to specify starting
 * and ending point.
 * @author Marin Jurjevic
 *
 */
public class Line extends GeometricalObject {

	/**
	 * number of instances created
	 */
	private static long instanceCounter = 1L;

	/**
	 * Creates new line object. Line is described by its name, starting point and ending point.
	 * @param lineStart line start
	 * @param lineEnd line end
	 * @param color line color
	 */
	public Line(Point lineStart, Point lineEnd, Color color) {
		super("Line " + instanceCounter++, lineStart, lineEnd, color);
	}

	@Override
	public void draw(Graphics g, Rectangle box) {
		g.setColor(color);
		g.drawLine(startPoint.x - box.x, startPoint.y - box.y, endPoint.x- box.x, endPoint.y- box.y);
	}
	
	@Override
	public void update() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		JTextField spX = new JTextField(Integer.toString(startPoint.x));
		JTextField spY = new JTextField(Integer.toString(startPoint.y));
		
		JTextField epX = new JTextField(Integer.toString(endPoint.x));
		JTextField epY = new JTextField(Integer.toString(endPoint.y));
		
		panel.add(new JLabel("start point X: "));
		panel.add(spX);
		panel.add(new JLabel("start point Y: "));
		panel.add(spY);
		
		panel.add(new JLabel("end point X: "));
		panel.add(epX);
		panel.add(new JLabel("end point Y: "));
		panel.add(epY);
		
		JColorArea newColor = new JColorArea(color);
		panel.add(new JLabel("Color: "));
		panel.add(newColor);
		int status = JOptionPane.showConfirmDialog(null, panel, "Change parameters:", JOptionPane.OK_CANCEL_OPTION);
		if (status == JOptionPane.OK_OPTION) {
			Point newStartPoint = null;
			Point newEndPoint = null;
			try {
				newStartPoint = new Point(
						spX.getText().isEmpty()?0:Integer.parseInt(spX.getText()),
						spY.getText().isEmpty()?0:Integer.parseInt(spY.getText())
				);
				newEndPoint = new Point(
						epX.getText().isEmpty()?0:Integer.parseInt(epX.getText()),
						epY.getText().isEmpty()?0:Integer.parseInt(epY.getText())
				);
			} catch (NumberFormatException ex) {
				return;
			}
			setColor(newColor.getCurrentColor());
			setStartPoint(newStartPoint);
			setEndPoint(newEndPoint);
		}
	}

	@Override
	public String getInfo() {
		return "LINE " + startPoint.x + " " +
				startPoint.y + " " + 
				endPoint.x + " " +
				endPoint.y + " " + 
				color.getRed() + " " +
				color.getGreen() + " " + 
				color.getBlue();
	}

	@Override
	public Rectangle getBoundingBox() {
		int minX;
		int minY;
		minX = Math.min(startPoint.x, endPoint.x);
		minY = Math.min(startPoint.y, endPoint.y);
		return new Rectangle(minX, minY, Math.abs(startPoint.x-endPoint.x), Math.abs(startPoint.y-endPoint.y));
	}

	
}
