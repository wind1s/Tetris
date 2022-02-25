package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.*;

public class HighscoreListComponent extends JComponent
{
    private static final int SHOW_COUNT = 10;
    private static final double FONT_SCALE = 0.5         ;
    private final HighscoreList highscoreList;
    private final int boardWidth;
    private final int boardHeight;

    public HighscoreListComponent(final Board board, final HighscoreList highscoreList) {
	this.highscoreList = highscoreList;
	this.boardWidth = board.getWidth();
	this.boardHeight = board.getHeight();
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(boardWidth * TetrisComponent.SQUARE_SIZE, boardHeight * TetrisComponent.SQUARE_SIZE);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	final Dimension componentDimension = this.getSize();
	final int squareHeight = componentDimension.height / boardHeight;
	final int squareWidth = componentDimension.width / boardWidth;

	// Clamp the amount of highscores to show on screen.
	final int showCount = Math.min(highscoreList.getSize(), SHOW_COUNT);

	final int xDrawStart = squareWidth;
	final int yDrawStart = squareHeight;
	g2d.setFont(new Font("Monospaced", Font.PLAIN, (int)(squareHeight * FONT_SCALE)));
	g2d.drawString("Highscores", xDrawStart, yDrawStart);
	g2d.drawString("", xDrawStart, yDrawStart + yDrawStart / 2);

	for (int i = 0; i < showCount; i++) {
	    final Highscore highscore = highscoreList.getHighscore(i);
	    g2d.drawString(String.format("(%d) %s - %d", i, highscore.getName(), highscore.getScore()), xDrawStart,
			   yDrawStart * (i + 2));
	}
    }
}
