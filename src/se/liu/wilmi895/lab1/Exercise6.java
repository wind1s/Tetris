package se.liu.wilmi895.lab1;

public class Exercise6
{
    public static void main(String[] args) {
	for (int i = 2; i < 100; i++) {
	    System.out.println(isPrime(i));
	}
    }

    public static boolean isPrime(int number) {

	for (int i = 2; i < number; i++) {
	    int rest = number % i;

	    if (rest == 0) {
		return false;
	    }
	}

	return true;
    }
}