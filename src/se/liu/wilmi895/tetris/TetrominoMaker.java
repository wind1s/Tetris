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
	    case I -> polyShape = createPolyShapeI();
	    case O -> polyShape = createPolyShapeO();
	    case J -> polyShape = createPolyShapeJ();
	    case L -> polyShape = createPolyShapeL();
	    case S -> polyShape = createPolyShapeS();
	    case T -> polyShape = createPolyShapeT();
	    case Z -> polyShape = createPolyShapeZ();
	    default -> throw new IllegalArgumentException("Invalid Poly index: " + n);
	}

	return new Poly(polyShape);
    }

    public static void main(String[] args) {
	TetrominoMaker tet = new TetrominoMaker();
	for (SquareType[] s : tet.createPolyShapeI()) {
	    for (SquareType k : s) {
		System.out.println(k);
	    }
	}
    }

    public SquareType[] createPolyShapeRow(final SquareType type, final int length) {
	SquareType[] row = new SquareType[length];
	Arrays.fill(row, type);
	return row;
    }

    public SquareType[][] createPolyShapeI() {
	SquareType[][] shape = { createPolyShapeRow(SquareType.EMPTY, 4), createPolyShapeRow(SquareType.I, 4),
		createPolyShapeRow(SquareType.EMPTY, 4), createPolyShapeRow(SquareType.EMPTY, 4) };
	return shape;
    }

    private SquareType[][] createPolyShapeO() {
	SquareType[][] shape = { createPolyShapeRow(SquareType.O, 2), createPolyShapeRow(SquareType.O, 2) };
	return shape;
    }

    private SquareType[][] createPolyShapeJ() {
	SquareType[][] shape =
		{ { SquareType.J, SquareType.EMPTY, SquareType.EMPTY }, createPolyShapeRow(SquareType.J, 3),
			createPolyShapeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createPolyShapeL() {
	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.EMPTY, SquareType.L }, createPolyShapeRow(SquareType.L, 3),
			createPolyShapeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createPolyShapeS() {

	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.S, SquareType.S }, { SquareType.S, SquareType.S, SquareType.EMPTY },
			createPolyShapeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createPolyShapeT() {
	SquareType[][] shape =
		{ { SquareType.EMPTY, SquareType.T, SquareType.EMPTY }, createPolyShapeRow(SquareType.T, 3),
			createPolyShapeRow(SquareType.EMPTY, 3) };
	return shape;
    }

    private SquareType[][] createPolyShapeZ() {
	SquareType[][] shape =
		{ { SquareType.Z, SquareType.Z, SquareType.EMPTY }, { SquareType.EMPTY, SquareType.Z, SquareType.Z },
			createPolyShapeRow(SquareType.EMPTY, 3) };
	return shape;
    }

}
