package no.hiof.itf23019.prime_calculator_interprupt;

/**
 * This class generates prime numbers until is interrumped
 */
public class Calculator extends Thread {

	/**
	 * Central method of the class
	 */
	@Override
	public void run() {
		long number = 1L;

		// This never ends... until is interrupted
		while (true) {
			if (isPrime(number)) {
				System.out.printf("Number %d is Prime\n", number);
			}

			// When is interrupted, write a message and ends
			if (isInterrupted()) {
				System.out.printf("The Prime Generator has been Interrupted\n");
				return;
			}
			number++;
		}
	}

	/**
	 * Method that calculate if a number is prime or not
	 * 
	 * @param number
	 *            : The number
	 * @return A boolean value. True if the number is prime, false if not.
	 */
	private boolean isPrime(long number) {
		//TODO: Complete the implementation for this method. 
		// One Naïve Solution – Iterate through 2 to n-1 and check if given number 
		// is divisible by any number between 2 to n-1, 
		// if yes then number is not prime else it is prime.
		long m=0;
		int flag=0;
		m = number/2;
		if(number==0||number==1){
			System.out.println(number + " is not prime number");
		}else{
			for(int i=2;i<=m;i++){
				if(number%i==0){
					System.out.println(number+" is not prime number");
					flag=1;
					break;

				}
			}
			if(flag==0)  { System.out.println(number +" is prime number"); }
		}//end of else
		return false;
	}

}
