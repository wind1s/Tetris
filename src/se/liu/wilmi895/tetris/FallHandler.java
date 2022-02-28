package se.liu.wilmi895.tetris;

import java.awt.Point;

public abstract class FallHandler
{
    protected final Board tetrisBoard;
    protected final Point fallingPos;
    protected final int fallingSize;

    protected FallHandler(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.fallingPos = tetrisBoard.getFallingPos();
	this.fallingSize = tetrisBoard.getFallingSize();
    }

    public abstract boolean hasCollision();

    protected final boolean isFallingOutsideBoard(final int x, final int y) {
	final int xBoard = fallingPos.x + x;
	final int yBoard = fallingPos.y + y;
	return tetrisBoard.isOutsideBoard(xBoard, yBoard) && !SquareType.isEmpty(tetrisBoard.getFallingSquare(x, y));
    }
}