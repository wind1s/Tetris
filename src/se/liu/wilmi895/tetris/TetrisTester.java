package se.liu.wilmi895.tetris;

public class TetrisTester
{
    public static void main(String[] args) {
	final Board board = new Board(10, 15);
	final TetrisViewer tetrisViewer = new TetrisViewer(board);
	tetrisViewer.initWindow();
	tetrisViewer.showWindow();
    }
}