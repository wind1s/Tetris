package se.liu.wilmi895.tetris;

public class Poly
{
    private SquareType[][] shape;
    private int width;
    private int height;

    public Poly(final SquareType[][] shape) {
	this.shape = shape;
	this.height = shape.length;
	this.width = shape[0].length;
    }

    public SquareType getSquare(final int x, final int y) {
	if (x < 0 || x >= width || y < 0 || x >= height) {
	    throw new IllegalArgumentException("X and Y out of bounds");
	}

	return shape[y][x];
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }
}
