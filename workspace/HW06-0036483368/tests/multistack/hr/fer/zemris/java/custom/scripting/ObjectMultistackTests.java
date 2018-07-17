package hr.fer.zemris.java.custom.scripting;

import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Series of tests for testing <tt>ObjectMultistack</tt> and <tt>ValueWrapper</tt>
 * class working properly.
 * @author Marin Jurjevic
 *
 */
public class ObjectMultistackTests {

	@Test
	public void testPushingOnMultistack() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("brojevi", new ValueWrapper(Integer.valueOf(1)));
		multi.push("brojevi", new ValueWrapper(Integer.valueOf(2)));
		multi.push("brojevi", new ValueWrapper(Integer.valueOf(3)));
		
		assertEquals("Expected 3", 3 , multi.peek("brojevi").getValue());
	}
	
	@Test (expected = RuntimeException.class)
	public void testPushingInvalidClassOnMultistack() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("brojevi", new ValueWrapper(new InputStreamReader(System.in)));

	}
	
	@Test (expected = RuntimeException.class)
	public void testPoppingEmptyMultistack() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("brojevi", new ValueWrapper(Integer.valueOf(1)));
		multi.pop("brojevi");
		multi.pop("brojevi");
	}
	
	@Test (expected = RuntimeException.class)
	public void testPeekingEmptyMultistack() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("brojevi", new ValueWrapper(Integer.valueOf(1)));
		multi.pop("brojevi");
		multi.peek("brojevi");
	}

	// --------------------------
	// |ValueWrapper operations |
	// --------------------------
	
	// Current value: Integer
	@Test 
	public void testIntegerIncrement() {
		ValueWrapper value = new ValueWrapper(Integer.valueOf(1));
		
		value.increment(5);
		assertEquals("Expecting 6", 6, value.getValue());
		
		value.increment(5.5);
		assertEquals("Expecting 11.5", 11.5, value.getValue());
		
		value.setValue(10);
		value.increment("10");
		assertEquals("Expecting 20", 20, value.getValue());
		
		value.increment("30.0");
		assertEquals("Expecting 50.0", 50.0, value.getValue());
	}
	
	@Test 
	public void testIntegerDecrement() {
		ValueWrapper value = new ValueWrapper(Integer.valueOf(100));
		
		value.decrement(50);
		assertEquals("Expecting 50", 50, value.getValue());
		
		value.decrement(15.90);
		assertEquals("Expecting 34.1", 34.1, value.getValue());
		
		value.setValue(10);
		value.decrement("10");
		assertEquals("Expecting 0", 0, value.getValue());
	}
	
	@Test 
	public void testIntegerMultiplication() {
		ValueWrapper value = new ValueWrapper(Integer.valueOf(8));
		
		value.multiply(2);
		assertEquals("Expecting 16", 16, value.getValue());
		
		value.multiply(5.0);
		assertEquals("Expecting 80.0", 80.0, value.getValue());
		
		value.setValue(10);
		value.multiply("10");
		assertEquals("Expecting 100", 100, value.getValue());
		
		value.multiply("1.0");
		assertEquals("Expecting 100.0", 100.0, value.getValue());
	}
	
	@Test 
	public void testIntegerDivision() {
		ValueWrapper value = new ValueWrapper(Integer.valueOf(128));
		
		value.divide(16);
		assertEquals("Expecting 8", 8, value.getValue());
		
		value.divide(2.0);
		assertEquals("Expecting 4.0", 4.0, value.getValue());
		
		value.setValue(100);
		value.divide("5");
		assertEquals("Expecting 20", 20, value.getValue());
		
		value.divide("4.0");
		assertEquals("Expecting 5.0", 5.0, value.getValue());
	}
	
	@Test 
	public void testIntegerCompare() {
		ValueWrapper value = new ValueWrapper(Integer.valueOf(128));
		
		assertEquals("Expecting true", true, value.numCompare(15.75)>0);
		assertEquals("Expecting true", true, value.numCompare(100)>0);
		
		assertEquals("Expecting true", true, value.numCompare("100")>0);
		assertEquals("Expecting true", true, value.numCompare("50.5")>0);
		
		assertEquals("Expecting true", true, value.numCompare("128")==0);
		assertEquals("Expecting true", true, value.numCompare("128.0")==0);
		
		
	}
	
	// current value: Double
	
	
	@Test 
	public void testDoubleIncrement() {
		ValueWrapper value = new ValueWrapper(Double.valueOf(5));
		
		value.increment(5);
		assertEquals("Expecting 10.0", 10.0, value.getValue());
		
		value.increment(5.5);
		assertEquals("Expecting 15.5", 15.5, value.getValue());
		
		value.increment("30.5");
		assertEquals("Expecting 46.0", 46.0, value.getValue());
		
		value.increment("10");
		assertEquals("Expecting 56.0", 56.0, value.getValue());
	}
	
	@Test 
	public void testDoubleDecrement() {
		ValueWrapper value = new ValueWrapper(Double.valueOf(100.0));
		
		value.decrement(50);
		assertEquals("Expecting 50.0", 50.0, value.getValue());
		
		value.decrement("17.5");
		assertEquals("Expecting 32.5", 32.5, value.getValue());
		
		value.decrement("10");
		assertEquals("Expecting 22.5", 22.5, value.getValue());
	}
	
	@Test 
	public void testDoubleMultiplication() {
		ValueWrapper value = new ValueWrapper(Double.valueOf(15.0));
		
		value.multiply(2);
		assertEquals("Expecting 30.0", 30.0, value.getValue());
		
		value.multiply(5.0);
		assertEquals("Expecting 150.0", 150.0, value.getValue());
		
		value.multiply("10");
		assertEquals("Expecting 1500.0", 1500.0, value.getValue());
		
		value.multiply("1.0");
		assertEquals("Expecting 1500.0", 1500.0, value.getValue());
	}
	
	@Test 
	public void testDoubleDivision() {
		ValueWrapper value = new ValueWrapper(Double.valueOf(128));
		
		value.divide(16);
		assertEquals("Expecting 8.0", 8.0, value.getValue());
		
		value.divide(2.0);
		assertEquals("Expecting 4.0", 4.0, value.getValue());
		
		value.divide("5");
		assertEquals("Expecting 0.8", 0.8, value.getValue());
		
		value.divide("4.0");
		assertEquals("Expecting 0.2", 0.2, value.getValue());
	}
	
	// current value : String
	
	@Test 
	public void testStringIncrement() {
		ValueWrapper valueInt = new ValueWrapper("5");
		
		valueInt.increment(5);
		assertEquals("Expecting 10", 10, valueInt.getValue());
		
		valueInt.increment(5.5);
		assertEquals("Expecting 15.5", 15.5, valueInt.getValue());
		
		ValueWrapper valueDouble = new ValueWrapper("5.0");
		
		valueDouble.increment(5);
		assertEquals("Expecting 10.0", 10.0, valueDouble.getValue());
	}
	
	@Test 
	public void testStringDecrement() {
		ValueWrapper valueInt = new ValueWrapper("15");
		
		valueInt.decrement(5);
		assertEquals("Expecting 10", 10, valueInt.getValue());
		
		valueInt.decrement(5.5);
		assertEquals("Expecting 4.5", 4.5, valueInt.getValue());
		
		ValueWrapper valueDouble = new ValueWrapper("15.0");
		
		valueDouble.decrement(5);
		assertEquals("Expecting 10.0", 10.0, valueDouble.getValue());
	}
	
	@Test 
	public void testStringMultiplication() {
	ValueWrapper valueInt = new ValueWrapper("15");
		
		valueInt.multiply(5);
		assertEquals("Expecting 75", 75, valueInt.getValue());
		
		valueInt.multiply(4.0);
		assertEquals("Expecting 300.0", 300.0, valueInt.getValue());
		
		ValueWrapper valueDouble = new ValueWrapper("15.0");
		
		valueDouble.multiply(5);
		assertEquals("Expecting 75.0", 75.0, valueDouble.getValue());
	}
	
	@Test 
	public void testStringDivision() {
		ValueWrapper valueInt = new ValueWrapper("15");
		
		valueInt.divide(3);
		assertEquals("Expecting 5", 5, valueInt.getValue());
		
		valueInt.divide(4.0);
		assertEquals("Expecting 1.25", 1.25, valueInt.getValue());
		
		ValueWrapper valueDouble = new ValueWrapper("15.0");
		
		valueDouble.divide(5);
		assertEquals("Expecting 3.0", 3.0, valueDouble.getValue());
	}
	
	@Test 
	public void testStringCompare() {
		ValueWrapper value = new ValueWrapper("128");
		
		assertEquals("Expecting true", true, value.numCompare(15.75)>0);
		assertEquals("Expecting true", true, value.numCompare(100)>0);
		
		assertEquals("Expecting true", true, value.numCompare("100")>0);
		assertEquals("Expecting true", true, value.numCompare("50.5")>0);
		
		assertEquals("Expecting true", true, value.numCompare("128")==0);
		assertEquals("Expecting true", true, value.numCompare("128.0")==0);
		
		
	}
	
	// some invalid strings
	
	@Test (expected = NumberFormatException.class)
	public void testInvalidString() {
		ValueWrapper value = new ValueWrapper(5);
		
		value.increment("dksaod");
	}
	
	@Test (expected = NumberFormatException.class)
	public void testInvalidString2() {
		ValueWrapper value = new ValueWrapper(5);
		
		value.increment("5.0.5");
	}

}
