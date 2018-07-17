package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Implementation of Visitor pattern for decoupling operations from domain objects.
 * @author Marin Jurjevic
 *
 */
public interface INodeVisitor {
	/**
	 * 
	 * Called when visitor visits <tt>TextNode</tt>
	 * @param node visited node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * 
	 * Called when visitor visits <tt>ForLoopNode</tt>
	 * @param node visited node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * 
	 * Called when visitor visits <tt>EchoNode</tt>
	 * @param node visited node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * 
	 * Called when visitor visits <tt>DocumentNode</tt>
	 * @param node visited node
	 */
	public void visitDocumentNode(DocumentNode node);
}