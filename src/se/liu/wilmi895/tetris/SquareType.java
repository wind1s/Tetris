package se.liu.wilmi895.tetris;

import java.util.Random;

public enum SquareType
{

    I, O, T, S, Z, J, L, EMPTY, OUTSIDE;
    public final static int NON_TETROMINO_COUNT = 2;

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
