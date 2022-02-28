package se.liu.wilmi895.tetris;

import java.awt.Point;

public class DefaultFallHandler extends FallHandler
{
    public DefaultFallHandler(final Board tetrisBoard) {
	super(tetrisBoard);
    }

    @Override public final boolean hasCollision() {
	for (int y = 0; y < fallingSize; ++y) {
	    for (int x = 0; x < fallingSize; ++x) {
		final int xBoard = fallingPos.x + x;
		final int yBoard = fallingPos.y + y;
		// Collision occurs if the falling coordinate are outside the board or the falling square and board square are not empty.
		if (isFallingOutsideBoard(x, y) || !(SquareType.isEmpty(tetrisBoard.getFallingSquare(x, y)) ||
						     SquareType.isEmpty(tetrisBoard.getSquare(xBoard, yBoard)))) {
		    return true;
		}
	    }
	}
	return false;
    }
}
