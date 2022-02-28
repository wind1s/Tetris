package se.liu.wilmi895.tetris;

public class Fallthrough extends FallHandler
{
    public Fallthrough(final Board tetrisBoard) {
	super(tetrisBoard);
    }

    @Override public final boolean hasCollision() {
	final int fallingSize = tetrisBoard.getFallingSize();

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
