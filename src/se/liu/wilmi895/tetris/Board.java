package se.liu.wilmi895.tetris;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board
{
    private final static boolean DEBUG = true;
    private final static Random RND = new Random();

    private final List<BoardListener> boardListeners = new ArrayList<>();
    private final TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private SquareType[][] squares = null;
    private Poly falling = null;
    private Point fallingPos = null;
    private int fallingSize = 0;
    private boolean gameOver = false;
    private boolean isPaused = false;
    private FallHandler fallHandler;
    private final int height;
    private final int width;
    private final ScoreCounter scoreCounter;

    public Board(final int width, final int height, final ScoreCounter scoreCounter) {
	this.width = width;
	this.height = height;
	this.fallHandler = new DefaultFallHandler(this);
	this.scoreCounter = scoreCounter;
	setDefaultBoard();

	if (DEBUG) {
	    Arrays.fill(squares[13], 0, width, SquareType.I);
	    Arrays.fill(squares[14], 0, width, SquareType.I);
	    setSquare(4, 13, SquareType.EMPTY);
	    setSquare(4, 14, SquareType.EMPTY);
	    setSquare(4, 11, SquareType.I);
	    setSquare(4, 12, SquareType.I);
	}
    }

    private void setDefaultBoard() {
	squares = new SquareType[height][width];

	for (int y = 0; y < height; ++y) {
	    setEmptyRow(y);
	}
    }

    private void setEmptyRow(final int row) {
	Arrays.fill(squares[row], 0, width, SquareType.EMPTY);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public void restartGame() {
	setDefaultBoard();
	resetFalling();
	scoreCounter.resetScore();
	gameOver = false;
	pauseGame(false);

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
	    if (xDiff >= 0 && xDiff < fallingSize && yDiff >= 0 && yDiff < fallingSize &&
		!isFallingSquareEmpty(xDiff, yDiff)) {
		return getFallingSquare(xDiff, yDiff);
	    }
	}
	return getSquare(x, y);
    }

    public SquareType getFallingSquare(final int x, final int y) {
	return falling.getSquare(x, y);
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public int getFallingSize() {
	return fallingSize;
    }

    private void resetFalling() {
	falling = null;
	fallingPos = null;
	fallingSize = 0;
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

    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener bl : boardListeners) {
	    bl.boardChanged();
	}
    }

    public void tick() {
	if (isPaused || isGameOver()) {
	    return;
	}

	if (hasFallingTetromino()) {
	    final boolean hasCollided = move(Direction.DOWN);
	    // Check if the bottom of the falling tetromino area has collided with something.
	    if (hasCollided) {
		placeFallingOnBoard();
		increasScore(removeFullRows());
	    }
	} else {
	    setFalling(RND.nextInt(0, tetrominoMaker.getNumberOfTypes()));
	    if(DEBUG) {
		setFalling(6);
	    }
	    // If a newly spawned tetromino collides immediately, its game over.
	    gameOver = fallHandler.hasCollision(fallingPos);
	}
	notifyListeners();
    }

    public void increasScore(final int rowsRemoved) {
	scoreCounter.increaseScore(rowsRemoved);
    }

    public int removeFullRows() {
	int rowsRemoved = 0;

	for (int row = 0; row < height; ) {
	    // If the row is full, move all rows down effectively removing that row.
	    if (isFullRow(row)) {
		moveRowsDown(row);
		rowsRemoved++;
	    } else {
		row++;
	    }
	}
	return rowsRemoved;
    }

    public boolean isFullRow(final int row) {
	for (int col = 0; col < width; ++col) {
	    if (isSquareEmpty(col, row)) {
		return false;
	    }
	}
	return true;
    }

    public void moveRowsDown(final int startingRow) {
	for (int row = startingRow; row >= 1; --row) {
	    // Get the SquareType above the current column to effectivly move a row down.
	    squares[row] = squares[row - 1];
	}
	squares[0] = new SquareType[width];
	setEmptyRow(0);
    }

    public void pushDownColumn(final int col, final int stopRow) {
	// stopRow at col has to contain an empty square to not remove any squares.
	for (int row = stopRow; row >= 1; --row) {
	    setSquare(col, row, getSquare(col, row - 1));
	}
    }

    private void placeFallingOnBoard() {
	for (int y = 0; y < fallingSize; y++) {
	    for (int x = 0; x < fallingSize; x++) {
		// Place the non empty squares on the board.
		if (!isFallingSquareEmpty(x, y)) {
		    setSquare(fallingPos.x + x, fallingPos.y + y, falling.getSquare(x, y));
		}
	    }
	}
	resetFalling();
    }

    public boolean move(Direction direction) {
	if (!hasFallingTetromino()) {
	    return false;
	}
	final Point oldFallingPos = new Point(fallingPos);
	translateFalling(direction);
	final boolean hasCollided = fallHandler.hasCollision(oldFallingPos);

	// If a collision occurs move the tetromino back. No need to notify listeners in this case.
	if (hasCollided) {
	    fallingPos = oldFallingPos;
	} else {
	    notifyListeners();
	}
	return hasCollided;
    }

    public void translateFalling(final Direction direction) {
	int dx = 0;
	int dy = 0;

	switch (direction) {
	    case LEFT -> dx = -1;
	    case RIGHT -> dx = 1;
	    case DOWN -> dy = 1;
	    default -> throw new IllegalArgumentException("Invalid move direction");
	}

	fallingPos.translate(dx, dy);
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
	final Point oldFallingPos = fallingPos;
	falling = rotatedFalling;

	if (fallHandler.hasCollision(oldFallingPos)) {
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

    public boolean isSquareEmpty(final int x, final int y) {
	return SquareType.isEmpty(getSquare(x, y));
    }

    public boolean isFallingSquareEmpty(final int x, final int y) {
	return SquareType.isEmpty(getFallingSquare(x, y));
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
