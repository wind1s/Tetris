package se.liu.wilmi895.tetris;

public class BoardToTextConverter
{
    public String convertToText(final Board board) {
	StringBuilder builder = new StringBuilder();
	final int height = board.getHeight();
	final int width = board.getWidth();

	// Check each square for its type and append its string representation.
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		final SquareType square = board.getVisibleSquare(x, y);

		switch (square) {
		    case EMPTY -> builder.append("_");
		    case I -> builder.append("@");
		    case J -> builder.append("#");
		    case L -> builder.append("$");
		    case O -> builder.append("%");
		    case S -> builder.append("&");
		    case T -> builder.append("=");
		    case Z -> builder.append("*");
		    default -> throw new IllegalArgumentException("Illegal SquareType: " + square);
		}

		builder.append(" ");
	    }

	    builder.append("\n");
	}

	return builder.toString();
    }
}
