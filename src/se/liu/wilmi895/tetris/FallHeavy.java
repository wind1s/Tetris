package se.liu.wilmi895.tetris;

public class FallHeavy extends FallHandler
{
    public FallHeavy(final Board tetrisBoard) {
	super(tetrisBoard);
    }

    @Override public final boolean hasCollision() {
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
