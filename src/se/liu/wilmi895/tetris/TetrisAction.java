package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.SortedMap;

public class TetrisAction
{
    private final AbstractAction quitAction = new QuitAction();
    private final AbstractAction restartAction = new RestartAction();
    private final TetrisViewer tetrisViewer;
    private final Board tetrisBoard;

    public TetrisAction(final TetrisViewer tetrisViewer, final Board tetrisBoard)
    {
	this.tetrisViewer = tetrisViewer;
	this.tetrisBoard = tetrisBoard;
    }

    public AbstractAction createAction(final GameAction gameAction, final Object... params) {
	// Check the number of arguments supplied.
	switch (gameAction) {
	    case MOVE, ROTATE, POWERUP -> throwIfMissingArgs(1, params, gameAction);
	}

	switch (gameAction) {
	    case MOVE:
		return new MoveAction((Direction) params[0]);
	    case ROTATE:
		return new RotateAction((Direction) params[0]);
	    case POWERUP:
		return new PowerUpAction((FallHandler) params[0]);
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

    private void throwIfMissingArgs(final int numRequiredArgs, final Object[] params, final GameAction gameAction)
    {
	if (numRequiredArgs < params.length) {
	    throw new IllegalArgumentException(
		    String.format("%d missing arguments for game action %s", params.length - numRequiredArgs,
				  gameAction.name()));
	}
    }

    private class PowerUpAction extends AbstractAction
    {
	private final FallHandler fallHandler;

	private PowerUpAction(final FallHandler fallHandler) {
	    this.fallHandler = fallHandler;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.setFallHandler(fallHandler);
	}
    }

    private class RestartAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    final boolean restart = tetrisViewer.showYesNoDialog("Restart game?", JOptionPane.QUESTION_MESSAGE);

	    if (!restart) {
		quitAction.actionPerformed(null);
	    }
	    tetrisViewer.resetClock();
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
	@Override public void actionPerformed(final ActionEvent e) {
	    tetrisBoard.pauseGame(true);

	    final boolean quit = tetrisViewer.showYesNoDialog("Quit game?", JOptionPane.QUESTION_MESSAGE);

	    if (quit) {
		System.exit(0);
	    }
	    tetrisBoard.pauseGame(false);
	}
    }

    private class PauseAction extends AbstractAction
    {
	private final SortedMap<Integer, String> pauseOptionMap =
		TetrisViewer.createOptionMap(new String[] { "Play", "Restart", "Quit" });

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

}
