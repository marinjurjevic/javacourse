package hr.fer.zemris.java.tecaj.hw5.operators;

/**
 * WildCardEquals is special case of comparison. It uses asterix (*) as wild
 * card character. It can contain only 1 wild card or none. If none are present,
 * it will simply compare literal to value. If asterix is present, it represents
 * any type of character and any number of characters on that position.
 * 
 * @author Marin Jurjevic
 *
 */
public class WildCardEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		int count = value2.length() - value2.replace("*", "").length();

		if (count == 0) {
			return value1.equals(value2);
		} else if (count == 1) {
			int index = value2.indexOf('*');

			if (index == 0) {
				return value1.endsWith(value2.substring(1));
			} else if (index == value2.length() - 1) {
				return value1.startsWith(value2.substring(0, value2.length() - 1));
			} else {
				String[] args = value2.split("\\*");
				return value1.startsWith(args[0]) && value1.endsWith(args[1]);
			}

		} else {
			throw new IllegalArgumentException("String literal can contain only one asterix (*)");
		}
	}
}
