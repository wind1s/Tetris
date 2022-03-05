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
import java.util.TreeMap;

public class TetrisViewer
{
    private static final int NORMAL_TICK_SPEED_MS = 1000;
    private static final String FRAME_TITLE = "Tetris";
    private final SortedMap<Integer, String> yesNoOptionMap = createOptionMap(new String[] { "Yes", "No" });
    private final Board tetrisBoard;
    private final ScoreCounter scoreCounter;
    private final CardLayout frameLayout = new CardLayout();
    private CardComponent visibleComponent = CardComponent.NONE;
    private Timer clock = null;
    private EnumMap<CardComponent, JComponent> componentMap = null;
    private JFrame frame = null;
    private TetrisComponent tetrisComponent = null;
    private StartScreenComponent startScreenComponent = null;
    private HighscoreListComponent highscoreListComponent = null;
    private TetrisMenuBar tetrisMenuBar = null;
    private HighscoreList highscoreList = null;
    private AbstractAction restartAction = null;
    private AbstractAction quitAction = null;

    public TetrisViewer(final Board tetrisBoard, final ScoreCounter scoreCounter) {
	this.tetrisBoard = tetrisBoard;
	this.scoreCounter = scoreCounter;
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

	TetrisAction tetrisAction = new TetrisAction(this, tetrisBoard);
	restartAction = tetrisAction.createAction(GameAction.RESTART);
	quitAction = tetrisAction.createAction(GameAction.QUIT);

	tetrisComponent = new TetrisComponent(tetrisBoard, scoreCounter);
	startScreenComponent = new StartScreenComponent();
	highscoreListComponent = new HighscoreListComponent(tetrisBoard, highscoreList);
	tetrisMenuBar = new TetrisMenuBar(tetrisAction);
	tetrisBoard.addBoardListener(tetrisComponent);

	frame = new JFrame(FRAME_TITLE);
	final KeyBinder keyBinder = new KeyBinder(frame.getRootPane());
	keyBinder.initKeyBindings(tetrisAction, tetrisBoard);
	initFrame();
    }

    public void showWindow() {
	frame.setVisible(true);
	showCardComponent(CardComponent.START_SCREEN);

	clock = new Timer(NORMAL_TICK_SPEED_MS, new TickAction());
	clock.setInitialDelay(StartScreenComponent.SHOW_TIME_MS);
	clock.setCoalesce(true);
	clock.start();
    }

    private class TickAction extends AbstractAction
    {
	private static final int TICK_SPEED_UP_INTERVAL_MS = 20000;
	private static final int TICK_SPEED_UP_MS = 100;
	private static final int MIN_TICK_DELAY_MS = 150;
	private int totalTicks = 0;

	@Override public void actionPerformed(ActionEvent e) {
	    if (tetrisBoard.isGameOver()) {
		trySaveHighscoreList();
		showCardComponent(CardComponent.HIGHSCORE_LIST);
		restartAction.actionPerformed(null);
	    } else {
		showCardComponent(CardComponent.TETRIS_BOARD);
		tetrisBoard.tick();
		speedUpGame();
	    }
	}

	private void speedUpGame() {
	    final int tickDelay = clock.getDelay();
	    ++totalTicks;

	    if ((tickDelay * totalTicks) >= TICK_SPEED_UP_INTERVAL_MS) {
		clock.setDelay(Math.max(MIN_TICK_DELAY_MS, tickDelay - TICK_SPEED_UP_MS));
	    }
	}
    }

    public void resetClock() {
	clock.setDelay(NORMAL_TICK_SPEED_MS);
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
	String username = JOptionPane.showInputDialog(
		"Enter your name to save your score\n(empty and blank names are not saved)");

	if (username == null || username.isEmpty() || username.isBlank()) {
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

    public void showYesNoErrorDialog(final String dialogMessage) {
	final boolean quit = !showYesNoDialog(dialogMessage, JOptionPane.ERROR_MESSAGE);

	if (quit) {
	    quitAction.actionPerformed(null);
	}
    }

    public boolean showYesNoDialog(final String dialogMessage, final int messageType) {
	int optionChosen =
		showOptionDialog(dialogMessage, messageType, JOptionPane.DEFAULT_OPTION, yesNoOptionMap, null);

	switch (TetrisViewer.getOptionString(optionChosen, yesNoOptionMap)) {
	    case "Yes":
		return true;
	    case "No":
	    default:
		return false;
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


    public static SortedMap<Integer, String> createOptionMap(final String[] options) {
	final SortedMap<Integer, String> optionMap = new TreeMap<>();

	for (int i = 0; i < options.length; ++i) {
	    optionMap.put(i, options[i]);
	}
	return optionMap;
    }
}
