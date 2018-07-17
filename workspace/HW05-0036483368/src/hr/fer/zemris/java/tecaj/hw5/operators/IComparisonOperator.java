package hr.fer.zemris.java.tecaj.hw5.operators;

/**
 * Functional interface that provides method satisfied. It compares first value
 * to second and returns the result as boolean.
 * 
 * @author Marin Jurjevic
 *
 */
public interface IComparisonOperator {

	/**
	 * Evaluates value1 parameter versus value2.
	 * 
	 * @param value1
	 *            object we evaluate
	 * @param value2
	 *            object we evaluate value1 against
	 * @return true if value1 satisfies condition that value2 set, false
	 *         otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
