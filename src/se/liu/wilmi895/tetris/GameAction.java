package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class GameAction
{
    private final Board tetrisBoard;
    private final JFrame frame;
    private final String frameTitle;

    public GameAction(final Board tetrisBoard, final JFrame frame, final String frameTitle) {
	this.tetrisBoard = tetrisBoard;
	this.frame = frame;
	this.frameTitle = frameTitle;
    }

    public AbstractAction createAction(final KeyAction action, final Object... params) {
	switch (action) {
	    case MOVE:
		return new MoveAction((Direction) params[0]);
	    case ROTATE:
		return new RotateAction((Direction) params[0]);
	    case QUIT:
		return new QuitAction();
	    case PAUSE:
		return new PauseAction();
	    default:
		throw new IllegalArgumentException("Invalid movement action: " + action.name());
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
	    return "MOVE_" + direction.name();
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
	    return "ROTATE_" + direction.name();
	}
    }

    private class QuitAction extends AbstractAction
    {
	private final SortedMap<Integer, String> quitOptionMap = createOptionMap(new String[] { "Yes", "No" });

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);
	    int optionChosen = showOptionDialog("Quit game?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
						quitOptionMap, null);

	    switch (quitOptionMap.getOrDefault(optionChosen, "")) {
		case "Yes" -> System.exit(0);
		default -> tetrisBoard.pauseGame(false);
	    }
	}
    }

    private class PauseAction extends AbstractAction
    {
	private final SortedMap<Integer, String> pauseOptionMap = createOptionMap(new String[] { "Play", "Quit" });
	private final AbstractAction quitAction = new QuitAction();

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);
	    int optionChosen =
		    showOptionDialog("Game paused", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				     pauseOptionMap, null);
	    
	    switch (pauseOptionMap.getOrDefault(optionChosen, "")) {
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
}
