package se.liu.wilmi895.tetris;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.SortedMap;

public class TetrisViewer
{
    private final Board tetrisBoard;
    private final String frameTitle;
    private final ScoreCounter scoreCounter = new ScoreCounter();
    private EnumMap<CardComponent, JComponent> componentMap = null;
    private CardComponent visibleComponent = CardComponent.NONE;
    private JFrame frame = null;
    private CardLayout frameLayout = null;
    private TetrisComponent tetrisComponent = null;
    private StartScreenComponent startScreenComponent = null;
    private HighscoreListComponent highscoreListComponent = null;
    private TetrisMenuBar tetrisMenuBar = null;
    private HighscoreList highscoreList = null;
    private AbstractAction restartAction = null;
    private AbstractAction quitAction = null;

    public TetrisViewer(final String frameTitle, final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.frameTitle = frameTitle;
    }

    public enum CardComponent
    {
	NONE, START_SCREEN, TETRIS_BOARD, HIGHSCORE_LIST
    }

    /**
     * Creates the frame component mapping. Needs to be called after all components are allocated, otherwise the mapping
     * contains null components.
     */
    private void createComponentMap() {
	componentMap = new EnumMap<>(CardComponent.class);
	componentMap.put(CardComponent.START_SCREEN, startScreenComponent);
	componentMap.put(CardComponent.TETRIS_BOARD, tetrisComponent);
	componentMap.put(CardComponent.HIGHSCORE_LIST, highscoreListComponent);
    }

    public void initWindow() {
	frame = new JFrame(frameTitle);
	frameLayout = new CardLayout();

	TetrisAction tetrisAction = new TetrisAction(this, tetrisBoard, scoreCounter);
	restartAction = tetrisAction.createAction(GameAction.RESTART);
	quitAction = tetrisAction.createAction(GameAction.QUIT);

	tryLoadHighscoreList();
	tetrisComponent = new TetrisComponent(tetrisBoard, scoreCounter);
	startScreenComponent = new StartScreenComponent();
	highscoreListComponent = new HighscoreListComponent(tetrisBoard, highscoreList);
	tetrisMenuBar = new TetrisMenuBar(tetrisAction);

	final KeyBinder keyBinder = new KeyBinder(frame, tetrisAction);
	keyBinder.initKeyBindings();

	tetrisBoard.addBoardListener(tetrisComponent);
	createComponentMap();
	initFrame();
    }

    public void showWindow() {
	frame.setVisible(true);
	showCardComponent(CardComponent.START_SCREEN);

	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		if (tetrisBoard.isGameOver()) {
		    trySaveHighscoreList();
		    showCardComponent(CardComponent.HIGHSCORE_LIST);
		    restartAction.actionPerformed(null);
		} else {
		    showCardComponent(CardComponent.TETRIS_BOARD);
		    tetrisBoard.tick();
		}
	    }
	};

	final Timer clockTimer = new Timer(750, doOneStep);
	clockTimer.setInitialDelay(StartScreenComponent.SHOW_TIME_MS);
	clockTimer.setCoalesce(true);
	clockTimer.start();

	// Tetris 5.7: Add an action listener to timer which notifies board about the timer to speed up tetris.
	// clockTimer.addActionListener();
    }

    private void initFrame() {
	addCardComponent(CardComponent.TETRIS_BOARD, BorderLayout.CENTER);
	addCardComponent(CardComponent.START_SCREEN, BorderLayout.CENTER);
	addCardComponent(CardComponent.HIGHSCORE_LIST, BorderLayout.CENTER);

	frame.setLayout(frameLayout);

	tetrisMenuBar.initMenuBar();
	frame.setJMenuBar(tetrisMenuBar.getMenuBar());
	frame.pack();
    }

    private void tryLoadHighscoreList() {
	while (true) {
	    String dialogMessage = null;

	    try {
		highscoreList = HighscoreList.readSavedJson();
	    } catch (IOException | JsonIOException ignored) {
		dialogMessage = "Could not read highscore list file.\nTry again?";
	    } catch (JsonSyntaxException ignored) {
		dialogMessage = "Highscore file is corrupt.\nTry again?";
	    }

	    if (dialogMessage != null) {
		showYesNoErrorDialog(dialogMessage);
	    } else {
		break;
	    }
	}
    }

    private void trySaveHighscoreList() {
	String username = JOptionPane.showInputDialog("Enter your name to save your score");

	if (username == null) {
	    return;
	}
	highscoreList.addHighscore(new Highscore(username, scoreCounter.getScore()));

	while (true) {
	    String dialogMessage = null;

	    try {
		highscoreList.saveToJson();
	    } catch (FileNotFoundException ignored) {
		dialogMessage = "Could not save highscore list.\nTry again?";
	    } catch (SecurityException ignored) {
		dialogMessage = "Access denied to highscore list file.\nTry again?";
	    }

	    if (dialogMessage != null) {
		showYesNoErrorDialog(dialogMessage);
	    } else {
		break;
	    }
	}
    }

    private void addCardComponent(final CardComponent component, final Object constraints) {
	final JComponent viewComponent = componentMap.get(component);
	frameLayout.addLayoutComponent(viewComponent, component.name());
	frame.add(viewComponent, constraints);
    }

    public void showCardComponent(final CardComponent component) {
	if (visibleComponent == component) {
	    return;
	}
	frameLayout.show(frame.getContentPane(), component.name());
	frame.setSize(componentMap.get(component).getPreferredSize());
	visibleComponent = component;
    }

    private void showYesNoErrorDialog(final String dialogMessage) {
	int optionChosen = showOptionDialog(dialogMessage, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION,
					    TetrisAction.YES_NO_OPTION_MAP, null);

	switch (getOptionString(optionChosen, TetrisAction.YES_NO_OPTION_MAP)) {
	    case "No" -> quitAction.actionPerformed(null);
	}
    }

    public int showOptionDialog(final String message, final int messageType, final int optionType,
				final Map<Integer, String> optionMap, final Icon icon)
    {
	return JOptionPane.showOptionDialog(frame, message, frameTitle, optionType, messageType, icon,
					    optionMap.values().toArray(), null);
    }

    public static boolean isOptionChosen(final String option, final int optionChosen,
					 final Map<String, Integer> optionMap)
    {
	return optionChosen == optionMap.get(option);
    }

    public static String getOptionString(final int optionChosen, final SortedMap<Integer, String> optionMap) {
	return optionMap.getOrDefault(optionChosen, "");
    }
}
