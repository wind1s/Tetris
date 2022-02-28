package se.liu.wilmi895.tetris;

import java.awt.Point;

public class Fallthrough extends FallHandler
{
    @Override protected boolean isFallingCollision(final int x, final int y, final Board tetrisBoard) {
	return isFallingOutsideBoard(x,y,tetrisBoard);
    }
}
