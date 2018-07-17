package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.operators.EqualsCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.GreaterThanCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.GreaterThanOrEqualCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.operators.LessThanCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.LessThanOrEqualCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.NotEqualCondition;
import hr.fer.zemris.java.tecaj.hw5.operators.WildCardEqualsCondition;

public class OperatorTests {

	@Test
	public void testEqualsCondition() {
		IComparisonOperator op = new EqualsCondition();
		String s1 = "Ana";
		
		assertEquals("Expected true", true, op.satisfied("Ana", s1));
		assertEquals("Expected true", false, op.satisfied("Pero", s1));
	}
	
	@Test 
	public void testGreaterThanCondition() {
		IComparisonOperator op = new GreaterThanCondition();
		String s1 = "Pero";
		
		assertEquals("Expected true", true, op.satisfied("Sanja", s1));
		assertEquals("Expected true", false, op.satisfied("Marko", s1));
	}
	
	@Test 
	public void testGreaterThanOrEqualCondition() {
		IComparisonOperator op = new GreaterThanOrEqualCondition();
		String s1 = "Pero";
		
		assertEquals("Expected true", true, op.satisfied("Sanja", s1));
		assertEquals("Expected true", false, op.satisfied("Marko", s1));
		
		assertEquals("Expected true", true, op.satisfied("Pero", s1));
	}
	
	@Test 
	public void testLessThanCondition() {
		IComparisonOperator op = new LessThanCondition();
		String s1 = "Pero";
		
		assertEquals("Expected true", true, op.satisfied("Ana", s1));
		assertEquals("Expected true", false, op.satisfied("Sanja", s1));
	}
	
	@Test 
	public void testLessThanOrEqualCondition() {
		IComparisonOperator op = new LessThanOrEqualCondition();
		String s1 = "Pero";
		
		assertEquals("Expected true", true, op.satisfied("Ana", s1));
		assertEquals("Expected true", false, op.satisfied("Sanja", s1));
		
		assertEquals("Expected true", true, op.satisfied("Pero", s1));
	}
	
	@Test 
	public void testNotEqualCondition() {
		IComparisonOperator op = new NotEqualCondition();
		String s1 = "Pero";
		
		assertEquals("Expected true", true, op.satisfied("Ana", s1));
		assertEquals("Expected true", false, op.satisfied("Pero", s1));
	}
	
	@Test 
	public void testWildCardEqualsCondition() {
		IComparisonOperator op = new WildCardEqualsCondition();
		String wc1 = "Perić";
		String wc2 = "*ić";
		String wc3 = "Per*";
		
		assertEquals("Expected true", true, op.satisfied("Perić", wc1));
		assertEquals("Expected true", false, op.satisfied("Anić", wc1));
		
		assertEquals("Expected true", true, op.satisfied("Perić", wc2));
		assertEquals("Expected true", true, op.satisfied("Tolušić", wc2));
		assertEquals("Expected true", false, op.satisfied("Lokin", wc2));
		
		assertEquals("Expected true", true, op.satisfied("Perić", wc3));
		assertEquals("Expected true", true, op.satisfied("Perušić", wc3));
		assertEquals("Expected true", false, op.satisfied("Anić", wc3));
	}

	
}
