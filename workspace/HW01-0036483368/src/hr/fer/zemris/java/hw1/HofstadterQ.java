package hr.fer.zemris.java.hw1;

/**
 * Program that calculates i-th number of Hofstadter's Q sequence.
 * Program accepts paramter i through command line, i has to be
 * greater than zero or error occurs.
 * 
 */
public class HofstadterQ {
	
	/**
	 * Program entry point.
	 * @param args one long parameter to calculate value of i-th number in Hofstadter's Q sequence
	 */
	public static void main(String[] args) {
		long i = Long.parseLong(args[0]);
		
		if(i<=0){
			System.out.println("Provided argument must be greather than 0.");
		}
		else{
			System.out.println("You requested calculation of " + i + ". number of Hofstadter's Q-sequence." 
					+ "The requested number is " + qSequence(i) + ".");
		}
	}
	
	/**
	 * Recursive implementation of Hofstadter's Q sequence.
	 * @param i i-th number in the sequence
	 * @return value of i-th number in Q sequence
	 */
	private static long qSequence(long i){
		if(i <= 2) return 1;
		return qSequence(i - qSequence(i-1)) + qSequence(i - qSequence(i-2));
	}

}
