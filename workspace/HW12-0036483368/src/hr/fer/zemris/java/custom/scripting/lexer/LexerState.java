package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * LexerState enumeration describes in what state lexer is analysing.
 * 
 * @author Marin Jurjevic
 *
 */
public enum LexerState {
	/**
	 * Default state. Reads normal text.
	 */
	TEXT,

	/**
	 * Extended state which allows lexer to analyze tags.
	 */
	TAG
}
