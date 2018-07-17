package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Wrapper class for String, Integer and Double objects. This clas keeps one of
 * supported class objects as value and if that value represents a number it
 * offers some basic math operations on them.
 * 
 * Constructor can accept a null value as argument, but notice that every such
 * call will result in new Integer with value of 0 stored inside as default
 * value.
 * 
 * @author Marin Jurjevic
 *
 */
public class ValueWrapper {

	/**
	 * private reference to stored object inside this wrapper
	 */
	private Object value;

	/**
	 * Creates new value wrapper. Null value can be acceoted but it will be
	 * converted to new <tt>Integer</tt> object with value of 0.
	 * 
	 * @param value
	 *            only instance of Integer, Double or String class
	 */
	public ValueWrapper(Object value) {
		this.value = checkValidClass(value);
	}

	/**
	 * Returns currently stored value in this wrapper.
	 * 
	 * @return stored value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets new value in this wrapper if it matches valid classes.
	 * 
	 * @param value
	 *            new value in wrapper
	 */
	public void setValue(Object value) {
		this.value = checkValidClass(value);
	}

	/**
	 * Checks if given object is instance of valid class for this wrapper.
	 * Method returns new Integer(0) if passed object is null. If passed object
	 * is String, it tries to parse it to number.
	 * 
	 * @param o
	 *            object to be checked
	 * @throws RuntimeException
	 *             if object is not a valid class
	 * @return modified object if it is null reference according to description, or same object
	 */
	private static Object checkValidClass(Object o) {
		if (o == null) {
			return new Integer(0);
		}

		if (o.getClass() != String.class && o.getClass() != Integer.class && o.getClass() != Double.class) {
			throw new RuntimeException("Unsuported class in wrapper!");
		}

		return o;
	}

	/**
	 * Increases current value in wrapper by given amount.
	 * 
	 * @param incValue
	 *            amount to add to the current value
	 */
	public void increment(Object incValue) {
		incValue = checkValidClass(incValue);
		performOperation(incValue, OperationType.INC);

	}

	/**
	 * Decreases current value in wrapper by given amount.
	 * 
	 * @param decValue
	 *            amount to decrease current value
	 */
	public void decrement(Object decValue) {
		decValue = checkValidClass(decValue);
		performOperation(decValue, OperationType.DEC);
	}

	/**
	 * Multiplies current value in wrapper by given number.
	 * 
	 * @param mulValue
	 *            number to multiply current value with
	 */
	public void multiply(Object mulValue) {
		mulValue = checkValidClass(mulValue);
		performOperation(mulValue, OperationType.MUL);
	}

	/**
	 * Divides current value in wrapper by given number.
	 * 
	 * @param divValue
	 *            number to divide current value with
	 */
	public void divide(Object divValue) {
		divValue = checkValidClass(divValue);
		performOperation(divValue, OperationType.DIV);
	}

	/**
	 * Tries to parse given object to double or integer.
	 * 
	 * @param o
	 *            object that is type of String
	 * @return number represented by this string
	 * @throws NumberFormatException
	 *             if string is not parsable to number
	 */
	private Number parseString(Object o) {
		String string = (String) o;

		if (string.contains(".") || string.contains("E") || string.contains("e")) {
			return Double.parseDouble(string);
		}

		return Integer.parseInt(string);

	}

	/**
	 * Performs operation specified by {@link OperationType} on current value.
	 * Arguments of operations are current value and given operand.
	 * 
	 * @param operand
	 *            argument of operation
	 * @param opType
	 *            type of operation to perform
	 */
	private void performOperation(Object operand, OperationType opType) {

		if (value.getClass() == String.class) {
			value = parseString(value);
		}
		if (operand.getClass() == String.class) {
			operand = parseString(operand);
		}

		// at this point both curr value and operand are Double or Integer
		if (value.getClass() == Double.class || operand.getClass() == Double.class) {
			Double operandDouble;
			operandDouble = operand.getClass() == Integer.class ? ((Integer) operand).doubleValue() : (Double) operand;

			switch (opType) {
			case INC:
				value = getDoubleResult(operandDouble, (x, y) -> x + y);
				break;
			case DEC:
				value = getDoubleResult(operandDouble, (x, y) -> x - y);
				break;
			case MUL:
				value = getDoubleResult(operandDouble, (x, y) -> x * y);
				break;
			case DIV:
				value = getDoubleResult(operandDouble, (x, y) -> x / y);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		} else {
			switch (opType) {
			case INC:
				value = getIntegerResult((Integer) operand, (x, y) -> x + y);
				break;
			case DEC:
				value = getIntegerResult((Integer) operand, (x, y) -> x - y);
				break;
			case MUL:
				value = getIntegerResult((Integer) operand, (x, y) -> x * y);
				break;
			case DIV:
				value = getIntegerResult((Integer) operand, (x, y) -> x / y);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

	}

	/**
	 * Help method for performing operaion if either one of operands is Double.
	 * 
	 * @param operand
	 *            operand
	 * @param operation
	 *            type of operation to perform
	 * @return new value calculated by this operation
	 */
	private Double getDoubleResult(Double operand, BiFunction<Double, Double, Double> operation) {
		Double oldValue;
		oldValue = value.getClass() == Integer.class ? ((Integer) value).doubleValue() : (Double) value;
		return operation.apply(oldValue, operand);
	}

	/**
	 * Help method for performing operaion if neither one of operands is Double.
	 * 
	 * @param operand
	 *            operand
	 * @param operation
	 *            type of operation to perform
	 * @return new value calculated by this operation
	 */
	private Integer getIntegerResult(Integer operand, BiFunction<Integer, Integer, Integer> operation) {
		return operation.apply((Integer) value, operand);
	}

	/**
	 * Compares current value with specified value as argument of this method.
	 * 
	 * @param withValue
	 *            value current value will be compared to
	 * @return positive integer if current value is bigger than given value,
	 *         negative if given value is bigger than current value, and 0 if
	 *         they are equal
	 */
	public int numCompare(Object withValue) {
		withValue = checkValidClass(withValue);

		Double valueCopy;
		Double withValueCopy;

		if (value.getClass() != String.class) {
			if (value.getClass() == Integer.class) {
				valueCopy = ((Integer) value).doubleValue();
			} else {
				valueCopy = (Double) value;
			}
		} else {
			valueCopy = Double.parseDouble((String) value);
		}

		if (withValue.getClass() != String.class) {
			if (withValue.getClass() == Integer.class) {
				withValueCopy = ((Integer) withValue).doubleValue();
			} else {
				withValueCopy = (Double) withValue;
				;
			}
		} else {
			withValueCopy = Double.parseDouble((String) withValue);
		}

		return Double.compare(valueCopy, withValueCopy);
	}
}
