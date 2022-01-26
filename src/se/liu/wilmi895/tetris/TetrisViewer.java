package se.liu.wilmi895.tetris;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;

public class TetrisViewer
{
    private Board tetrisBoard;

    public TetrisViewer(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
    }

    public void show() {
	final JFrame frame = new JFrame("Tetris");
	final JTextArea textArea = new JTextArea(tetrisBoard.getHeight(), tetrisBoard.getWidth());
	final BoardToTextConverter converter = new BoardToTextConverter();

	textArea.setText(converter.convertToText(tetrisBoard));
	frame.setLayout(new BorderLayout());
	frame.add(textArea, BorderLayout.CENTER);
	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
	frame.pack();
	frame.setVisible(true);
    }
}
