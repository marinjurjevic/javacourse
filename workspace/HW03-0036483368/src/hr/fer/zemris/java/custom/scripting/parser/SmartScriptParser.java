package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * {@code SmartScriptParser} parses a piece of text. It has only one constructor
 * that takes a piece of text as argument. Parser delegates text to a
 * {@code Lexer} for processing and analysing. Parser takes back processed data
 * (tokens) from lexer and constructs a document model tree.
 * 
 * @author Marin Jurjevic
 *
 */
public class SmartScriptParser {

	private Lexer lexer;
	private DocumentNode documentNode;
	private ObjectStack stack;

	/**
	 * Creates a new instance of parser which upon initialization parses given
	 * text.
	 * 
	 * @param text text to be analyzed and parsed
	 */
	public SmartScriptParser(String text) {
		lexer = new Lexer(text);
		stack = new ObjectStack();
		parse();
	}

	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	private void parse() {
		documentNode = new DocumentNode();
		stack.push(documentNode);

		while (lexer.nextToken().getType() != TokenType.EOF) {

			if (lexer.getToken().getType() == TokenType.TEXT) { // text
				((Node) stack.peek()).addChildNode(new TextNode((String) lexer.getToken().getValue()));
			} else if (lexer.getToken().getType() == TokenType.TAG_START) {
				lexer.setState(LexerState.TAG);
				if (lexer.nextToken().getType() == TokenType.TAG_NAME) {
					if (lexer.getToken().getValue() == "=") {
						addEchoNode();
					} else if (((String) lexer.getToken().getValue()).equalsIgnoreCase("FOR")) {
						Node forLoopNode = getForLoopNode();
						((Node) stack.peek()).addChildNode(forLoopNode);
						stack.push(forLoopNode);
					} else if (((String) lexer.getToken().getValue()).equalsIgnoreCase("END")) {
						stack.pop();
					}

				} else {
					throw new SmartScriptParserException("Invalid tag name!");
				}
			}

			if (lexer.getToken().getType() == TokenType.TAG_END) {
				lexer.setState(LexerState.TEXT);
			}
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("Invalid stack state.");
		}

	}

	/**
	 * Creates and adds new EchoNode to the tree.
	 */
	private void addEchoNode() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		while (lexer.nextToken().getType() != TokenType.TAG_END) {
			switch (lexer.getToken().getType()) {
			case VARIABLE:
				array.add(new ElementVariable((String) lexer.getToken().getValue()));
				break;
			case FUNCTION:
				array.add(new ElementFunction((String) lexer.getToken().getValue()));
				break;
			case CONST_DOUBLE:
				array.add(new ElementConstantDouble((Double) lexer.getToken().getValue()));
				break;
			case CONST_INT:
				array.add(new ElementConstantInteger((Integer) lexer.getToken().getValue()));
				break;
			case OPERATOR:
				array.add(new ElementOperator(lexer.getToken().getValue().toString()));
				break;
			case STRING:
				array.add(new ElementString((String) lexer.getToken().getValue()));
				break;
			default:
				throw new SmartScriptParserException("An error occured while parsing echo tag");
			}
		}

		Element[] elements = new Element[array.size()];
		for (int i = 0, size = array.size(); i < size; i++) {
			elements[i] = (Element) array.get(i);
		}

		((Node) stack.peek()).addChildNode(new EchoNode(elements));
	}

	private Node getForLoopNode() {
		Element[] elements = new Element[4];
		int i = 0;

		while (lexer.nextToken().getType() != TokenType.TAG_END) {

			if (i > 3) {
				throw new SmartScriptParserException("Invalid number of arguments in FOR loop!");
			}

			if (i == 0) {
				if (lexer.getToken().getType() != TokenType.VARIABLE) {
					throw new SmartScriptParserException("Invalid variable in for loop!");
				}
				elements[i] = new ElementVariable((String) lexer.getToken().getValue());
			} else {
				switch (lexer.getToken().getType()) {
				case VARIABLE:
					elements[i] = new ElementVariable((String) lexer.getToken().getValue());
					break;
				case CONST_INT:
					elements[i] = new ElementConstantInteger((Integer) lexer.getToken().getValue());
					break;
				case CONST_DOUBLE:
					elements[i] = new ElementConstantDouble((Double) lexer.getToken().getValue());
					break;
				case STRING:
					elements[i] = new ElementString((String) lexer.getToken().getValue());
					break;
				default:
					throw new SmartScriptParserException("Invalid expression in for loop!");
				}

			}
			i++;
		}

		if (i <= 2) {
			throw new SmartScriptParserException("Invalid number of arguments in FOR loop!");
		}

		return new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2], elements[3]);
	}

}
