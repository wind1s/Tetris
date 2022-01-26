package se.liu.wilmi895.tetris;

import java.util.Arrays;

public class TetrominoMaker
{

    public int getNumberOfTypes() {
	return SquareType.getTypeCount();
    }

    public Poly getPoly(final int n) {
	SquareType[][] polyShape;

	switch (SquareType.fromInteger(n)) {
	    case I -> polyShape = createTetrominoI();
	    case O -> polyShape = createTetrominoO();
	    case J -> polyShape = createTetrominoJ();
	    case L -> polyShape = createTetrominoL();
	    case S -> polyShape = createTetrominoS();
	    case T -> polyShape = createTetrominoT();
	    case Z -> polyShape = createTetrominoZ();
	    default -> throw new IllegalArgumentException("Invalid Poly index: " + n);
	}

	return new Poly(polyShape);
    }

    public SquareType[] createTetrominoRow(final SquareType type, final int length) {
	SquareType[] row = new SquareType[length];
	Arrays.fill(row, type);
	return row;
    }

    public SquareType[][] createTetrominoI() {
	SquareType[][] shape = { createTetrominoRow(SquareType.EMPTY, 4), createTetrominoRow(SquareType.I, 4),
		createTetrominoRow(SquareType.EMPTY, 4), createTetrominoRow(SquareType.EMPTY, 4) };
	return shape;
    }

    private SquareType[][] createTetrominoO() {
	SquareType[][] shape = { createTetrominoRow(SquareType.O, 2), createTetrominoRow(SquareType.O, 2) };
	return shape;
    }

    private SquareType[][] createTetrominoJ() {
	SquareType[][] shape =
		{ { SquareType.J, SquareType.EMPTY, SquareType.EMPTY }, createTetrominoRow(SquareType.J, 3),
			createTetrominoRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createTetrominoL() {
	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.EMPTY, SquareType.L }, createTetrominoRow(SquareType.L, 3),
			createTetrominoRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createTetrominoS() {

	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.S, SquareType.S }, { SquareType.S, SquareType.S, SquareType.EMPTY },
			createTetrominoRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createTetrominoT() {
	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.T, SquareType.EMPTY }, createTetrominoRow(SquareType.T, 3),
			createTetrominoRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createTetrominoZ() {
	SquareType[][] shape =
		{ { SquareType.Z, SquareType.Z, SquareType.EMPTY }, { SquareType.EMPTY, SquareType.Z, SquareType.Z },
			createTetrominoRow(SquareType.EMPTY, 3) };
	return shape;
    }

}
