package se.liu.wilmi895.tetris;

import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyBinder
{
    private final Map<String, AbstractAction> keyBindings = getKeyBindingMap();
    private Board tetrisBoard;
    private InputMap inputMap;
    private ActionMap actionMap;

    public KeyBinder(final JFrame frame, final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	final JComponent pane = frame.getRootPane();
	this.inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	this.actionMap = pane.getActionMap();
    }

    public void initKeyBindings() {
	keyBindings.forEach((keyStroke, action) -> bindKey(keyStroke, action.toString(), action));
    }

    private void bindKey(final String keyStroke, final Object actionMapKey, final Action action) {
	inputMap.put(KeyStroke.getKeyStroke(keyStroke), actionMapKey);
	actionMap.put(actionMapKey, action);
    }

    private Map<String, AbstractAction> getKeyBindingMap() {
	final Map<String, AbstractAction> keyBindingMap = new HashMap<>();
	keyBindingMap.put("LEFT", new MoveAction(Direction.LEFT));
	keyBindingMap.put("RIGHT", new MoveAction(Direction.RIGHT));
	keyBindingMap.put("SPACE", new MoveAction(Direction.DOWN));
	keyBindingMap.put("DOWN", new RotateAction(Direction.LEFT));
	keyBindingMap.put("UP", new RotateAction(Direction.RIGHT));

	return keyBindingMap;
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
}
