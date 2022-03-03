package se.liu.wilmi895.tetris;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class FallHeavy extends FallHandler
{
    private final int boardHeight;

    public FallHeavy(final Board board) {
	super(board);
	this.boardHeight = board.getHeight();
    }

    @Override protected final boolean abstractCollision(final Point oldFallingPos) {
	final int rowsRemoved = board.removeFullRows();
	
	if(rowsRemoved > 0) {
	    board.increasScore(rowsRemoved);
	    return false;
	}
	final List<Point> collisionCoords = new ArrayList<>();

	for (int y = 0; y < fallingSize; ++y) {
	    for (int x = 0; x < fallingSize; ++x) {
		// If falling is currently outside or the old position was outside the board there is a collision.
		if (isFallingOutsideBoard(x, y)) {
		    return true;
		}

		if (isFallingSquareCollision(x, y)) {
		    final int yBoard = fallingPos.y + y;
		    final int xBoard = fallingPos.x + x;
		    final int row = rowOfFirstEmpty(xBoard, yBoard);

		    if (row < 0 || oldFallingPos.x != fallingPos.x) {
			return true;
		    }
		    collisionCoords.add(new Point(xBoard, row));
		}
	    }
	}

	for (final Point coord : collisionCoords) {
	    board.pushDownColumn(coord.x, coord.y);
	}

	return false;
    }

    private int rowOfFirstEmpty(final int col, final int startRow) {
	for (int row = startRow; row < boardHeight; ++row) {
	    if (board.isSquareEmpty(col, row)) {
		return row;
	    }
	}
	return -1;
    }
}
