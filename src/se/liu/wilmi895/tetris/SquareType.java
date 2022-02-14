package se.liu.wilmi895.tetris;

import java.util.Random;

public enum SquareType
{

    EMPTY, OUTSIDE, I, O, T, S, Z, J, L;

    public static int getTypeCount() {
	return SquareType.values().length;
    }

    public static SquareType fromInteger(final int n) {
	if (n < 0 || n >= getTypeCount()) {
	    throw new IllegalArgumentException("Illegal SquareType: " + n);
	}
	return SquareType.values()[n];
    }

    public static void main(String[] args) {
	final Random rnd = new Random();

	for (int i = 0; i < 25; i++) {
	    System.out.println(rnd.nextInt(100));
	}

	final int length = SquareType.getTypeCount();

	for (int i = 0; i < 25; i++) {
	    System.out.println(SquareType.fromInteger(rnd.nextInt(length - 1)));
	}
    }
}
