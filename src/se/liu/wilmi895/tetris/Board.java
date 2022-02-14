package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();

    private List<BoardListener> boardListeners = new ArrayList<>();
    private SquareType[][] squares;
    private int width;
    private int height;
    private int squareTypeCount;
    private Poly falling;
    private Point fallingPos;
    private TetrominoMaker tetrominoMaker;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.falling = null;
	this.fallingPos = null;
	this.tetrominoMaker = new TetrominoMaker();
	this.squareTypeCount = this.tetrominoMaker.getNumberOfTypes();
	initDefaultBoard();
    }

    private void initDefaultBoard() {
	squares = new SquareType[height][width];
	for (SquareType[] row : squares) {
	    Arrays.fill(row, SquareType.EMPTY);
	}
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener bl : boardListeners) {
	    bl.boardChanged();
	}
    }

    public void tick() {
	if (falling != null && fallingPos != null) {
	    translateFalling(0, 1);
	} else {
	    setFalling(RND.nextInt(1, squareTypeCount));
	}

	notifyListeners();
    }

    private void setFalling(final int n) {
	falling = tetrominoMaker.getPoly(n);
	// Set the position to top middle of the screen.
	fallingPos = new Point((width - falling.getWidth()) / 2, 0);
    }

    private void translateFalling(final int dx, final int dy) {
	fallingPos.translate(dx, dy);
    }

    public void move(Direction direction) {
	switch (direction) {
	    case LEFT -> translateFalling(-1, 0);
	    case RIGHT -> translateFalling(1, 0);
	    default -> throw new IllegalArgumentException("Invalid move direction");
	}

	notifyListeners();
    }

    public void randomizeBoard() {
	final int length = SquareType.getTypeCount();

	for (final SquareType[] row : squares) {
	    for (int x = 0; x < width; x++) {
		row[x] = SquareType.fromInteger(Board.RND.nextInt(length - 1));
	    }
	}

	notifyListeners();
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

		if (square != SquareType.EMPTY) {
		    return square;
		}
	    }
	}

	return getSquare(x, y);
    }
}
