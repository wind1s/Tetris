package se.liu.wilmi895.tetris;

import java.util.Random;

public enum SquareType
{

    EMPTY, I, O, T, S, Z, J, L;
    public static final int TYPE_COUNT = 7;

    public static int getTypeCount() {
	return TYPE_COUNT;
    }

    public static SquareType fromInteger(final int n) {
	switch (n) {
	    case 0:
		return EMPTY;
	    case 1:
		return I;
	    case 2:
		return O;
	    case 3:
		return T;
	    case 4:
		return S;
	    case 5:
		return Z;
	    case 6:
		return J;
	    case 7:
		return L;
	    default:
		throw new IllegalArgumentException("Illegal SquareType: " + n);
	}
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
