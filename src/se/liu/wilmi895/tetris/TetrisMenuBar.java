package se.liu.wilmi895.tetris;

import javax.swing.*;

public class TetrisMenuBar extends JMenuBar
{
    private final GameAction gameAction;

    public TetrisMenuBar(final GameAction gameAction) {
	this.gameAction = gameAction;
    }

    public void initMenuBar() {
	final JMenuItem quit = createMenuItem("quit", KeyAction.QUIT);
	final JMenuItem togglePause = createMenuItem("pause", KeyAction.PAUSE);
	add(togglePause);
	add(quit);
    }

    private JMenuItem createMenuItem(final String text, final KeyAction action) {
	final JMenuItem menuItem = new JMenuItem(text, 0);
	menuItem.addActionListener(gameAction.createAction(action));
	return menuItem;
    }
}
