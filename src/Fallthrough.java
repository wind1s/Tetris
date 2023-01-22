package se.liu.wilmi895.tetris;

import java.awt.Point;

public class Fallthrough extends FallHandler
{
    public Fallthrough(final Board board) {
	super(board);
    }

    @Override protected final boolean abstractCollision(final Point oldFallingPos) {
	for (int y = 0; y < fallingSize; ++y) {
	    for (int x = 0; x < fallingSize; ++x) {
		if (isFallingOutsideBoard(x, y)) {
		    return true;
		}
	    }
	}
	return false;
    }
}
