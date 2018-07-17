package hr.fer.zemris.java.tecaj.hw5.lexer;

/**
 * Represents type of token that lexer can generate.
 * 
 * @author Marin Jurjevic
 *
 */
public enum TokenType {
	/**
	 * attribut name token. It supports only 3 values: firstName, lastName and
	 * jmbag
	 */
	ATTRIBUT,

	/**
	 * sequence of characters to compare attribut with
	 */
	LITERAL,

	/**
	 * operator for comparison
	 */
	OPERATOR,

	/**
	 * end of this sequence of text
	 */
	EOF
}
