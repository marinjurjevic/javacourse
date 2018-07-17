package hr.fer.zemris.java.tecaj.hw5.lexer;

/**
 * Lexer is a simple class that helps analyzing user input for executing queries
 * on loaded database. Lexer can only generate four type of tokens as listed in
 * {@link TokenType} enumeration.
 * 
 * @author Marin Jurjevic
 *
 */
public class Lexer {

	/**
	 * Array of characters to be evauluated by analyzer.
	 */
	private char[] data;

	/**
	 * Current generated token in series.
	 */
	private Token token;

	/**
	 * Current index of character to be evaluated.
	 */
	private int currentIndex;

	/**
	 * Controls in what state lexer is currently at.
	 */

	/**
	 * Creates new lexer with String of data as parameter.
	 * 
	 * @param data
	 *            string of data to lexically analyze
	 */
	public Lexer(String data) {
		this.data = data.toCharArray();
	}

	/**
	 * Generates next valid token for query.
	 * 
	 * @return next generated token
	 */
	public Token nextToken() {
		String s = "";

		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("End of file reached. Cannot generate new token.");
		}

		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (Character.isLetter(data[currentIndex])) {
			while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				s += data[currentIndex++];
			}

			if (s.equals("LIKE") || s.equalsIgnoreCase("AND")) {
				token = new Token(TokenType.OPERATOR, s);
			} else {
				token = new Token(TokenType.ATTRIBUT, s);
			}

		} else if (isOperator(data[currentIndex])) {
			while (currentIndex < data.length && isOperator(data[currentIndex])) {
				s += data[currentIndex++];
			}

			token = new Token(TokenType.OPERATOR, s);
		} else if (data[currentIndex] == '\"') {

			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				s += data[currentIndex++];
			}
			currentIndex++;
			token = new Token(TokenType.LITERAL, s);
		} else {
			throw new IllegalArgumentException("Invalid input. Invalid token!");
		}

		return token;
	}

	/**
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Checks is given character a valid operator for query syntax.
	 * 
	 * @param c character to check
	 * @return true if characters belongs to supported query operators, false
	 *         otherwise
	 */
	private boolean isOperator(char c) {
		if ("<>!=".indexOf(c) == -1) {
			return false;
		}

		return true;
	}
}
