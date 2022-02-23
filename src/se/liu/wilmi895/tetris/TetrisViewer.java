package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class TetrisViewer
{
    private final Board tetrisBoard;
    private final String frameTitle;
    private GameAction gameAction = null;
    private JFrame frame = null;
    private TetrisComponent tetrisComponent = null;

    public TetrisViewer(final String frameTitle, final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.frameTitle = frameTitle;
    }

    public void init() {
	frame = new JFrame(frameTitle);
	tetrisComponent = new TetrisComponent(tetrisBoard);
	gameAction = new GameAction(tetrisBoard, frame, frameTitle);

	final KeyBinder keyBinder = new KeyBinder(frame, gameAction);
	keyBinder.initKeyBindings();

	tetrisBoard.addBoardListener(tetrisComponent);
	initFrame();
    }

    public void show() {
	frame.setVisible(true);

	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		tetrisBoard.tick();
	    }
	};

	final Timer clockTimer = new Timer(750, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
	// Tetris 5.7: Add an action listener to timer which notifies board about the timer to speed up tetris.
	// clockTimer.addActionListener();
    }

    private void initFrame() {
	frame.setLayout(new BorderLayout());
	frame.add(tetrisComponent, BorderLayout.CENTER);
	final TetrisMenuBar tetrisMenuBar = new TetrisMenuBar(gameAction);
	tetrisMenuBar.initMenuBar();
	frame.setJMenuBar(tetrisMenuBar);
	frame.pack();
    }
}
