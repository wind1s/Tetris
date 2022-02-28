package se.liu.wilmi895.tetris;

public class HeavyFall extends FallHandler
{
    @Override protected boolean isFallingCollision(final int x, final int y, final Board tetrisBoard) {

	return isFallingOutsideBoard(x,y,tetrisBoard);
    }
}
