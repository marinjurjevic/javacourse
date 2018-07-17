package hr.fer.zemris.java.hw1;

/**
 * Program writes out first n prime numbers.
 * Parameter n is taken as command line argument.
 */
public class PrimeNumbers {

	/**
	 * Program entry point.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int num=2;
		int i = 0;
		
		while(i<n){
			// assume number is prime
			boolean prime = true;
			
			for(int j=2; j <= (int)Math.sqrt(num); j++){
				if(num%j == 0){
					prime = false;
					break;
				}
			}
			
			if(prime == true){
				i++;
				System.out.println(num);
			}
			num++;
		}
	}

}
