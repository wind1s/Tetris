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
	final JMenuItem quit = createMenuItem("Quit", GameAction.QUIT);
	final JMenuItem pause = createMenuItem("Pause", GameAction.PAUSE);
	final JMenuItem restart = createMenuItem("Restart", GameAction.RESTART);
	menuBar.add(pause);
	menuBar.add(restart);
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
