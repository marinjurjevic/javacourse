package hr.fer.zemris.java.hw1;

/**
 * Program for decompositing number onto prime factors.
 * Program takes number through command line.
 */
public class NumberDecomposition {

	public static void main(String[] args) {
		int number = Integer.parseInt(args[0]);
		int i = 2; // first prime number
		
		while(number > 1){
			if(number % i == 0){
				System.out.println(i);
				number/=i;
			}
			else
				i++;
		}
	}

}
