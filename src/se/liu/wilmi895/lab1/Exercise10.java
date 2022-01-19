package se.liu.wilmi895.lab1;

import java.math.BigInteger;

public class Exercise10
{
    public static void main(String[] args) {
	int number = 16777216;
	System.out.println(number);

	double decimal = number;
	System.out.println(decimal);

	int integerAgain = (int)decimal;
	System.out.println(integerAgain);

	int big = 2147483647;
	long bigger = big+1L;
	System.out.println(big);
	System.out.println(bigger);

	BigInteger bigInt = new BigInteger("123123123123123123123123");
	System.out.println(bigInt);
    }
}
