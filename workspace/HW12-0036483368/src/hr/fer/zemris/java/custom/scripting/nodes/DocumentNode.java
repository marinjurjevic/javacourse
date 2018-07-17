package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * DocumentNode represents an entire document.
 * 
 * @author Marin Jurjevic
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
