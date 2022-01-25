package se.liu.wilmi895.tetris;

public class BoardTester
{
    public static void main(String[] args) {
	final Board board = new Board(10, 20);

	final BoardToTextConverter converter = new BoardToTextConverter();
	// Randomize board 10 times.
	for (int i = 0; i < 10; i++) {
	    board.randomizeBoard();
	    final String textBoard = converter.convertToText(board);
	    System.out.println(textBoard);
	}
    }
}
