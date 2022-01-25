package se.liu.wilmi895.tetris;

import java.util.Random;

public enum SquareType
{
    EMPTY, I, O, T, S, Z, J, L;

    public static SquareType[] getTypes() {
	return SquareType.values();
    }

    public static int getTypeCount() {
	return getTypes().length;
    }

    public static SquareType getType(int n) {
	if (n > (getTypeCount() - 1) || n < 0) {
	    throw new IllegalArgumentException("Illegal SquareType: " + n);
	}

	return getTypes()[n];
    }

    public static void main(String[] args) {
	final Random rnd = new Random();

	for (int i = 0; i < 25; i++) {
	    System.out.println(rnd.nextInt(100));
	}

	final SquareType[] values = SquareType.values();
	final int length = values.length;

	for (int i = 0; i < 25; i++) {
	    System.out.println(values[rnd.nextInt(length - 1)]);
	}
    }
}
