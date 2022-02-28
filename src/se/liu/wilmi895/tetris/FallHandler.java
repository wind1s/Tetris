package se.liu.wilmi895.tetris;

import java.awt.Point;

public abstract class FallHandler
{
    protected final Board tetrisBoard;

    protected FallHandler(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
    }

    public abstract boolean hasCollision();

    protected final boolean isFallingOutsideBoard(final int x, final int y) {
	final Point fallingPos = tetrisBoard.getFallingPos();
	final int xBoard = fallingPos.x + x;
	final int yBoard = fallingPos.y + y;
	return tetrisBoard.isOutsideBoard(xBoard, yBoard) && !SquareType.isEmpty(tetrisBoard.getFallingSquare(x, y));
    }
}