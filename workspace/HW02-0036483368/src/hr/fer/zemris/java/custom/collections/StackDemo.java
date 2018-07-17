package hr.fer.zemris.java.custom.collections;

/**
 * StackDemo is a simple command-line application which accepts a single
 * command-line argument on evaluation. Expression must be in postfix
 * representation.
 */
public class StackDemo {

	public static void main(String[] args) {
		
		boolean error = false;
		ObjectStack stack = new ObjectStack();
		String arg[] = args[0].split(" ");
		
		
		for (int i = 0, size = arg.length; i < size; i++) {
			int value;	//value to push on stack
			char operator;	
			int op1;	// value we pop, our first operand
			int op2;	// value we pop, our second operand
			
			try {
				value = Integer.parseInt(arg[i]);
				stack.push(value);
			} catch (NumberFormatException e) {
				try{
					if(arg[i].length() != 1){
						throw new IllegalArgumentException("Expression is invalid!");
					}
					
					operator = arg[i].charAt(0);
					op2 = (Integer) stack.pop();
					op1 = (Integer) stack.pop();
	
					switch (operator) {
					case '+':
						stack.push(op1 + op2);
						break;
					case '-':
						stack.push(op1 - op2);
						break;
					case '/':
						if (op2 == 0) {
							throw new ArithmeticException("Dividing by zero!");
						}
						stack.push(op1 / op2);
						break;
					case '*':
						stack.push(op1 * op2);
						break;
					case '%':
						stack.push(op1 % op2);
						break;
					}
				} catch (ArithmeticException e2) {
					System.out.println(e2.getMessage());
					error = true;
				} catch (IllegalArgumentException e2) {
					System.out.println(e2.getMessage());
					error = true;
				}
			}
			
			if(error){
				break;
			}
			// uncomment line below to track how does stack change each
			// iteration
			// stack.print();
		}

		if (stack.size() != 1 || error ) {
			System.out.println("An error has occured. Program terminated");
		} else {
			System.out.println("Your expression: " + args[0] + "\nhas been" + " evaluated to: " + stack.pop());
		}

	}

}
