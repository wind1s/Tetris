package se.liu.wilmi895.tetris;

import java.awt.*;

public class FallHeavy extends FallHandler
{
    public FallHeavy(final Board tetrisBoard) {
	super(tetrisBoard);
    }

    @Override public final boolean hasCollision() {
	final Point fallingPos = tetrisBoard.getFallingPos();
	final int fallingSize = tetrisBoard.getFallingSize();

	for (int y = 0; y < fallingSize; ++y) {
	    for (int x = 0; x < fallingSize; ++x) {
		if (isFallingOutsideBoard(x,y)) {
		    return true;
		}


	    }
	}
	return false;
    }
}
