package hr.fer.zemris.java.tecaj.hw5.operators;

/**
 * EqualsCondition returns true only if value1 and value2 are equal
 * lexicographically.
 * 
 * @author Marin Jurjevic
 *
 */
public class EqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {

		return value1.compareTo(value2) == 0;
	}

}
