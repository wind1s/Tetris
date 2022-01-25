package se.liu.wilmi895.tetris;

import java.util.Arrays;

public class TetrominoMaker
{
    public TetrominoMaker() {
    }

    public int getNumberOfTypes() {
	return SquareType.getTypeCount();
    }

    public Poly getPoly(int n) {
	final int nTypes = getNumberOfTypes();
	final SquareType polyType = SquareType.getType(n);
	SquareType[][] polyShape;

	switch (polyType) {
	    case I -> polyShape = new SquareType[4][4];
	    case O -> polyShape = new SquareType[2][2];
	    case J, L, S, T, Z -> polyShape = new SquareType[3][3];
	    default -> throw new IllegalArgumentException("Invalid index: " + n);
	}

	fillPolyShape(polyShape, polyType);
	return new Poly(polyShape);
    }

    private static void fillPolyShape(SquareType[][] shape, SquareType type) {
	for(SquareType[] row : shape) {
	    Arrays.fill(row, type);
	}
    }

}
