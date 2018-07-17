package hr.fer.zemris.java.tecaj.hw5.parser;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.getters.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.getters.JmbagFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.getters.LastNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.lexer.Token;
import hr.fer.zemris.java.tecaj.hw5.operators.EqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.GreaterThanCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.GreaterThanOrEqualCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.operators.LessThanCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.LessThanOrEqualCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.NotEqualCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.WildCardEqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.utilities.ConditionalExpression;

/**
 * Simple parser for query command. It generates list of ConditionalExpressions
 * based on given input.
 * 
 * @author Marin Jurjevic
 *
 */
public class QueryParser {

	/**
	 * private lexer used for text analysis
	 */
	private static Lexer lexer;

	/**
	 * list of ConditionalExpresions where parser stores generated expressions
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Creates new parser and parses given input text.
	 * 
	 * @param text
	 *            input text
	 */
	public QueryParser(String text) {
		lexer = new Lexer(text);
		parse();

	}

	/**
	 * Parses given text with help of Lexer instance. It parses generated tokens
	 * into ConditionalExpressions and stores them into a private list of this
	 * class.
	 */
	private void parse() {
		expressions = new LinkedList<ConditionalExpression>();
		String attr = "";
		String operator = "";
		String literal = "";
		boolean end = false;

		while (!end) {

			Token currentToken = lexer.nextToken();

			switch (currentToken.getType()) {
			case ATTRIBUT:
				attr = currentToken.getValue();
				break;
			case LITERAL:
				literal = currentToken.getValue();
				break;
			case OPERATOR:
				if (currentToken.getValue().equalsIgnoreCase("AND")) {
					expressions.add(createNewCond(attr, operator, literal));
					continue;
				}

				operator = currentToken.getValue();
				break;
			case EOF:
				end = true;
				break;
			}

		}

		expressions.add(createNewCond(attr, operator, literal));

	}

	/**
	 * Factory method for creating new ConditionalExpression.
	 * 
	 * @param attr
	 *            attribute name
	 * @param operator
	 *            operator symbol
	 * @param literal
	 *            literal for comparison with attr
	 * @return new instance of ConditionalExpression
	 */
	private ConditionalExpression createNewCond(String attr, String operator, String literal) {
		IFieldValueGetter getter;
		IComparisonOperator op;

		switch (attr) {
		case "firstName":
			getter = new FirstNameFieldGetter();
			break;
		case "lastName":
			getter = new LastNameFieldGetter();
			break;
		case "jmbag":
			getter = new JmbagFieldGetter();
			break;
		default:
			throw new QueryParserException("Invalid attribut name!");
		}

		switch (operator) {
		case "=":
			op = new EqualsCondition();
			break;
		case "!=":
			op = new NotEqualCondition();
			break;
		case ">":
			op = new GreaterThanCondition();
			break;
		case ">=":
			op = new GreaterThanOrEqualCondition();
			break;
		case "<":
			op = new LessThanCondition();
			break;
		case "<=":
			op = new LessThanOrEqualCondition();
			break;
		case "LIKE":
			op = new WildCardEqualsCondition();
			break;
		default:
			throw new QueryParserException("Invalid operator name!");
		}

		return new ConditionalExpression(getter, literal, op);
	}

	/**
	 * @return generated list of ConditionalExpressions
	 */
	public List<ConditionalExpression> getConditionalExpressions() {
		return expressions;
	}
}
