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
    private JFrame frame;
    private TetrisComponent tetrisComponent;

    public TetrisViewer(final String frameTitle, final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.frame = new JFrame(frameTitle);
	this.tetrisComponent = new TetrisComponent(tetrisBoard);
    }

    public void show() {
	frame.setLayout(new BorderLayout());
	frame.add(tetrisComponent, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);

	tetrisBoard.addBoardListener(tetrisComponent);

	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		tetrisBoard.tick();
	    }
	};

	final Timer clockTimer = new Timer(1000, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
