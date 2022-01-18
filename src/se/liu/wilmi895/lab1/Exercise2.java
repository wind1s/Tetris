package se.liu.wilmi895.lab1;

import javax.swing.*;

public class Exercise2
{
    public static void main(String[] args) {
	final int min = 10;
	final int max = 20;
	System.out.println(sumFor(min, max));
	System.out.println(sumWhile(min, max));

    }

    public static int sumFor(int min, int max) {
	int sum = 0;

	for (int i = min; i <= max; i++) {
	    sum += i;
	}

	return sum;
    }

    public static int sumWhile(int min, int max) {
	int sum = 0;

	while (min <= max) {
	    sum += min;
	    min++;
	}

	return sum;
    }
}
