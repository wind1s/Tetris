package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.SortedMap;
import java.util.TreeMap;

public class TetrisAction
{
    public static final SortedMap<Integer, String> YES_NO_OPTION_MAP = createOptionMap(new String[] { "Yes", "No" });
    private final AbstractAction quitAction = new QuitAction();
    private final AbstractAction restartAction = new RestartAction();
    private final TetrisViewer tetrisViewer;
    private final Board tetrisBoard;
    private final ScoreCounter scoreCounter;

    public TetrisAction(final TetrisViewer tetrisViewer, final Board tetrisBoard, final ScoreCounter scoreCounter)
    {
	this.tetrisViewer = tetrisViewer;
	this.tetrisBoard = tetrisBoard;
	this.scoreCounter = scoreCounter;
    }

    public AbstractAction createAction(final GameAction gameAction, final Object... params) {
	// Check the number of arguments supplied.
	switch (gameAction) {
	    case MOVE, ROTATE -> checkCreateActionParams(1, params, gameAction);
	}

	switch (gameAction) {
	    case MOVE:
		return new MoveAction((Direction) params[0]);
	    case ROTATE:
		return new RotateAction((Direction) params[0]);
	    case QUIT:
		return new QuitAction();
	    case PAUSE:
		return new PauseAction();
	    case RESTART:
		return new RestartAction();
	    default:
		throw new IllegalArgumentException("Invalid game action: " + gameAction);
	}
    }

    private void checkCreateActionParams(final int numRequiredArgs, final Object[] params, final GameAction gameAction)
    {
	if (numRequiredArgs < params.length) {
	    throw new IllegalArgumentException(
		    String.format("%d missing arguments for game action %s", params.length - numRequiredArgs,
				  gameAction.name()));
	}
    }

    private class RestartAction extends AbstractAction
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    final boolean restart = showYesNoDialog("Restart game?", JOptionPane.QUESTION_MESSAGE);

	    if (restart) {
		scoreCounter.resetScore();
		tetrisBoard.restartGame();
	    }
	    quitAction.actionPerformed(null);
	}
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

	@Override public String toString() {
	    return GameAction.MOVE.name() + direction.name();
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

	@Override public String toString() {
	    return GameAction.ROTATE.name() + direction.name();
	}
    }

    private class QuitAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);

	    final boolean quit = showYesNoDialog("Quit game?", JOptionPane.QUESTION_MESSAGE);

	    if (quit) {
		System.exit(0);
	    }
	    tetrisBoard.pauseGame(false);
	}
    }

    private class PauseAction extends AbstractAction
    {
	private final SortedMap<Integer, String> pauseOptionMap =
		createOptionMap(new String[] { "Play", "Restart", "Quit" });

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);
	    int optionChosen = tetrisViewer.showOptionDialog("Game paused", JOptionPane.INFORMATION_MESSAGE,
							     JOptionPane.DEFAULT_OPTION, pauseOptionMap, null);

	    switch (TetrisViewer.getOptionString(optionChosen, pauseOptionMap)) {
		case "Restart" -> restartAction.actionPerformed(null);
		case "Quit" -> quitAction.actionPerformed(null);
	    }
	    tetrisBoard.pauseGame(false);
	}

    }

    private boolean showYesNoDialog(final String dialogMessage, final int messageType) {
	int optionChosen =
		tetrisViewer.showOptionDialog(dialogMessage, messageType, JOptionPane.DEFAULT_OPTION, YES_NO_OPTION_MAP,
					      null);

	switch (TetrisViewer.getOptionString(optionChosen, YES_NO_OPTION_MAP)) {
	    case "Yes":
		return true;
	    case "No":
	    default:
		return false;
	}
    }

    private static SortedMap<Integer, String> createOptionMap(final String[] options) {
	final SortedMap<Integer, String> optionMap = new TreeMap<>();

	for (int i = 0; i < options.length; ++i) {
	    optionMap.put(i, options[i]);
	}
	return optionMap;
    }
}
