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

    private final List<BoardListener> boardListeners = new ArrayList<>();
    private final TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private SquareType[][] squares = null;
    private Poly falling = null;
    private Point fallingPos = null;
    private int fallingSize = 0;
    private int rowsLastRemoved = 0;
    private boolean gameOver = false;
    private boolean isPaused = false;
    private FallHandler fallHandler;
    private final int height;
    private final int width;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.fallHandler = new DefaultFallHandler(this);
	initDefaultBoard();
    }

    private void initDefaultBoard() {
	squares = new SquareType[height][width];

	for (int y = 0; y < height; ++y) {
	    Arrays.fill(squares[y], 0, width, SquareType.EMPTY);
	}
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int rowsLastRemoved() {
	final int rowsRemoved = rowsLastRemoved;
	rowsLastRemoved = 0;
	return rowsRemoved;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public void restartGame() {
	initDefaultBoard();
	pauseGame(false);
	falling = null;
	fallingPos = null;
	fallingSize = 0;
	gameOver = false;

	notifyListeners();
    }

    private void setSquare(final int x, final int y, final SquareType squareType) {
	squares[y][x] = squareType;
    }

    public SquareType getSquare(final int x, final int y) {
	if (isOutsideBoard(x, y)) {
	    throw new IllegalArgumentException(String.format("%d (x) and %d (y) is out of bounds", x, y));
	}
	// Return the SquareType inside the board.
	return squares[y][x];
    }

    public SquareType getVisibleSquare(final int x, final int y) {
	if (hasFallingTetromino()) {
	    final int xDiff = x - fallingPos.x;
	    final int yDiff = y - fallingPos.y;

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

    public SquareType getFallingSquare(final int x, final int y) {
	return falling.getSquare(x,y);
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public int getFallingSize() {
	return fallingSize;
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
	if (isPaused || gameOver) {
	    return;
	}

	if (hasFallingTetromino()) {
	    final boolean hasCollided = move(Direction.DOWN);
	    // Check if the bottom of the falling tetromino area has collided with something.
	    if (hasCollided) {
		placeFallingOnBoard();
		removeFullRows();
	    }
	} else {
	    setFalling(RND.nextInt(0, tetrominoMaker.getNumberOfTypes()));
	    // If a newly spawned tetromino collides immediately, its game over.
	    gameOver = fallHandler.hasCollision();
	}

	notifyListeners();
    }

    private void removeFullRows() {
	rowsLastRemoved = 0;
	for (int row = 0; row < height; ) {
	    // If the row is full, move all rows down effectively removing that row.
	    if (isFullRow(row)) {
		moveRowsDown(row);
		rowsLastRemoved++;
	    } else {
		row++;
	    }
	}
    }

    private boolean isFullRow(final int row) {

	for (int col = 0; col < width; ++col) {
	    if (getSquare(col, row) == SquareType.EMPTY) {
		return false;
	    }
	}

	return true;
    }

    public void moveRowsDown(final int rowToRemove) {
	for (int row = rowToRemove; row >= 1; --row) {
	    // Get the SquareType above the current column to effectivly move a row down.
	    squares[row] = squares[row - 1];
	}
	squares[0] = new SquareType[width];
	Arrays.fill(squares[0], 0, width, SquareType.EMPTY);
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
	fallingPos = new Point((width - fallingSize) / 2, 0);
    }

    public void setFallHandler(final FallHandler fallHandler) {
	this.fallHandler = fallHandler;
    }

    public boolean powerUpActive() {
	return !fallHandler.getClass().equals(DefaultFallHandler.class);
    }

    public boolean move(Direction direction) {
	if (!hasFallingTetromino()) {
	    return false;
	}
	int dx = 0;
	int dy = 0;

	switch (direction) {
	    case LEFT -> dx = -1;
	    case RIGHT -> dx = 1;
	    case DOWN -> dy = 1;
	    default -> throw new IllegalArgumentException("Invalid move direction");
	}
	translateFalling(dx, dy);
	// If a collision occurs move the tetromino back. No need to notify listeners in this case.
	final boolean hasCollided = fallHandler.hasCollision();
	if (hasCollided) {
	    translateFalling(-dx, -dy);
	} else {
	    notifyListeners();
	}

	return hasCollided;
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

	if (fallHandler.hasCollision()) {
	    falling = oldFalling;
	} else {
	    notifyListeners();
	}
    }


    public void pauseGame(final boolean pauseState) {
	isPaused = pauseState;
    }

    private boolean hasFallingTetromino() {
	return falling != null && fallingPos != null;
    }

    public boolean isOutsideBoard(final int x, final int y) {
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
