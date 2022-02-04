package se.liu.wilmi895.tetris;

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
	this.squares = new SquareType[height][width];
	fillDefaultBoard();
    }

    private void fillDefaultBoard() {
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
	if(falling != null && fallingPos != null) {
		fallingPos.y += 1;
	} else {
		setFalling(RND.nextInt(1, squareTypeCount));
		moveFalling((width - falling.getWidth()) / 2,0);
	}

	notifyListeners();
    }

    private void setFalling(final int n) {
	falling = tetrominoMaker.getPoly(n);
    }

    private void moveFalling(final int x, final int y) {
	fallingPos = new Point(x,y);
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
