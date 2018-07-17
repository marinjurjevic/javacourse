package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * {@code Lexer} represents lexical analyser. It takes source text as input and
 * generates series of tokens. Lexer offers two methods for generating tokens,
 * one to generate and return token and one to return last generated token which
 * can be called multiple times.
 * 
 * @author Marin Jurjevic
 * @see Token
 */
public class Lexer {

	private final String tagStart = "{$";
	private final String tagEnd = "$}";

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
	private LexerState state;

	/**
	 * Constructs lexer with the input text ready to be evaluated.
	 * 
	 * @param text text to be analyzed by lexer 
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Null reference recieved. Cannot make new lexer.");
		}

		if (text.endsWith("\\")) {
			throw new LexerException("Invalid escape end.");
		}
		data = text.toCharArray();
		state = LexerState.TEXT;

	}

	/**
	 * Generates and returns new token from source text.
	 * 
	 * @return new generated token
	 */
	public Token nextToken() {
		// check for EOF
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("End of file reached. Cannot generate new token.");
		}

		if (currentIndex >= data.length) {
			return new Token(TokenType.EOF, null);
		}

		if (state == LexerState.TEXT) {

			if (new String(data, currentIndex, 2).equals(tagStart)) {
				token = new Token(TokenType.TAG_START, null);
				currentIndex += 2;
			} else {
				token = generateText();
			}

		} else { // TAG state

			// ignore whitespaces
			while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			}

			if (new String(data, currentIndex, 2).equals(tagEnd)) { // Check for
																	// tag end
				token = new Token(TokenType.TAG_END, null);
				currentIndex += 2;
			} else {
				if (data[currentIndex] == '=') { // empty tag name
					token = new Token(TokenType.TAG_NAME, "=");
					currentIndex++;
				} else if (Character.isLetter(data[currentIndex])) {
					token = generateTagNameOrVariable();
				} else if (Character.isDigit(data[currentIndex])) {
					token = generateNumber(false);
				} else if (isOperator(data[currentIndex])) { // operator or neg.
																// number
					if (!Character.isDigit(data[currentIndex + 1])) {
						token = new Token(TokenType.OPERATOR, data[currentIndex]);
					} else {
						currentIndex++;
						token = generateNumber(true);
					}
					currentIndex++;
				} else if (data[currentIndex] == '@') { // function
					token = generateFunction();
					if (token == null) {
						throw new LexerException("Error occured while parsing function");
					}
				} else if (data[currentIndex] == '\"') { // strings
					token = generateString();
				}
			}

		}

		return token;
	}

	/**
	 * Returns last generated token.
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets lexer to new state.
	 * 
	 * @param state
	 *            new state that lexer will move on
	 * @see LexerState
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State can not be null.");
		}
		this.state = state;
	}

	// additional methods

	/**
	 * Generates new token with normal text as its value.
	 * 
	 * @return token with text as value
	 */
	private Token generateText() {
		String s = "";
		while (currentIndex < data.length && !(data[currentIndex] == '{' && data[currentIndex + 1] == '$')) {

			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (data[currentIndex] != '{' && data[currentIndex] != '\\') {
					System.out.println(data[currentIndex-1]+""+data[currentIndex]);
					throw new LexerException("Invalid escape.");
				}

				s += data[currentIndex++];
			} else {
				s += data[currentIndex++];
			}

		}
		return new Token(TokenType.TEXT, s);
	}

	/**
	 * Checks is given character a valid operator. Valid operators are:
	 * +(addition), -(subtraction), / (division), *(multiplication), ^(power)
	 * 
	 * @param c
	 *            given character
	 * @return true if character is valid operator, false otherwise
	 */
	private boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '/' || c == '*' || c == '^') {
			return true;
		}

		return false;
	}

	/**
	 * Generates new token that will be tag name or variable.
	 * 
	 * @return new token representing variable or tag name
	 */
	private Token generateTagNameOrVariable() {
		String s = "";
		while (Character.isAlphabetic(data[currentIndex]) || data[currentIndex] == '_') {
			s += data[currentIndex++];
		}

		if (token.getType() == TokenType.TAG_START) {
			return new Token(TokenType.TAG_NAME, s);
		} else {
			return new Token(TokenType.VARIABLE, s);
		}
	}

	/**
	 * Generates new token representing number value. Numbers can be of type
	 * integer or double.
	 * 
	 * @return new token representing number.
	 */
	private Token generateNumber(boolean negative) {
		String s = negative ? "-" : "";
		int dot = 0;
		while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
			if (data[currentIndex] == '.') {
				dot++;
			}
			s += data[currentIndex++];
		}

		if (dot > 1) {
			throw new LexerException("Invalid number: multiple points found!");
		} else if (dot == 1) {
			return new Token(TokenType.CONST_DOUBLE, Double.parseDouble(s));
		} else {
			return new Token(TokenType.CONST_INT, Integer.parseInt(s));
		}
	}

	/**
	 * Generates new token representing function name
	 * 
	 * @return new token that represents function name.
	 */
	private Token generateFunction() {
		String s = "";
		currentIndex++;
		if (Character.isLetter(data[currentIndex])) {
			while (Character.isAlphabetic(data[currentIndex]) || data[currentIndex] == '_') {
				s += data[currentIndex++];
			}

			return new Token(TokenType.FUNCTION, s);
		}
		return null;
	}

	/**
	 * Generates new token representing a String.
	 * 
	 * @return new token that represents a string
	 */
	private Token generateString() {
		String s = "";

		currentIndex++;
		while (data[currentIndex] != '\"') {
			if (data[currentIndex] == '\\') { // escape
				currentIndex++;
				
				if (data[currentIndex] != '\"' && data[currentIndex] != '\\' ) {
					throw new LexerException("Invalid escape sequence in String");
				}
			}

			s += data[currentIndex++];
		}

		currentIndex++;
		return new Token(TokenType.STRING, s);
	}
}
