package se.liu.wilmi895.tetris;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Poly falling = null;
    private Point fallingPos = null;
    private int fallingSize = 0;
    private TetrominoMaker tetrominoMaker = new TetrominoMaker();
    ;
    private boolean gameOver = false;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
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
		    if (y == 16) {
			setSquare(x, y, SquareType.I);
		    } else {
			setSquare(x, y, SquareType.EMPTY);
		    }
		}
	    }
	}

	//setSquare(2, 16, SquareType.EMPTY);
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

    public SquareType getSquare(final int x, final int y) {
	if (outsideBoard(x, y)) {
	    throw new IllegalArgumentException(String.format("%d (x) and %d (y) is out of bounds", x, y));
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

    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener bl : boardListeners) {
	    bl.boardChanged();
	}
    }

    public void tick() {
	if (gameOver) {
	    return;
	}

	if (hasFallingTetromino()) {
	    final int dy = 1;
	    translateFalling(0, dy);

	    // Check if the bottom of the falling tetromino area has collided with something.
	    if (hasCollision()) {
		translateFalling(0, -dy);
		placeFallingOnBoard();
		falling = null;
		fallingPos = null;
		removeFullRows();
	    }

	} else {
	    //setFalling(RND.nextInt(0, tetrominoMaker.getNumberOfTypes() - 2));
	    setFalling(6);
	    // If a newly spawned tetromino collides immediately, its game over.
	    gameOver = hasCollision();
	}

	notifyListeners();
    }

    private int removeFullRows() {
	int filledRows = 0;

	for (int row = 0; row < height; ) {
	    if (isFullRow(row)) {
		moveRowsDown(row);
		filledRows++;

	    } else {
		row++;
	    }
	}

	return filledRows;
    }

    private boolean isFullRow(final int row) {
	int nonEmptyCount = 0;

	for (int col = 0; col < width; ++col) {
	    if (getSquare(col, row) != SquareType.EMPTY) {
		nonEmptyCount++;
	    } else {
		break;
	    }
	}

	return nonEmptyCount == width;
    }

    private void moveRowsDown(final int rowToRemove) {
	for (int row = rowToRemove; row >= 1; --row) {
	    for (int col = 0; col < width; ++col) {
		setSquare(col + MARGIN, row + MARGIN, getSquare(col, row - 1));
	    }
	}
    }

    private void placeFallingOnBoard() {
	for (int y = 0; y < fallingSize; y++) {
	    for (int x = 0; x < fallingSize; x++) {
		final SquareType square = falling.getSquare(x, y);

		if (square != SquareType.EMPTY) {
		    setSquare(fallingPos.x + x, fallingPos.y + y, square);
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
	if (!hasFallingTetromino()) {
	    return;
	}
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

    public void rotate(Direction direction) {
	if (!hasFallingTetromino()) {
	    return;
	}
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
