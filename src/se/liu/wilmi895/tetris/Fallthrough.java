package se.liu.wilmi895.tetris;

import java.awt.Point;

public class Fallthrough extends FallHandler
{
    public Fallthrough(final Board tetrisBoard) {
	super(tetrisBoard);
    }

    @Override protected boolean isFallingCollision(final int x, final int y) {
	return isFallingOutsideBoard(x,y);
    }
}
