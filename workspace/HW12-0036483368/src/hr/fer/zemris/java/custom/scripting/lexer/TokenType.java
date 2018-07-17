package hr.fer.zemris.java.custom.scripting.lexer;

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
	 * Represents normal plain text.
	 */
	TEXT,

	/**
	 * Sequence of characters.
	 */
	STRING,

	/**
	 * Constant integer number.
	 */
	CONST_INT,

	/**
	 * Constant double number.
	 */
	CONST_DOUBLE,

	/**
	 * Represents variable.
	 */
	FUNCTION,

	/**
	 * Represents operator.
	 */
	OPERATOR,

	/**
	 * Represents variable.
	 */
	VARIABLE,

	/**
	 * Tag start
	 */

	TAG_START,

	/**
	 * Tag end
	 */

	TAG_END,

	/**
	 * Tag name
	 */

	TAG_NAME
}
