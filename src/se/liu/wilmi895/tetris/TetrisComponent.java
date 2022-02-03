package se.liu.wilmi895.tetris;

import javax.swing.JComponent;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent
{
    private final static int SQUARE_PIXEL_SIZE = 35;
    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();
    private Board board;

    public TetrisComponent(final Board board) {
	this.board = board;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(board.getWidth() * SQUARE_PIXEL_SIZE, board.getHeight() * SQUARE_PIXEL_SIZE);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	final int width = board.getWidth();
	final int height = board.getHeight();

	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		paintSquare(x, y, g2d);
	    }
	}
    }

    private void paintSquare(final int x, final int y, final Graphics2D g) {
	//final Dimension size = this.getSize();
	final int xDrawPos = x * SQUARE_PIXEL_SIZE;
	final int yDrawPos = y * SQUARE_PIXEL_SIZE;
	g.setColor(SQUARE_COLORS.get(board.getVisibleSquare(x, y)));
	g.fill3DRect(xDrawPos, yDrawPos, SQUARE_PIXEL_SIZE, SQUARE_PIXEL_SIZE, true);
	g.setColor(Color.BLACK);
	g.drawRect(xDrawPos, yDrawPos, SQUARE_PIXEL_SIZE, SQUARE_PIXEL_SIZE);
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
