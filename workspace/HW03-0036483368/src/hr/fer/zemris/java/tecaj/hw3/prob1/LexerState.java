package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * LexerState enumeration describes in what state lexer is analysing.
 * 
 * @author Marin Jurjevic
 *
 */
public enum LexerState {
	/**
	 * Default state.
	 */
	BASIC,

	/**
	 * Extended state which allows lexer to analyze text by new set of rules.
	 */
	EXTENDED
}
