package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * <tt>BarChartComponent</tt> is representation of a bar chart that will be
 * drawn. This extended JComponent has only {@link JComponent#paintComponent}
 * overriden.
 * 
 * @author Marin Jurjevic
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -2290688286165650352L;

	/**
	 * instance of {@link BarChart}
	 */
	private final BarChart model;

	/**
	 * graph origin point
	 */
	private Point origin;

	/**
	 * fixed gap between axis description and values on axis
	 */
	private final int gap1 = 10;

	/**
	 * fixed gap between values on axis and axis
	 */
	private final int gap2 = 10;

	/**
	 * streak length
	 */
	private final int streak = 5;

	/**
	 * FontMetrics used by graphics object
	 */
	private FontMetrics fm;

	/**
	 * height of axis description
	 */
	private int descHeight;

	/**
	 * width of numbers on y axis calculated
	 */
	private int numberWidth;

	/**
	 * component insets
	 */
	private Insets ins;

	/**
	 * color used for drawing axis
	 */
	private Color axColor = new Color(192, 192, 192);

	/**
	 * color used for drawing grid
	 */
	private Color gridColor = new Color(253, 209, 150, 128);

	/**
	 * color used for drawing bars on chart
	 */
	private Color barColor = new Color(245, 114, 75);

	/**
	 * color used for drawing bar shadows
	 */
	private Color shadowColor = new Color(150, 150, 150, 128);

	/**
	 * Creates new BarChartComponent based on given model
	 * 
	 * @param model
	 *            model containing all necessary info so that this object can
	 *            draw it on frame
	 */
	public BarChartComponent(BarChart model) {
		this.model = model;
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 20));
		ins = getInsets();

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		fm = g2.getFontMetrics();
		descHeight = fm.getHeight();
		numberWidth = fm.stringWidth(String.valueOf(model.getyMax()));

		// get frame size
		int w = getWidth();
		int h = getHeight();
		origin = calcOrigin(w, h);

		g2.setFont(new Font("Arial", Font.BOLD, 12));
		drawDescriptions(w, h, g2);
		drawGrid(w, h, g2);
	}

	/**
	 * Calculates origin coordinates
	 * 
	 * @param w
	 *            component width
	 * @param h
	 *            component height
	 * @return new instance of Point encapsulating coordinate information
	 */
	private Point calcOrigin(int w, int h) {
		int x0 = descHeight + gap1 + numberWidth + gap2 + ins.left;
		int y0 = h - 2 * descHeight - gap1 - gap2 - ins.bottom;
		return new Point(x0, y0);
	}

	/**
	 * Draws axis description.
	 * 
	 * @param w
	 *            component width
	 * @param h
	 *            component height
	 * @param g2
	 *            graphics object
	 */
	private void drawDescriptions(int w, int h, Graphics2D g2) {
		g2.setFont(new Font("Arial", Font.PLAIN, 12));
		// x axis description
		int w2 = w - origin.x;
		g2.drawString(model.getxDesc(), w - w2 / 2 - fm.stringWidth(model.getxDesc()) / 2, h - ins.bottom);

		// y axis description
		AffineTransform saveAT = g2.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2.setTransform(at);

		g2.drawString(model.getyDesc(), -origin.y / 2 - fm.stringWidth(model.getyDesc()), ins.left);

		// reset transform
		g2.setTransform(saveAT);
		// reset font
		g2.setFont(new Font("Arial", Font.BOLD, 12));
	}

	/**
	 * Draws whole bar chart including axis, numbers and bars.
	 * 
	 * @param w
	 *            component width
	 * @param h
	 *            component height
	 * @param g2
	 *            graphics object
	 */
	private void drawGrid(int w, int h, Graphics2D g2) {
		// calculate starting point and increments for y axis
		int min = model.getyMin();
		int diff = model.getyDiff();
		int nYSteps = (model.getyMax() - model.getyMin()) / model.getyDiff();
		int yStep = (origin.y - ins.top) / nYSteps;

		// starting y
		int y = origin.y;

		for (int i = 0; i <= nYSteps; i++) {
			g2.setColor(Color.BLACK);
			String number = String.valueOf(min + i * model.getyDiff());
			g2.drawString(number, origin.x - fm.stringWidth(number) - gap2, y + fm.getAscent() / 2);

			g2.setColor(axColor);
			g2.drawLine(origin.x - streak, y, origin.x, y);
			if (i != 0) {
				g2.setColor(gridColor);
			}
			g2.drawLine(origin.x + 1, y, w - ins.right, y);
			y -= yStep;
		}

		// calculate starting point and increments for x axis
		int nXSteps = model.getColumns().size();
		int xStep = (w - origin.x - ins.right) / nXSteps;
		int x = origin.x;

		// retrieve x axis numbers
		List<XYValue> pairs = model.getColumns();
		pairs.sort( (v1,v2)->Integer.compare(v1.getX(), v2.getX()) );
		
		for (int i = 0; i <= nXSteps; i++) {
			if (i != 0) {
				g2.setColor(Color.BLACK);
				String number = String.valueOf(pairs.get(i - 1).getX());
				g2.drawString(number, x - xStep / 2 - fm.stringWidth(number), origin.y + descHeight + gap2);
			}

			g2.setColor(axColor);
			g2.drawLine(x, origin.y + streak, x, origin.y);
			if (i != 0) {
				g2.setColor(gridColor);
			}
			g2.drawLine(x, origin.y - 1, x, ins.top);

			// draw bars and shadows
			if (i != nXSteps) {
				g2.setColor(barColor);
				int height = (int) Math.abs(((yStep * 1.0 / diff) * pairs.get(i).getY()));
				g2.fillRect(x + 1, origin.y - height, xStep, height);

				// white frame
				g2.setColor(Color.WHITE);
				g2.drawLine(x + 1, origin.y - height, x + 1 + xStep, origin.y - height);
				g2.drawLine(x + xStep, origin.y - height, x + xStep, origin.y - 1);

				// shadows
				g2.setColor(shadowColor);
				height -= 5;
				g2.fillRect(x + 1 + xStep, origin.y - height, xStep / 20, height);
			}

			x += xStep;
		}
	}

}
