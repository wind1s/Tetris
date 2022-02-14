package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.BorderLayout;
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
	initFrameSettings();
	initKeyBindings();

	tetrisBoard.addBoardListener(tetrisComponent);
	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		tetrisBoard.tick();
	    }
	};

	final Timer clockTimer = new Timer(750, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

    private void initFrameSettings() {
	frame.setLayout(new BorderLayout());
	frame.add(tetrisComponent, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
    }

    private void initKeyBindings() {
	JComponent pane = frame.getRootPane();
	final InputMap inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	inputMap.put(KeyStroke.getKeyStroke("LEFT"), "move_left");
	inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "move_right");

	final ActionMap actionMap = pane.getActionMap();
	actionMap.put("move_left", new MoveAction(Direction.LEFT));
	actionMap.put("move_right", new MoveAction(Direction.RIGHT));
    }

    private class MoveAction extends AbstractAction
    {
	private final Direction direction;

	private MoveAction(final Direction direction) {
	    this.direction = direction;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.move(direction);
	}
    }
}
