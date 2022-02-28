package se.liu.wilmi895.tetris;

public class HeavyFall extends FallHandler
{
    public HeavyFall(final Board tetrisBoard) {
	super(tetrisBoard);
    }

    @Override protected boolean isFallingCollision(final int x, final int y) {
	if(isFallingOutsideBoard(x,y)) {
	    return true;
	}

	return false;
    }
}
