package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TetrisMenuBar extends JMenuBar
{
    private JFrame frame;
    private String frameTitle;

    public TetrisMenuBar(final JFrame frame, final String frameTitle) {
	this.frame = frame;
	this.frameTitle = frameTitle;
    }

    public void initMenuBar() {
	final JMenuItem quit = createMenuItem("quit", 0, new QuitAction());
	add(quit);
    }

    private JMenuItem createMenuItem(final String text, final int mnemonic, final Action action) {
	final JMenuItem menuItem = new JMenuItem(text, mnemonic);
	menuItem.addActionListener(action);
	return menuItem;
    }

    private class QuitAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    final String[] quitOptions = { "Continue playing", "Quit" };
	    int optionChosen =
		    JOptionPane.showOptionDialog(frame, "Quit Tetris?", frameTitle, JOptionPane.YES_NO_CANCEL_OPTION,
						 JOptionPane.QUESTION_MESSAGE, null, quitOptions, quitOptions[0]);

	    if (optionChosen == 1) {
		System.exit(0);
	    }
	}
    }
}
