package se.liu.wilmi895.tetris;

import javax.swing.JComponent;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener
{
    private final static int SQUARE_PIXEL_SIZE = 35;
    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();
    private Board board;
    private int boardWidth;
    private int boardHeight;

    public TetrisComponent(final Board board) {
	this.board = board;
	this.boardWidth = board.getWidth();
	this.boardHeight = board.getHeight();
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(board.getWidth() * SQUARE_PIXEL_SIZE, board.getHeight() * SQUARE_PIXEL_SIZE);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	final Dimension componentDimension = this.getSize();
	final int squareHeight = componentDimension.height / boardHeight;
	//final int squareWidth = componentDimension.width / boardWidth;
	final int squareWidth = squareHeight;

	for (int x = 0; x < boardWidth; x++) {
	    for (int y = 0; y < boardHeight; y++) {
		paintSquare(x, y, squareWidth, squareHeight, g2d);
	    }
	}
    }

    private void paintSquare(final int x, final int y, final int squareWidth, final int squareHeight,
			     final Graphics2D g)
    {
	final int xDrawPos = x * squareWidth;
	final int yDrawPos = y * squareHeight;

	g.setColor(SQUARE_COLORS.get(board.getVisibleSquare(x, y)));
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

    @Override public void boardChanged() {
	repaint();
    }
}
