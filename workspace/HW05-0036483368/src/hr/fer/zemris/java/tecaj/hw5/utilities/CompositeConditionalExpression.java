package hr.fer.zemris.java.tecaj.hw5.utilities;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Implementation of Composite design pattern. This conditional expression is
 * composed of arbitrary number of given ConditionalExpression's.
 * 
 * @author Marin Jurjevic
 *
 */
public class CompositeConditionalExpression implements ICondition {

	/**
	 * Stores all given conditional expressions.
	 */
	private List<ConditionalExpression> condExpressions;

	/**
	 * Creates new CompositeConditionalExpression which is composed of all
	 * conditional expressions given as arguments of this constructor.
	 * 
	 * @param conditionalExpressions list of cond. expressions to evaluate 
	 */
	public CompositeConditionalExpression(List<ConditionalExpression> conditionalExpressions) {
		this.condExpressions = conditionalExpressions;
	}

	/**
	 * Compares given StudentRecord against all conditional records and returns
	 * a true/false result.
	 */
	@Override
	public boolean compare(StudentRecord record) {
		for (ConditionalExpression ce : condExpressions) {
			if (!ce.compare(record)) {
				return false;
			}
		}

		return true;
	}

}
