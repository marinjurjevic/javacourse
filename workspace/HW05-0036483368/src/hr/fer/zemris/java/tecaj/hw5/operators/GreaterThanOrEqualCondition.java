package hr.fer.zemris.java.tecaj.hw5.operators;

import java.text.Collator;
import java.util.Locale;

/**
 * GreaterThanCondition returns all records whose value are bigger or equal than
 * given literal values.
 * 
 * @author Marin Jurjevic
 *
 */
public class GreaterThanOrEqualCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		Collator collator = Collator.getInstance(new Locale("hr-HR"));
		return collator.compare(value1, value2) >= 0;
	}

}
