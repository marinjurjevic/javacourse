package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * ForLoopNode represents a single for-loop construct.
 * 
 * @author Marin Jurjevic
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Stores variable as {@code ElementVariable}
	 */
	private final ElementVariable variable;

	/**
	 * Stores start expression as {@code Element}
	 */
	private final Element startExpression;

	/**
	 * Stores end expression as {@code Element}
	 */
	private final Element endExpression;

	/**
	 * Stores step expression as {@code Element}
	 */
	private final Element stepExpression;

	/**
	 * Creates new instance of {@code ForLoopNode} containing specified
	 * properties. Last expression argument (step) can be null.
	 * 
	 * @param variable variable element
	 * @param startExpression starting expression
	 * @param endExpression ending expression
	 * @param stepExpression (optional) step to be made each iteration
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * 
	 * @return variable in this ForLoopNode
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * 
	 * @return start expression in this ForLoopNode
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * 
	 * @return end expression in this ForLoopNode
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * 
	 * @return step expression in this ForLoopNode
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(variable.asText() + " ");
		sb.append(startExpression.asText() + " ");
		sb.append(endExpression.asText() + " ");
		if (stepExpression != null) {
			sb.append(stepExpression.asText());
		}
		sb.append(" $}");

		return sb.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
	
}
