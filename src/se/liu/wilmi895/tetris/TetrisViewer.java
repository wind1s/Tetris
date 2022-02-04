package se.liu.wilmi895.tetris;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.Timer;
import javax.swing.Action;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class TetrisViewer
{
    private Board tetrisBoard;

    public TetrisViewer(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
    }

    public void show() {
	final JFrame frame = new JFrame("Tetris");
	final TetrisComponent tetrisComponent = new TetrisComponent(tetrisBoard);

	frame.setLayout(new BorderLayout());
	frame.add(tetrisComponent, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);

	tetrisBoard.addBoardListener(tetrisComponent);

	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		tetrisBoard.tick();
	    }
	};

	final Timer clockTimer = new Timer(1000, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
