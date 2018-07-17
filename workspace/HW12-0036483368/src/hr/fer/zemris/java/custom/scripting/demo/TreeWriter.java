package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Test class for testing implementation of INodeVisitor pattern. 
 * @author Marin Jurjevic
 *
 */
public class TreeWriter {

	/**
	 * Program entry point.
	 * @param args path to the smart script file
	 * @throws IOException if an error while reading script occurs
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1){
			System.out.println("I expected path to the smart script file.");
			return;
		}
		
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	/**
	 * Simple implementation of INodeVisitor interface. WriteVisitor is traversing
	 * parsed tree and try to reconstructs script approximately. (as some information are
	 * lost in parsing process)
	 * @author Marin Jurjevic
	 *
	 */
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
			iterateChildren(node);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			iterateChildren(node);
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
			iterateChildren(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			iterateChildren(node);
		}
		
		/**
		 * Help method for iterading all node children if he has any.
		 * @param node node whose children will be iterated
		 */
		private void iterateChildren(Node node){
			for(int i = 0, n=node.numberOfChildren(); i<n; i++){
				node.getChild(i).accept(this);
			}
		}
	}
}
