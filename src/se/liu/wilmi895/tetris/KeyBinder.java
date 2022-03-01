package se.liu.wilmi895.tetris;

import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.JComponent;

public class KeyBinder
{
    private final InputMap inputMap;
    private final ActionMap actionMap;

    public KeyBinder(final JComponent pane) {
	this.inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	this.actionMap = pane.getActionMap();
    }

    public void initKeyBindings(final TetrisAction tetrisAction, final Board tetrisBoard) {
	bindKey("LEFT", tetrisAction.createAction(GameAction.MOVE, Direction.LEFT));
	bindKey("RIGHT", tetrisAction.createAction(GameAction.MOVE, Direction.RIGHT));
	bindKey("SPACE", tetrisAction.createAction(GameAction.MOVE, Direction.DOWN));
	bindKey("DOWN", tetrisAction.createAction(GameAction.ROTATE, Direction.LEFT));
	bindKey("UP", tetrisAction.createAction(GameAction.ROTATE, Direction.RIGHT));
	bindKey("ESCAPE", tetrisAction.createAction(GameAction.PAUSE));
	bindKey("F", tetrisAction.createAction(GameAction.POWERUP, new Fallthrough(tetrisBoard)));
	bindKey("H", tetrisAction.createAction(GameAction.POWERUP, new FallHeavy(tetrisBoard)));
	bindKey("D", tetrisAction.createAction(GameAction.POWERUP, new DefaultFallHandler(tetrisBoard)));
    }

    private void bindKey(final String keyStroke, final Action action) {
	final String actionMapKey = action.toString();
	inputMap.put(KeyStroke.getKeyStroke(keyStroke), actionMapKey);
	actionMap.put(actionMapKey, action);
    }
}
