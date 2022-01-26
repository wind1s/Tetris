package se.liu.wilmi895.tetris;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();

    private SquareType[][] squares;
    private int width;
    private int height;
    private Poly falling;
    private Point fallingPos;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.falling = new TetrominoMaker().getPoly(1);
	this.fallingPos = new Point(3,3);
	this.squares = new SquareType[height][width];

	for (SquareType[] array : this.squares) {
	    Arrays.fill(array, SquareType.EMPTY);
	}
    }

    public void randomizeBoard() {
	final int length = SquareType.getTypeCount();

	for (final SquareType[] row : this.squares) {
	    for (int x = 0; x < width; x++) {
		row[x] = SquareType.fromInteger(Board.RND.nextInt(length - 1));
	    }
	}
    }

    public SquareType getSquare(final int x, final int y) {
	if (x < 0 || x >= width || y < 0 || y >= height) {
	    throw new IllegalArgumentException("X and Y is out of bounds");
	}

	return squares[y][x];
    }

    public SquareType getVisibleSquare(final int x, final int y) {
	if (falling != null) {
	    final int xDiff = x - fallingPos.x;
	    final int yDiff = y - fallingPos.y;

	    if (xDiff >= 0 && xDiff < falling.getWidth() && yDiff >= 0 && yDiff < falling.getHeight()) {
		final SquareType square = falling.getSquare(xDiff, yDiff);

		if (square != SquareType.EMPTY) return square;
	    }
	}

	return getSquare(x, y);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }
}
