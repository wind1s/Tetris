package se.liu.wilmi895.tetris;

public class Poly
{
    private SquareType[][] shape;
    private int size;

    public Poly(final SquareType[][] shape) {
	this.shape = shape;
	// Assumes height == width
	this.size = shape.length;
    }

    public SquareType getSquare(final int x, final int y) {
	if (x < 0 || x >= size || y < 0 || y >= size) {
	    throw new IllegalArgumentException("X and Y out of bounds");
	}

	return shape[y][x];
    }

    public int getSize() {
	return size;
    }

    public Poly rotatedRight() {
	SquareType[][] newShape = new SquareType[size][size];

	for (int r = 0; r < size; ++r) {
	    for (int c = 0; c < size; ++c){
		newShape[c][size-1-r] = shape[r][c];
	    }
	}

	return new Poly(newShape);
    }

    public Poly rotatedLeft() {
	SquareType[][] newShape = new SquareType[size][size];

	for (int l = 0; l < size; ++l) {
	    for (int c = 0; c < size; ++c){
		newShape[l][c] = shape[c][size-1-l];
	    }
	}

	return new Poly(newShape);
    }
}
