package se.liu.wilmi895.tetris;

import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.JComponent;

public class KeyBinder
{
    private final GameAction gameAction;
    private final InputMap inputMap;
    private final ActionMap actionMap;

    public KeyBinder(final JFrame frame, final GameAction gameAction) {
	this.gameAction = gameAction;
	final JComponent pane = frame.getRootPane();
	this.inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	this.actionMap = pane.getActionMap();
    }

    public void initKeyBindings() {
	bindKey("LEFT", gameAction.createAction(KeyAction.MOVE, Direction.LEFT));
	bindKey("RIGHT", gameAction.createAction(KeyAction.MOVE, Direction.RIGHT));
	bindKey("SPACE", gameAction.createAction(KeyAction.MOVE, Direction.DOWN));
	bindKey("DOWN", gameAction.createAction(KeyAction.ROTATE, Direction.LEFT));
	bindKey("UP", gameAction.createAction(KeyAction.ROTATE, Direction.RIGHT));
	bindKey("ESCAPE", gameAction.createAction(KeyAction.PAUSE));
    }

    private void bindKey(final String keyStroke, final Action action) {
	final String actionMapKey = action.toString();
	inputMap.put(KeyStroke.getKeyStroke(keyStroke), actionMapKey);
	actionMap.put(actionMapKey, action);
    }
}
