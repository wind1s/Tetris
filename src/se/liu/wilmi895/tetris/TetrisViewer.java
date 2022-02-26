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
    private static final int NORMAL_TICK_SPEED_MS = 1000;
    private static final String FRAME_TITLE = "Tetris";
    private final Board tetrisBoard;
    private final ScoreCounter scoreCounter = new ScoreCounter();
    private final CardLayout frameLayout = new CardLayout();
    private CardComponent visibleComponent = CardComponent.NONE;
    private Timer clockTimer = null;
    private EnumMap<CardComponent, JComponent> componentMap = null;
    private JFrame frame = null;
    private TetrisComponent tetrisComponent = null;
    private StartScreenComponent startScreenComponent = null;
    private HighscoreListComponent highscoreListComponent = null;
    private TetrisMenuBar tetrisMenuBar = null;
    private HighscoreList highscoreList = null;
    private AbstractAction restartAction = null;
    private AbstractAction quitAction = null;

    public TetrisViewer(final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
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
	tryLoadHighscoreList();

	TetrisAction tetrisAction = new TetrisAction(this, tetrisBoard, scoreCounter);
	restartAction = tetrisAction.createAction(GameAction.RESTART);
	quitAction = tetrisAction.createAction(GameAction.QUIT);

	tetrisComponent = new TetrisComponent(tetrisBoard, scoreCounter);
	startScreenComponent = new StartScreenComponent();
	highscoreListComponent = new HighscoreListComponent(tetrisBoard, highscoreList);
	tetrisMenuBar = new TetrisMenuBar(tetrisAction);
	tetrisBoard.addBoardListener(tetrisComponent);

	frame = new JFrame(FRAME_TITLE);
	final KeyBinder keyBinder = new KeyBinder(frame, tetrisAction);
	keyBinder.initKeyBindings();
	initFrame();
    }

    public void showWindow() {
	frame.setVisible(true);
	showCardComponent(CardComponent.START_SCREEN);

	clockTimer = new Timer(NORMAL_TICK_SPEED_MS, new TickAction());
	clockTimer.setInitialDelay(StartScreenComponent.SHOW_TIME_MS);
	clockTimer.setCoalesce(true);
	clockTimer.start();

	// Tetris 5.7: Add an action listener to timer which notifies board about the timer to speed up tetris.
	// clockTimer.addActionListener();
    }

    private class TickAction extends AbstractAction
    {
	private static final int TICK_SPEED_UP_INTERVAL_MS = 1000;
	private static final int TICK_SPEED_UP_MS = 100;
	private static final int MIN_TICK_DELAY_MS = 100;
	private int tickCount = 0;

	@Override public void actionPerformed(ActionEvent e) {
	    if (tetrisBoard.isGameOver()) {
		trySaveHighscoreList();
		showCardComponent(CardComponent.HIGHSCORE_LIST);
		restartAction.actionPerformed(null);
	    } else {
		showCardComponent(CardComponent.TETRIS_BOARD);
		tetrisBoard.tick();
	    }

	    final int tickDelay = clockTimer.getDelay();
	    ++tickCount;

	    if((tickDelay * tickCount) >= TICK_SPEED_UP_INTERVAL_MS) {
		clockTimer.setDelay(Math.max(MIN_TICK_DELAY_MS, tickDelay - TICK_SPEED_UP_MS)) ;
		tickCount = 0;
	    }
	}
    };

    public void resetTickDelay() {
	clockTimer.setDelay(NORMAL_TICK_SPEED_MS);
    }

    private void initFrame() {
	createComponentMap();
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
		dialogMessage = "Could not read %s\n%s";
	    } catch (JsonSyntaxException ignored) {
		dialogMessage = "Highscore file %s is corrupt\n%s";
	    }

	    if (dialogMessage != null) {
		showYesNoErrorDialog(String.format(dialogMessage, HighscoreList.FILE_NAME, "Try again?"));
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
		dialogMessage = "Could not save %s\n%s";
	    } catch (SecurityException ignored) {
		dialogMessage = "Access denied to %s\n%s";
	    } catch (IOException ignored) {
		dialogMessage = "Unknown I/O error occurred with %s\n%s";
	    }

	    if (dialogMessage != null) {
		showYesNoErrorDialog(String.format(dialogMessage, HighscoreList.FILE_NAME, "Try again?"));
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
	return JOptionPane.showOptionDialog(frame, message, FRAME_TITLE, optionType, messageType, icon,
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
