package se.liu.wilmi895.tetris;

import java.awt.Point;

public class DefaultFallHandler extends FallHandler
{
    public DefaultFallHandler(final Board board) {
	super(board);
    }

    @Override protected final boolean abstractCollision(final Point oldFallingPos) {
	for (int y = 0; y < fallingSize; ++y) {
	    for (int x = 0; x < fallingSize; ++x) {
		// Collision occurs if the falling coordinate are outside the board or the falling square and board square are not empty.
		if (isFallingOutsideBoard(x, y) || isFallingSquareCollision(x, y)) {
		    return true;
		}
	    }
	}
	return false;
    }
}
