package se.liu.wilmi895.tetris;

import java.util.Arrays;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();

    private SquareType[][] squares;
    private int width;
    private int height;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height][width];

	for (SquareType[] array : this.squares) {
	    Arrays.fill(array, SquareType.EMPTY);
	}
    }

    public static void main(String[] args) {
	Board board = new Board(10, 20);
    }

    public void randomizeBoard() {
	final SquareType[] squareTypes= SquareType.getTypes();
	final int length = squareTypes.length - 1;

	for (SquareType[] row : this.squares) {
	    for (int x = 0; x < width; x++) {
		row[x] = squareTypes[Board.RND.nextInt(length)];
	    }
	}
    }

    public SquareType getSquare(final int x, final int y) {
	return this.squares[y][x];
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }
}
