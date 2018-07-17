package hr.fer.zemris.java.tecaj.hw3.prob1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		if (checkNonLong(text) == true) {
			throw new LexerException("Number in sequence will not fit in long type.");
		}

		if (text.endsWith("\\")) {
			throw new LexerException("Invalid escape end.");
		}
		data = text.toCharArray();
		state = LexerState.BASIC;

	}

	/**
	 * Checks if there are invalid number sequences that can't be parsed into
	 * type {@code Long}.
	 * 
	 * @param text
	 *            input text to evaluate
	 * @return true if input is ok, false otherwise
	 */
	private boolean checkNonLong(String text) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(text);
		while (m.find()) {
			try {
				Long.parseLong(m.group());
			} catch (NumberFormatException n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates and returns new token from source text.
	 * 
	 * @return new generated token
	 */
	public Token nextToken() {
		// ignore whitespaces
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}

		// check for EOF
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("End of file reached. Cannot generate new token.");
		}

		// Basic mode
		if (state == LexerState.BASIC) {
			if (currentIndex == data.length) {
				token = new Token(TokenType.EOF, null);
			} else if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				token = generateWord();
			} else if (Character.isDigit(data[currentIndex])) {
				token = generateDigit();
			} else {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			}
		} else { // Extended mode
			if (currentIndex == data.length) {
				token = new Token(TokenType.EOF, null);
			} else if (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '\\') {
				String s = "";
				while (currentIndex < data.length
						&& (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '\\')) {
					s += data[currentIndex++];
				}

				s.trim();
				token = new Token(TokenType.WORD, s);
			} else {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
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

	// help methods for generating tokens

	/**
	 * Generates new token of type {@code WORD}
	 * 
	 * @return new WORD token
	 */
	private Token generateWord() {
		String s = "";

		while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {

			// check for escape
			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (!Character.isDigit(data[currentIndex]) && data[currentIndex] != '\\') {
					throw new LexerException("Invalid escape.");
				}
				s += data[currentIndex];
				currentIndex++;

			} else {
				s += data[currentIndex++];
			}

		}

		return new Token(TokenType.WORD, s);
	}

	/**
	 * Generates new token of type {@code NUMBER}
	 * 
	 * @return new NUMBER token
	 */
	private Token generateDigit() {
		String s = "";
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			s += data[currentIndex++];
		}

		return new Token(TokenType.NUMBER, Long.parseLong(s));

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
}
