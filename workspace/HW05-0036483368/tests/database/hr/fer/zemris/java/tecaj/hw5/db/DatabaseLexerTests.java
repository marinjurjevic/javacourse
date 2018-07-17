package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.lexer.TokenType;

public class DatabaseLexerTests {

	@Test
	public void testEquals_NotEqualCondition() {
		Lexer lexer = new Lexer("jmbag = \"0000000024\"");
		Lexer lexer2 = new Lexer("jmbag != \"0000000024\"");
		
		testLexerTokens(lexer,"jmbag","=","0000000024");
		testLexerTokens(lexer2,"jmbag","!=","0000000024");
	}
	
	@Test
	public void testGreaterThan_GreaterThanOrEqualCondition() {
		Lexer lexer = new Lexer("firstName > \"Ivana\"");
		Lexer lexer2 = new Lexer("jmbag >= \"0000000024\"");
		
		testLexerTokens(lexer,"firstName",">","Ivana");
		testLexerTokens(lexer2,"jmbag",">=","0000000024");
	}
	
	@Test
	public void testWildCardEqualOperator() {
		Lexer lexer = new Lexer("firstName LIKE \"Ivana\"");
		Lexer lexer2 = new Lexer("lastName LIKE \"Bo*\"");
		Lexer lexer3 = new Lexer("lastName LIKE \"*ić\"");
		Lexer lexer4 = new Lexer("lastName LIKE \"Bo*ić\"");
		
		testLexerTokens(lexer,"firstName","LIKE","Ivana");
		testLexerTokens(lexer2,"lastName","LIKE","Bo*");
		testLexerTokens(lexer3,"lastName","LIKE","*ić");
		testLexerTokens(lexer4,"lastName","LIKE","Bo*ić");
	}
	
	@Test
	public void testLessThan_LessThanOrEqualOperator() {
		Lexer lexer = new Lexer("firstName < \"Ivana\"");
		Lexer lexer2 = new Lexer("jmbag <= \"0000000024\"");
		
		testLexerTokens(lexer,"firstName","<","Ivana");
		testLexerTokens(lexer2,"jmbag","<=","0000000024");
	}
	
	@Test
	public void testMultipleConditions() {
		Lexer lexer = new Lexer("firstName < \"Ivana\" 	AND 	lastName LIKE \"*ić\" ");
		
		testLexerTokens(lexer,"firstName","<","Ivana");
	
		assertEquals("Expected AND", "AND", lexer.nextToken().getValue().toUpperCase());
		
		testLexerTokens(lexer,"lastName","LIKE","*ić");
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidInput() {
		Lexer lexer = new Lexer("jmbag < 0000000050 ");
		
		testLexerTokens(lexer,"jmbag","<","0000000050");		
	}
	
	/**
	 * Help generalized method for testing out lexer token generation. Lexer is expected
	 * to generate three valid tokens in exact order: attribut, operator and literal 
	 * type of token.
	 * @param lexer filled lexer ready to generate tokens
	 * @param attr expeccted attribut value
	 * @param op expeccted operator value
	 * @param literal expeccted literal value
	 */
	
	private static void testLexerTokens(Lexer lexer, String attr, String op, String literal){
		
		assertEquals("Expected true", TokenType.ATTRIBUT, lexer.nextToken().getType());
		assertEquals("Expected true", attr, lexer.getToken().getValue());
		
		assertEquals("Expected true", TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals("Expected true", op, lexer.getToken().getValue());
		
		assertEquals("Expected true", TokenType.LITERAL, lexer.nextToken().getType());
		assertEquals("Expected true", literal, lexer.getToken().getValue());
	}
}
