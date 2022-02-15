package se.liu.wilmi895.tetris;

import javax.swing.JFrame;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import javax.swing.JComponent;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class TetrisViewer
{
    private Board tetrisBoard;
    private JFrame frame = null;
    private String frameTitle;
    private TetrisComponent tetrisComponent;

    public TetrisViewer(final String frameTitle, final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.frameTitle = frameTitle;
	this.tetrisComponent = new TetrisComponent(tetrisBoard);
    }

    public void show() {
	frame = new JFrame(frameTitle);
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
	// Tetris 5.7: Add an action listener to timer which notifies board about the timer to speed up tetris.
	// clockTimer.addActionListener();
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
	inputMap.put(KeyStroke.getKeyStroke("SPACE"), "move_down");
	inputMap.put(KeyStroke.getKeyStroke("DOWN"), "rotate_left");
	inputMap.put(KeyStroke.getKeyStroke("UP"), "rotate_right");

	final ActionMap actionMap = pane.getActionMap();
	actionMap.put("move_left", new MoveAction(Direction.LEFT));
	actionMap.put("move_right", new MoveAction(Direction.RIGHT));
	actionMap.put("move_down", new MoveAction(Direction.DOWN));
	actionMap.put("rotate_left", new RotateAction(Direction.LEFT));
	actionMap.put("rotate_right", new RotateAction(Direction.RIGHT));
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

    private class RotateAction extends AbstractAction
    {
	private final Direction direction;

	private RotateAction(final Direction direction) {
	    this.direction = direction;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.rotate(direction);
	}

    }
}
