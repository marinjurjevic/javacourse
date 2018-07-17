package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * JColorArea represents model for currently selected color. Component is painted in color it is currently selected.
 * Clicking on component boundaries triggers JColorChooser and user can pick new color.
 * @author Marin Jurjevic
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * currently selected color
	 */
	private Color selectedColor;
	
	/**
	 * registered listeners tracking color changes
	 */
	private List<ColorChangeListener> listeners;
	
	/**
	 * Creates new JColorArea with specified initial color.
	 * @param initialColor initial color of JColorArea
	 */
	public JColorArea(Color initialColor) {
		selectedColor = initialColor;
		listeners = new LinkedList<>();
		setBackground(selectedColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Pick new color", selectedColor);
				if(newColor != null){
					update(newColor); 
				}
			}
		});
	}
	
	/**
	 * Updates JColorArea to new color.
	 * @param newColor new color represented by this JColorArea
	 */
	private void update(Color newColor){
		for(ColorChangeListener l : listeners){
			l.newColorSelected(this, selectedColor, newColor);
		}
		selectedColor = newColor;
		repaint();
	}
	
	/**
	 * Registers new ColorChangeListener.
	 * @param l listener who wants to track color changes
	 */
	public void addColorChangeListener(ColorChangeListener l){
		listeners.add(l);
	}
	
	/**
	 * Deregisters ColorChangeListener from this subject.
	 * @param l listener to be removed
	 */
	public void removeColorChangeListener(ColorChangeListener l){
		listeners.remove(l);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15,15);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, 15, 15);
	}
	
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
}
