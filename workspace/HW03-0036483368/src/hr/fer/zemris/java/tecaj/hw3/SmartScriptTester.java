package hr.fer.zemris.java.tecaj.hw3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Tester class for testing parser.
 * 
 * @author Marin Jurjevic
 *
 */
public class SmartScriptTester {

	/**
	 * Main method that runs this program.
	 * 
	 * @param args
	 *            path to the source text
	 */
	public static void main(String[] args) {
		String docBody;
		SmartScriptParser parser = null;

		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);

			parser = new SmartScriptParser(docBody);
		} catch (LexerException e) {
			System.out.println("Lexer Exception!!");
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!:");
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Unable to read text file!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		//System.out.println(document.numberOfChildren());
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocument2Body = createOriginalDocumentBody(document2);
		//System.out.println("\nIs original document equal after reparsing it: "
		//		+ originalDocument2Body.equals(originalDocumentBody));
	}

	/**
	 * Reconstructs original source text from syntax tree that parser generated.
	 * 
	 * @param document
	 *            root node in hierarchy of syntax tree
	 * @return original source text
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder sb = new StringBuilder();

		DFS(document, sb);

		return sb.toString();
	}

	/**
	 * Static method to search the tree using dept-first search algorithm. It
	 * appends every node create a String containing source text.
	 * 
	 * @param node
	 *            root node of the tree
	 * @param sb
	 *            StringBuilder object to append nodes
	 */
	public static void DFS(Node node, StringBuilder sb) {
		if (node == null) {
			return;
		}

		if (!(node instanceof DocumentNode)) {
			sb.append(node.toString());
		}

		for (int i = 0, children = node.numberOfChildren(); i < children; i++) {
			DFS(node.getChild(i), sb);
		}

		if (node instanceof ForLoopNode) {
			sb.append("{$END$}");
		}

	}

}
