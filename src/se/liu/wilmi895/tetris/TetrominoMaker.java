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
	    case I -> polyShape = createShapeI();
	    case O -> polyShape = createShapeO();
	    case J -> polyShape = createShapeJ();
	    case L -> polyShape = createShapeL();
	    case S -> polyShape = createShapeS();
	    case T -> polyShape = createShapeT();
	    case Z -> polyShape = createShapeZ();
	    default -> throw new IllegalArgumentException("Invalid Poly index: " + n);
	}

	return new Poly(polyShape);
    }

    public SquareType[] createShapeTypeRow(final SquareType type, final int length) {
	SquareType[] row = new SquareType[length];
	Arrays.fill(row, type);
	return row;
    }

    public SquareType[][] createShapeI() {
	SquareType[][] shape = { createShapeTypeRow(SquareType.EMPTY, 4), createShapeTypeRow(SquareType.I, 4),
		createShapeTypeRow(SquareType.EMPTY, 4), createShapeTypeRow(SquareType.EMPTY, 4) };
	return shape;
    }

    private SquareType[][] createShapeO() {
	SquareType[][] shape = { createShapeTypeRow(SquareType.O, 2), createShapeTypeRow(SquareType.O, 2) };
	return shape;
    }

    private SquareType[][] createShapeJ() {
	SquareType[][] shape =
		{ { SquareType.J, SquareType.EMPTY, SquareType.EMPTY }, createShapeTypeRow(SquareType.J, 3),
			createShapeTypeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createShapeL() {
	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.EMPTY, SquareType.L }, createShapeTypeRow(SquareType.L, 3),
			createShapeTypeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createShapeS() {

	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.S, SquareType.S }, { SquareType.S, SquareType.S, SquareType.EMPTY },
			createShapeTypeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createShapeT() {
	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.T, SquareType.EMPTY }, createShapeTypeRow(SquareType.T, 3),
			createShapeTypeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createShapeZ() {
	SquareType[][] shape =
		{ { SquareType.Z, SquareType.Z, SquareType.EMPTY }, { SquareType.EMPTY, SquareType.Z, SquareType.Z },
			createShapeTypeRow(SquareType.EMPTY, 3) };
	return shape;
    }

}
