package se.liu.wilmi895.lab1;

public class Exercise5
{
    public static void main(String[] args) {
	for (int i = 0; i <= 20; i++) {
	    System.out.println(i + "-fakultet: " + factorial(i));
	}
    }

    /*
     * Calculates f! given f.
     *
     * @param f
     *
     * @return f!
     */
    private static int factorial(int f) {
	if (f == 0) {
	    return 1;
	}

	int result = 1;

	for (int i = 1; i <= f; i++) {
	    result *= i;
	}

	return result;
    }


}
