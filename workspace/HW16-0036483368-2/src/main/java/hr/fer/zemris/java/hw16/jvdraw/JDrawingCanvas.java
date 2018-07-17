package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.ObjectCreator;

/**
 * JDrawingCanvas represents blank area where user can in two mouse clicks draw lines and circles.
 * @author Marin Jurjevic
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * foreground color provider
	 */
	private IColorProvider fgColor;
	
	/**
	 * background color provider
	 */
	private IColorProvider bgColor;
	
	/**
	 * model whose objects will be drawn
	 */
	private DrawingModel model;
	
	/**
	 * current start point on canvas (user's first mouse click)
	 */
	private Point startPoint;
	
	/**
	 * end point on canvas (user's second mouse click)
	 */
	private Point endPoint;
	
	/**
	 * Current object, which will be eventually stored in model.
	 */
	private GeometricalObject currentObject;
	
	/**
	 * Object for creating shapes.
	 */
	private ObjectCreator creator;
	
	/**
	 * Creates new JDrawingCanvas with given area colors and model.
	 * @param fgColor color provider for foreground color
	 * @param bgColor color provider for background color
	 * @param model DrawingModel used for drawing objects
	 */
	public JDrawingCanvas(IColorProvider fgColor, IColorProvider bgColor, DrawingModel model) {
		super();
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.model = model;
		
		// listeners
		model.addDrawingModelListener(this);
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(creator == null){
					return;
				}
				
				if(startPoint == null){
					startPoint = e.getPoint();
				}else{
					endPoint = e.getPoint();
					endDrawing();
				}
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if(startPoint == null){
					return;
				}
				endPoint = e.getPoint();
				startDrawing();
			}
			
		});
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).draw(g, new Rectangle());
		}
		
		if (currentObject != null) {
			currentObject.draw(g, new Rectangle());
		}
	}
	
	/**
	 * Begins drawing, triggered by user first click.
	 */
	private void startDrawing(){
		if(currentObject == null){
			currentObject = creator.create(startPoint, endPoint, fgColor.getCurrentColor(), bgColor.getCurrentColor());
		}else{
			currentObject.setEndPoint(endPoint);
		}
		repaint();
	}
	
	/**
	 * Ends drawing, triggered by user second click.
	 */
	private void endDrawing(){
		model.add(currentObject);
		startPoint = null;
		endPoint = null;
		currentObject = null;
	}
	
	/**
	 * Sets current object creator to be in use.
	 * @param creator instance of object creator
	 */ 
	public void setObjectCreator(ObjectCreator creator){
		this.creator = creator;
	}
}
