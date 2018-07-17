package hr.fer.zemris.java.gui.layouts;

/**
 * Represents class for retrieving <tt>CalcLayout</tt> constraints when adding
 * components to componenet using <tt>CalcLayout</tt> manager. Instances of this
 * class are keeping exact information where will component be added in
 * container.
 * 
 * @author Marin Jurjevic
 *
 */
public class RCPosition {

	/**
	 * row index
	 */
	private final int row;

	/**
	 * column index
	 */
	private final int column;

	/**
	 * Creates new RCPosition instance containing information where will
	 * component be placed inside given container.
	 * 
	 * @param row
	 *            row index
	 * @param column
	 *            column index
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || column < 1) {
			throw new IllegalArgumentException("Invalid constraint index.");
		}
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns row index.
	 * 
	 * @return row index.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns column index.
	 * 
	 * @return column index.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Converts string representation of component position to instance of
	 * <tt>RCPosition</tt> class. This method can throw
	 * <tt>NumberFormatException</tt> if given string is invalid.
	 * 
	 * @param pos
	 *            string containing row and column index
	 * @return instance of RCPosition clas
	 */
	public static RCPosition getRCPosition(String pos) {
		pos = pos.replace("\"", " ").trim();
		String row = pos.split(",")[0];
		String col = pos.split(",")[1];

		return new RCPosition(Integer.parseInt(row), Integer.parseInt(col));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
