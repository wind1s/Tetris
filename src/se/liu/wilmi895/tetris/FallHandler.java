package se.liu.wilmi895.tetris;

import java.awt.*;

public abstract class FallHandler
{
    public final boolean hasCollision(final Board tetrisBoard) {
	final int fallingSize = tetrisBoard.getFallingSize();

	for (int x = 0; x < fallingSize; ++x) {
	    for (int y = 0; y < fallingSize; ++y) {
		if (isFallingCollision(x, y, tetrisBoard)) {
		    return true;
		}
	    }
	}

	return false;
    }

    protected final boolean isFallingOutsideBoard(final int x, final int y, final Board tetrisBoard) {
	final Point fallingPos = tetrisBoard.getFallingPos();
	final int xBoard = fallingPos.x + x;
	final int yBoard = fallingPos.y + y;
	return tetrisBoard.isOutsideBoard(xBoard, yBoard) && !SquareType.isEmpty(tetrisBoard.getFallingSquare(x, y));
    }

    protected abstract boolean isFallingCollision(final int x, final int y, final Board tetrisBoard);
}