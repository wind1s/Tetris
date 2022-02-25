package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener
{
    public final static int SQUARE_SIZE = 50;
    private final EnumMap<SquareType, Color> squareColors = createColorMap();
    private final Board tetrisBoard;
    private final ScoreCounter scoreCounter;
    private final int boardWidth;
    private final int boardHeight;

    public TetrisComponent(final Board board, final ScoreCounter scoreCounter) {
	this.tetrisBoard = board;
	this.scoreCounter = scoreCounter;
	this.boardWidth = board.getWidth();
	this.boardHeight = board.getHeight();
    }

    @Override public void boardChanged() {
	repaint();
	scoreCounter.increaseScore(tetrisBoard.rowsLastRemoved());
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(boardWidth * SQUARE_SIZE, boardHeight * SQUARE_SIZE);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	final Dimension componentDimension = this.getSize();
	final int squareHeight = componentDimension.height / boardHeight;
	final int squareWidth = componentDimension.width / boardWidth;

	for (int y = 0; y < boardHeight; ++y) {
	    for (int x = 0; x < boardWidth; ++x) {
		paintSquare(x, y, squareWidth, squareHeight, g2d);
	    }
	}

	g2d.setColor(Color.CYAN);
	g2d.setFont(new Font("Monospaced", Font.PLAIN,  squareWidth / 3));
	g2d.drawString(String.format("Score: %d", scoreCounter.getScore()), squareWidth, squareHeight);
    }

    private void paintSquare(final int x, final int y, final int squareWidth, final int squareHeight,
			     final Graphics2D g)
    {
	final int xDrawPos = x * squareWidth;
	final int yDrawPos = y * squareHeight;

	g.setColor(squareColors.get(tetrisBoard.getVisibleSquare(x, y)));
	g.fill3DRect(xDrawPos, yDrawPos, squareWidth, squareHeight, true);
	g.setColor(Color.BLACK);
	g.drawRect(xDrawPos, yDrawPos, squareWidth, squareHeight);
    }

    private static EnumMap<SquareType, Color> createColorMap() {
	final EnumMap<SquareType, Color> colorMap = new EnumMap<>(SquareType.class);
	colorMap.put(SquareType.EMPTY, Color.BLACK);
	colorMap.put(SquareType.I, Color.CYAN);
	colorMap.put(SquareType.O, Color.YELLOW);
	colorMap.put(SquareType.T, Color.PINK);
	colorMap.put(SquareType.S, Color.GREEN);
	colorMap.put(SquareType.Z, Color.RED);
	colorMap.put(SquareType.J, Color.BLUE);
	colorMap.put(SquareType.L, Color.ORANGE);

	return colorMap;
    }
}
