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
    private TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private SquareType[][] squares;
    private int width;
    private int height;
    private Poly falling = null;
    private Point fallingPos = null;
    private int fallingSize = 0;
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
		// x or y is outside the visible board, put a OUTSIDE type there.
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

    public SquareType getSquare(final int x, final int y) {
	if (isOutsideBoard(x, y)) {
	    throw new IllegalArgumentException(String.format("%d (x) and %d (y) is out of bounds", x, y));
	}
	// Return the SquareType inside the board.
	return squares[y + MARGIN][x + MARGIN];
    }

    public SquareType getVisibleSquare(final int x, final int y) {
	if (hasFallingTetromino()) {
	    final int xDiff = x - fallingPos.x + MARGIN;
	    final int yDiff = y - fallingPos.y + MARGIN;

	    // If the difference is within the falling tetrominos size, then display it's SquareType.
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
		removeFullRows();
	    }

	} else {
	    setFalling(RND.nextInt(0, tetrominoMaker.getNumberOfTypes() - 2));
	    // If a newly spawned tetromino collides immediately, its game over.
	    gameOver = hasCollision();
	}

	notifyListeners();
    }

    private int removeFullRows() {
	int filledRows = 0;

	for (int row = 0; row < height; ) {
	    // If the row is full, move all rows down effectively removing that row.
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
		// If a row is EMPTY then we break as there can't be a full row.
		break;
	    }
	}

	return nonEmptyCount == width;
    }

    private void moveRowsDown(final int rowToRemove) {
	for (int row = rowToRemove; row >= 1; --row) {
	    // Get the SquareType above the current column to effectivly move a row down.
	    for (int col = 0; col < width; ++col) {
		setSquare(col + MARGIN, row + MARGIN, getSquare(col, row - 1));
	    }
	}
    }

    private void placeFallingOnBoard() {
	for (int y = 0; y < fallingSize; y++) {
	    // Place the non EMPTY squares on the board.
	    for (int x = 0; x < fallingSize; x++) {
		final SquareType square = falling.getSquare(x, y);

		if (square != SquareType.EMPTY) {
		    setSquare(fallingPos.x + x, fallingPos.y + y, square);
		}
	    }
	}
	falling = null;
	fallingPos = null;
    }

    private void translateFalling(final int dx, final int dy) {
	fallingPos.translate(dx, dy);
    }

    private void setFalling(final int n) {
	falling = tetrominoMaker.getPoly(n);
	fallingSize = falling.getSize();
	// Set the position to top middle of the screen.
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
	    //case DOWN -> dy = 1;
	    default -> throw new IllegalArgumentException("Invalid move direction");
	}
	translateFalling(dx, dy);
	// If a collision occurs move the tetromino back. No need to notify listeners in this case.
	if (hasCollision()) {
	    translateFalling(-dx, -dy);
	} else {
	    notifyListeners();
	}
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

    private boolean isOutsideBoard(final int x, final int y) {
	return x < 0 || x >= width || y < 0 || y >= height;
    }

    public void randomizeBoard() {
	final int length = tetrominoMaker.getNumberOfTypes();

	for (final SquareType[] row : squares) {
	    for (int x = 0; x < width; ++x) {
		row[x] = SquareType.fromInteger(Board.RND.nextInt(length));
	    }
	}

	notifyListeners();
    }
}
