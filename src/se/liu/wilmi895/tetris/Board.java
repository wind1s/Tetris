package se.liu.wilmi895.tetris;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();
    private final static int MARGIN = 2;
    private final static int DOUBLE_MARGIN = MARGIN * 2;

    private List<BoardListener> boardListeners = new ArrayList<>();
    private SquareType[][] squares;
    private int width;
    private int height;
    private Poly falling;
    private Point fallingPos;
    private int fallingSize;
    private TetrominoMaker tetrominoMaker;
    private boolean gameOver;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.falling = null;
	this.fallingPos = null;
	this.fallingSize = 0;
	this.tetrominoMaker = new TetrominoMaker();
	this.gameOver = false;
	initDefaultBoard();
    }

    private void initDefaultBoard() {
	final int widthWithMargins = width + DOUBLE_MARGIN;
	final int heightWithMargins = height + DOUBLE_MARGIN;
	squares = new SquareType[heightWithMargins][widthWithMargins];

	for (int y = 0; y < heightWithMargins; ++y) {
	    for (int x = 0; x < widthWithMargins; ++x) {
		if (x < MARGIN || x >= width + MARGIN || y < MARGIN || y >= height + MARGIN) {
		    setSquare(x, y, SquareType.OUTSIDE);

		} else {
		    setSquare(x, y, SquareType.EMPTY);
		}
	    }
	}
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    private void setSquare(final int x, final int y, final SquareType squareType) {
	squares[y][x] = squareType;
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
	if (!gameOver) {
	    if (hasFallingTetromino()) {
		translateFalling(0, 1);

		// Check if the bottom of the falling tetromino area has collided with something.
		if (hasCollision()) {
		    translateFalling(0, -1);
		    placeFallingOnBoard();
		    falling = null;
		    fallingPos = null;
		}

	    } else {
		setFalling(RND.nextInt(0, tetrominoMaker.getNumberOfTypes() - 2));
		// If a newly spawned tetromino collides immediately, its game over.
		gameOver = hasCollision();
	    }

	    notifyListeners();
	}
    }

    private void placeFallingOnBoard() {
	for (int y = 0; y < fallingSize; y++) {
	    for (int x = 0; x < fallingSize; x++) {
		final SquareType squareType = falling.getSquare(x, y);

		if (squareType != SquareType.EMPTY) {
		    setSquare(fallingPos.x + x, fallingPos.y + y, squareType);
		}
	    }
	}
    }

    private void translateFalling(final int dx, final int dy) {
	fallingPos.translate(dx, dy);
    }

    private void setFalling(final int n) {
	falling = tetrominoMaker.getPoly(n);
	// Set the position to top middle of the screen.
	fallingSize = falling.getSize();
	fallingPos = new Point((width + DOUBLE_MARGIN - fallingSize) / 2, MARGIN);
    }

    private boolean hasCollision() {
	for (int x = 0; x < fallingSize; ++x) {
	    for (int y = 0; y < fallingSize; ++y) {
		if (hasFallingCollision(x, y)) {
		    return true;
		}
	    }
	}

	return false;
    }

    private boolean hasFallingCollision(final int x, final int y) {
	return falling.getSquare(x, y) != SquareType.EMPTY &&
	       squares[fallingPos.y + y][fallingPos.x + x] != SquareType.EMPTY;
    }

    public void move(Direction direction) {
	if (hasFallingTetromino()) {
	    int dx = 0;
	    int dy = 0;

	    switch (direction) {
		case LEFT -> dx = -1;
		case RIGHT -> dx = 1;
		default -> throw new IllegalArgumentException("Invalid move direction");
	    }
	    translateFalling(dx, dy);
	    // If a collision occurs move the tetromino back. No need to notify listeners in this case.
	    if (hasCollision()) {
		translateFalling(-dx, dy);
	    }

	    notifyListeners();
	}
    }

    public void rotate(Direction direction) {
	if (hasFallingTetromino()) {
	    Poly rotatedFalling = null;

	    switch (direction) {
		case LEFT -> rotatedFalling = falling.rotatedLeft();
		case RIGHT -> rotatedFalling = falling.rotatedRight();
		default -> throw new IllegalArgumentException("Invalid rotate direction");
	    }

	    final Poly oldFalling = falling;
	    falling = rotatedFalling;

	    if (hasCollision()) {
		falling = oldFalling;
	    } else {
		notifyListeners();
	    }
	}
    }

    public SquareType getSquare(final int x, final int y) {
	if (outsideBoard(x, y)) {
	    throw new IllegalArgumentException("X and Y is out of bounds");
	}

	return squares[y + MARGIN][x + MARGIN];
    }

    public SquareType getVisibleSquare(final int x, final int y) {
	if (hasFallingTetromino()) {
	    final int xDiff = x - fallingPos.x + MARGIN;
	    final int yDiff = y - fallingPos.y + MARGIN;

	    if (xDiff >= 0 && xDiff < fallingSize && yDiff >= 0 && yDiff < fallingSize) {
		final SquareType square = falling.getSquare(xDiff, yDiff);

		if (square != SquareType.EMPTY) {
		    return square;
		}
	    }
	}

	return getSquare(x, y);
    }

    private boolean hasFallingTetromino() {
	return falling != null && fallingPos != null;
    }

    private boolean outsideBoard(final int x, final int y) {
	return x < 0 || x >= width || y < 0 || y >= height;
    }

    public void randomizeBoard() {
	final int length = tetrominoMaker.getNumberOfTypes() - 2;

	for (final SquareType[] row : squares) {
	    for (int x = 0; x < width; ++x) {
		row[x] = SquareType.fromInteger(Board.RND.nextInt(length));
	    }
	}

	notifyListeners();
    }
}
