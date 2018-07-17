package hr.fer.zemris.java.tecaj.hw5.utilities;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.operators.IComparisonOperator;

/**
 * Base class for creating a conditional expression. Conditional expression
 * consists of provided value to compare, string literal that value is compared to
 * and specified operation that should be performed.
 * @author Marin Jurjevic
 *
 */
public class ConditionalExpression implements ICondition {

	private IFieldValueGetter getter;
	private String literal;
	private IComparisonOperator operator;

	/**
	 * Creates new instance of ConditionalExpression. 
	 * @param getter instance of IFieldValueGetter
	 * @param literal string we compare records to
	 * @param operator operation we perform in comparison with this expression
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		super();
		this.getter = getter;
		this.literal = literal;
		this.operator = operator;
	}

	public IFieldValueGetter getGetter() {
		return getter;
	}

	public String getLiteral() {
		return literal;
	}

	public IComparisonOperator getOperator() {
		return operator;
	}

	@Override
	public boolean compare(StudentRecord record) {
		return operator.satisfied(getter.get(record), literal);
	}

}
