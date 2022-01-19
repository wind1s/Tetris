package se.liu.wilmi895.tetris;

import java.util.Random;

public enum SquareType
{
    EMPTY, I, O, T, S, Z, J, L;

    public static void main(String[] args) {
	final Random rnd = new Random();

	for (int i = 0; i < 25; i++) {
	    System.out.println(rnd.nextInt(100));
	}

	final SquareType[] values = SquareType.values();
	final int length = values.length;

	for (int i = 0; i < 25; i++) {
	    System.out.println(values[rnd.nextInt(length-1)]);
	}
    }
}
