package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * <tt>CalcLayout</tt> is a custom layout manager for managing components for
 * purposes of a building a simple calculator application.
 * 
 * @author Marin Jurjevic
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * fixed number of rows used in CalcLayout
	 */
	private static final int ROWS = 5;

	/**
	 * fixed number of columns used in CalcLayout
	 */
	private static final int COLS = 7;

	/**
	 * vertical and horizontal gap
	 */
	private int gap;

	/**
	 * map of components used by layout manager
	 */
	private Map<RCPosition, Component> components;

	/**
	 * reference to instance of RCPosition representing component placed on
	 * position (1,1) in the container
	 */
	private static final RCPosition FIRST = new RCPosition(1, 1);

	/**
	 * Creates new instance of <tt>CalcLayout</tt> with default gap set to 0.
	 * This will result in glued components as no gap will be present between
	 * any component.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Creates new instance of <tt>CalcLayout</tt> with given gap. Gap wil be
	 * set vertically and horizontally.
	 * 
	 * @param gap
	 *            specified gap between components
	 */
	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new IllegalArgumentException("Gap can not be negative!");
		}
		this.gap = gap;

		components = new HashMap<>();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int prefWidth = 0;
		int prefHeight = 0;
		Insets parIns = parent.getInsets();

		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition pos = entry.getKey();
			Component comp = entry.getValue();

			if (comp.getPreferredSize() != null) {
				prefHeight = Math.max(prefHeight, comp.getPreferredSize().height);
				if (pos.equals(FIRST)) {
					continue;
				}
				prefWidth = Math.max(prefWidth, comp.getPreferredSize().width);
			}

		}

		return new Dimension(parIns.left + parIns.right + prefWidth * COLS + gap * (COLS - 1),
				parIns.top + parIns.bottom + prefHeight * ROWS + gap * (ROWS - 1));
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int minWidth = 0;
		int minHeight = 0;
		Insets parIns = parent.getInsets();

		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition pos = entry.getKey();
			Component comp = entry.getValue();

			if (comp.getPreferredSize() != null) {
				minHeight = Math.max(minHeight, comp.getMinimumSize().height);
				if (pos.equals(FIRST)) {
					continue;
				}
				minWidth = Math.max(minWidth, comp.getMinimumSize().width);
			}

		}
		return new Dimension(parIns.left + parIns.right + minWidth * COLS + gap * (COLS - 1),
				parIns.top + parIns.bottom + minHeight * ROWS + gap * (ROWS - 1));
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		int maxWidth = 0;
		int maxHeight = 0;
		Insets parIns = target.getInsets();

		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition pos = entry.getKey();
			Component comp = entry.getValue();

			if (comp.getPreferredSize() != null) {
				maxHeight = Math.max(maxHeight, comp.getMaximumSize().height);
				if (pos.equals(FIRST)) {
					continue;
				}
				maxWidth = Math.max(maxWidth, comp.getMaximumSize().width);
			}

		}
		return new Dimension(parIns.left + parIns.right + maxWidth * COLS + gap * (COLS - 1),
				parIns.top + parIns.bottom + maxHeight * ROWS + gap * (ROWS - 1));
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets parIns = parent.getInsets();

		double wScale = parent.getWidth() / preferredLayoutSize(parent).getWidth();
		double hScale = parent.getHeight() / preferredLayoutSize(parent).getHeight();

		Dimension dim = getComponentDimensions();
		dim.setSize(wScale * dim.width, hScale * dim.height);

		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition pos = entry.getKey();
			Component comp = entry.getValue();

			comp.setSize(dim);
			if (pos.equals(FIRST)) {
				comp.setBounds(parIns.left, parIns.top, dim.width * 5 + 4 * gap, dim.height);
			} else {
				comp.setLocation(parIns.left + (pos.getColumn() - 1) * (dim.width + gap),
						parIns.top + (pos.getRow() - 1) * (dim.height + gap));
			}
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition pos;
		if (constraints.getClass() == String.class) {
			pos = RCPosition.getRCPosition((String) constraints);
		} else {
			pos = (RCPosition) constraints;
		}

		if (pos.getRow() > ROWS || pos.getColumn() > COLS) {
			throw new IndexOutOfBoundsException("No such index in the table!");
		}

		if (pos.getRow() == 1 && (pos.getColumn() >= 2 && pos.getColumn() <= 5)) {
			throw new IllegalArgumentException("Invalid column index for first row!");
		}

		if (components.containsKey(pos)) {
			throw new IllegalArgumentException("Constraint already exists!");
		} else {
			components.put(pos, comp);
		}

	}

	/**
	 * Gets preferred component dimensions.
	 * 
	 * @return component preferred dimension
	 */
	private Dimension getComponentDimensions() {
		int prefWidth = 0;
		int prefHeight = 0;

		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition pos = entry.getKey();
			Component comp = entry.getValue();

			if (comp.getPreferredSize() != null) {
				prefHeight = Math.max(prefHeight, comp.getPreferredSize().height);
				if (pos.equals(FIRST)) {
					continue;
				}
				prefWidth = Math.max(prefWidth, comp.getPreferredSize().width);

			}

		}

		return new Dimension(prefWidth, prefHeight);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition pos = null;
		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			if (entry.getValue().equals(comp)) {
				pos = entry.getKey();
			}
		}
		components.remove(pos);
	}

	// NOT USED
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void invalidateLayout(Container target) {
	}

}
