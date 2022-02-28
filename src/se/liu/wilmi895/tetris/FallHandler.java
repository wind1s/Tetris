package se.liu.wilmi895.tetris;

import java.awt.Point;

public abstract class FallHandler
{
    protected final Board tetrisBoard;
    protected final Point fallingPos;

    protected FallHandler(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.fallingPos = tetrisBoard.getFallingPos();
    }

    public final boolean hasCollision() {
	final int fallingSize = tetrisBoard.getFallingSize();

	for (int x = 0; x < fallingSize; ++x) {
	    for (int y = 0; y < fallingSize; ++y) {
		if (isFallingCollision(x, y)) {
		    return true;
		}
	    }
	}

	return false;
    }

    protected final boolean isFallingOutsideBoard(final int x, final int y) {
	final int xBoard = fallingPos.x + x;
	final int yBoard = fallingPos.y + y;
	return tetrisBoard.isOutsideBoard(xBoard, yBoard) && !SquareType.isEmpty(tetrisBoard.getFallingSquare(x, y));
    }

    protected abstract boolean isFallingCollision(final int x, final int y);
}