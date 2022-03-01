package se.liu.wilmi895.tetris;

import java.awt.Point;

public abstract class FallHandler
{
    protected final Board board;
    protected Point fallingPos = null;
    protected int fallingSize = 0;

    protected FallHandler(final Board board) {
	this.board = board;
    }

    public final boolean hasCollision(final Point oldFallingPos) {
	updateFallingPos();
	updateFallingSize();
	return abstractCollision(oldFallingPos);
    }

    protected abstract boolean abstractCollision(final Point oldFallingPos);

    protected final void updateFallingPos() {
	fallingPos = board.getFallingPos();
    }

    protected final void updateFallingSize() {
	fallingSize = board.getFallingSize();
    }

    protected final boolean isFallingOutsideBoard(final int x, final int y) {
	final int xBoard = fallingPos.x + x;
	final int yBoard = fallingPos.y + y;
	return board.isOutsideBoard(xBoard, yBoard) && !board.isFallingSquareEmpty(x, y);
    }

    protected final boolean isFallingSquareCollision(final int x, final int y) {
	final int xBoard = fallingPos.x + x;
	final int yBoard = fallingPos.y + y;
	return !board.isFallingSquareEmpty(x, y) && !board.isSquareEmpty(xBoard, yBoard);
    }
}