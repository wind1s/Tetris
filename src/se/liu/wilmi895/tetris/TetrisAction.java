package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TetrisAction
{
    private final AbstractAction quitAction = new QuitAction();
    private final AbstractAction restartAction = new RestartAction();
    private final Board tetrisBoard;
    private final JFrame frame;
    private final String frameTitle;
    private final ScoreCounter scoreCounter;

    public TetrisAction(Board tetrisBoard, final JFrame frame, final String frameTitle, final ScoreCounter scoreCounter)
    {
	this.tetrisBoard = tetrisBoard;
	this.frame = frame;
	this.frameTitle = frameTitle;
	this.scoreCounter = scoreCounter;
    }

    public AbstractAction createAction(final GameAction action, final Object... params) {
	switch (action) {
	    case MOVE:
		badVarArgsParameter(1, params, "MoveAction");
		return new MoveAction((Direction) params[0]);
	    case ROTATE:
		badVarArgsParameter(1, params, "RotateAction");
		return new RotateAction((Direction) params[0]);
	    case QUIT:
		return new QuitAction();
	    case PAUSE:
		return new PauseAction();
	    case RESTART:
		return new RestartAction();
	    default:
		throw new IllegalArgumentException("Invalid movement action: " + action);
	}
    }

    private void badVarArgsParameter(final int numRequiredArgs, final Object[] params, final String actionName) {
	if (numRequiredArgs < params.length) {
	    throw new IllegalArgumentException(
		    String.format("%d missing arguments for %s construction", params.length - numRequiredArgs,
				  actionName));
	}
    }

    private class RestartAction extends AbstractAction
    {

	private final SortedMap<Integer, String> restartOptionMap = createOptionMap(new String[] { "Yes", "No" });

	@Override public void actionPerformed(final ActionEvent e) {
	    int optionChosen =
		    showOptionDialog("Restart game?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
				     restartOptionMap, null);

	    switch (getOptionString(optionChosen, restartOptionMap)) {
		case "No" -> quitAction.actionPerformed(null);
	    }

	    scoreCounter.resetScore();
	    tetrisBoard.restartGame();
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
	private final SortedMap<Integer, String> quitOptionMap = createOptionMap(new String[] { "Yes", "No" });

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);
	    int optionChosen = showOptionDialog("Quit game?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
						quitOptionMap, null);

	    switch (getOptionString(optionChosen, quitOptionMap)) {
		case "Yes" -> System.exit(0);
		default -> tetrisBoard.pauseGame(false);
	    }
	}
    }

    private class PauseAction extends AbstractAction
    {
	private final SortedMap<Integer, String> pauseOptionMap =
		createOptionMap(new String[] { "Play", "Restart", "Quit" });

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);
	    int optionChosen =
		    showOptionDialog("Game paused", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				     pauseOptionMap, null);

	    switch (getOptionString(optionChosen, pauseOptionMap)) {
		case "Restart" -> restartAction.actionPerformed(null);
		case "Quit" -> quitAction.actionPerformed(null);
		default -> tetrisBoard.pauseGame(false);
	    }
	}
    }

    private SortedMap<Integer, String> createOptionMap(final String[] options) {
	final SortedMap<Integer, String> optionMap = new TreeMap<>();

	for (int i = 0; i < options.length; ++i) {
	    optionMap.put(i, options[i]);
	}
	return optionMap;
    }

    private boolean isOptionChosen(final String option, final int optionChosen, final Map<String, Integer> optionMap)
    {
	return optionChosen == optionMap.get(option);
    }

    public int showOptionDialog(final String message, final int messageType, final int optionType,
				final Map<Integer, String> optionMap, final Icon icon)
    {
	return JOptionPane.showOptionDialog(frame, message, frameTitle, optionType, messageType, icon,
					    optionMap.values().toArray(), null);
    }

    private String getOptionString(final int optionChosen, final SortedMap<Integer, String> optionMap) {
	return optionMap.getOrDefault(optionChosen, "");
    }
}
