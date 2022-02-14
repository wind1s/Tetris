package se.liu.wilmi895.tetris;

import java.util.Arrays;
import java.util.Random;

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

    public SquareType[][] createShapeI() {
	final SquareType empty = SquareType.EMPTY;
	final SquareType i = SquareType.I;
	SquareType[][] shape = { { empty, empty, empty, empty }, { i, i, i, i }, { empty, empty, empty, empty },
		{ empty, empty, empty, empty } };
	return shape;
    }

    private SquareType[][] createShapeO() {
	final SquareType o = SquareType.O;
	SquareType[][] shape = { { o, o }, { o, o } };
	return shape;
    }

    private SquareType[][] createShapeJ() {
	final SquareType empty = SquareType.EMPTY;
	final SquareType j = SquareType.J;
	SquareType[][] shape = { { j, empty, empty }, { j, j, j }, { empty, empty, empty } };
	return shape;
    }

    private SquareType[][] createShapeL() {
	final SquareType empty = SquareType.EMPTY;
	final SquareType l = SquareType.L;
	SquareType[][] shape = { { empty, empty, l }, { l, l, l }, { empty, empty, empty } };
	return shape;
    }

    private SquareType[][] createShapeS() {
	final SquareType empty = SquareType.EMPTY;
	final SquareType s = SquareType.S;
	SquareType[][] shape = { { empty, s, s }, { s, s, empty }, { empty, empty, empty } };
	return shape;
    }

    private SquareType[][] createShapeT() {
	final SquareType empty = SquareType.EMPTY;
	final SquareType t = SquareType.T;
	SquareType[][] shape = { { empty, t, empty }, { t, t, t }, { empty, empty, empty } };
	return shape;
    }

    private SquareType[][] createShapeZ() {
	final SquareType empty = SquareType.EMPTY;
	final SquareType z = SquareType.Z;
	SquareType[][] shape = { { z, z, empty }, { empty, z, z }, { empty, empty, empty } };
	return shape;
    }

}
