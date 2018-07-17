package hr.fer.zemris.java.tecaj.hw5.utilities;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.parser.QueryParser;

/**
 * QueryFilter is a filter class that implements functional interface IFilter.
 * It creates one ICondition object which evaluates given student record.
 * 
 * @author Marin Jurjevic
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * private ICondition that compares given student record against all cond.
	 * expressions.
	 */
	ICondition conditions;

	/**
	 * Creates new QueryFilter which combines conditional expressions from query
	 * command.
	 * 
	 * @param text text that will get parsed into conditional expressions
	 */
	public QueryFilter(String text) {
		QueryParser parser = new QueryParser(text);
		conditions = new CompositeConditionalExpression(parser.getConditionalExpressions());
	}

	@Override
	public boolean accepts(StudentRecord record) {
		return conditions.compare(record);
	}

}
