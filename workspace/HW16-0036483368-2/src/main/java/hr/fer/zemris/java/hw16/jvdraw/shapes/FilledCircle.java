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
 * Filled circle is a class derived from {@link Circle}. It expands circles functionality and adds new property, fill color.
 * Fill color is used to fill circle's inner area.
 * @author Marin Jurjevic
 *
 */
public class FilledCircle extends Circle {

	/**
	 * circle fill color
	 */
	private Color fillColor;
	
	/**
	 * Creates new circle based on two points and two colors. First point is circle center, and second point is used for determining
	 * circle radius. First color will color circular and second color is for inner area.
	 * @param center circle center
	 * @param point any circular point
	 * @param color circle color
	 * @param fillColor used to fill circle inner side
	 */
	public FilledCircle(Point center, Point point, Color color, Color fillColor) {
		super(center, point, color);
		this.fillColor = fillColor;
	}
	
	/**
	 * Creates new circle based on one point(circle center), radius length, and two colors.First color will color circular and second color is for inner area.
	 * @param center circle center
	 * @param radius radius length
	 * @param color circle color
	 * @param fillColor used to fill circle inner side
	 */
	public FilledCircle(Point center, int radius, Color color, Color fillColor) {
		super(center, new Point(center.x+radius, center.y), color);
		this.fillColor = fillColor;
	}

	@Override
	public void draw(Graphics g, Rectangle box) {
		g.setColor(fillColor);
		g.fillOval(startPoint.x-radius/2- box.x, startPoint.y-radius/2- box.y, radius, radius);
		super.draw(g, box);
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
		
		JColorArea newFColor = new JColorArea(color);
		panel.add(new JLabel("Outline color: "));
		
		JColorArea newBColor = new JColorArea(fillColor);
		panel.add(new JLabel("Fill color: "));
		
		panel.add(newFColor);
		panel.add(newBColor);
		
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
			setColor(newFColor.getCurrentColor());
			fillColor = newBColor.getCurrentColor();
			setStartPoint(newStartPoint);
			setEndPoint(new Point(newStartPoint.x+radius,newStartPoint.y));
		}
	}

	@Override
	public String getInfo() {
		return "FCIRCLE " + startPoint.x + " " +
				startPoint.y + " " + 
			     radius + " " +
			     color.getRed() + " " +
			     color.getGreen() + " " + 
			     color.getBlue() + " " +
			     fillColor.getRed() + " " +
			     fillColor.getGreen() + " " + 
			     fillColor.getBlue();
	}

	
}
