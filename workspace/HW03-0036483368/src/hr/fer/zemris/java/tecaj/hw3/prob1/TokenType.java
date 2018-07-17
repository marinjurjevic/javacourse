package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * TokenType is an enumeration representing type of a token.
 * 
 * @author Marin Jurjevic
 *
 */
public enum TokenType {
	/**
	 * End of file.
	 */
	EOF,

	/**
	 * Sequence of characters. Calling Character.IsLetter(char) will result in
	 * true.
	 */
	WORD,

	/**
	 * Number that can be represented by type Long.
	 */
	NUMBER,

	/**
	 * Every individual character except \r,\t and ' '. Blanks are ignored.
	 */
	SYMBOL
}
