package se.liu.wilmi895.tetris;

public class TetrisTester
{
    public static void main(String[] args) {
	final ScoreCounter scoreCounter = new ScoreCounter();
	final Board board = new Board(10, 15, scoreCounter);
	final TetrisViewer tetrisViewer = new TetrisViewer(board, scoreCounter);
	tetrisViewer.initWindow();
	tetrisViewer.showWindow();
    }
}