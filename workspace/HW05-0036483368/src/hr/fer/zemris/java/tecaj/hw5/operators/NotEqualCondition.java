package hr.fer.zemris.java.tecaj.hw5.operators;

/**
 * GreaterThanCondition returns all records whose value are not equal to given
 * string literal value.
 */
public class NotEqualCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {

		return value1.compareTo(value2) != 0;
	}

}
