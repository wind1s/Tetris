package se.liu.wilmi895.tetris;

public class Tester
{
    public static void main(String[] args) {
	final Board board = new Board(10, 15);
	final TetrisViewer tetrisViewer = new TetrisViewer("Tetris", board);
	tetrisViewer.show();
    }
}
