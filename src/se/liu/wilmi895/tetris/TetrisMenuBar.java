package se.liu.wilmi895.tetris;

import javax.swing.*;

public class TetrisMenuBar
{
    private final TetrisAction tetrisAction;
    private JMenuBar menuBar = null;

    public TetrisMenuBar(final TetrisAction tetrisAction) {
	this.tetrisAction = tetrisAction;
    }

    public void initMenuBar() {
	menuBar = new JMenuBar();
	final JMenuItem quit = createMenuItem("quit", GameAction.QUIT);
	final JMenuItem togglePause = createMenuItem("pause", GameAction.PAUSE);
	menuBar.add(togglePause);
	menuBar.add(quit);
    }

    public JMenuBar getMenuBar() {
	return menuBar;
    }

    private JMenuItem createMenuItem(final String text, final GameAction action) {
	final JMenuItem menuItem = new JMenuItem(text, 0);
	menuItem.addActionListener(tetrisAction.createAction(action));
	return menuItem;
    }
}
