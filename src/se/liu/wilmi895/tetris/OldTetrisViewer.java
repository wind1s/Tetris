package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OldTetrisViewer
{
    private Board tetrisBoard;

    public OldTetrisViewer(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
    }

    public void show() {
	final JFrame frame = new JFrame("Tetris");
	final JTextArea textArea = new JTextArea(tetrisBoard.getHeight(), tetrisBoard.getWidth());
	final BoardToTextConverter converter = new BoardToTextConverter();

	textArea.setText(converter.convertToText(tetrisBoard));
	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
	frame.setLayout(new BorderLayout());
	frame.add(textArea, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);


	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		tetrisBoard.randomizeBoard();
		textArea.setText(converter.convertToText(tetrisBoard));
	    }
	};

	final Timer clockTimer = new Timer(500, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
