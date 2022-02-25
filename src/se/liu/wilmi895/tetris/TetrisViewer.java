package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

public class TetrisViewer
{
    private final Board tetrisBoard;
    private final String frameTitle;
    private final HighscoreList highscoreList;
    private final ScoreCounter scoreCounter = new ScoreCounter();
    private EnumMap<CardComponent, JComponent> componentMap = null;
    private CardComponent visibleComponent = CardComponent.NONE;
    private JFrame frame = null;
    private CardLayout frameLayout = null;
    private TetrisComponent tetrisComponent = null;
    private StartScreenComponent startScreenComponent = null;
    private HighscoreListComponent highscoreListComponent = null;
    private TetrisMenuBar tetrisMenuBar = null;
    private AbstractAction restartAction = null;

    public TetrisViewer(final String frameTitle, final Board tetrisBoard, final HighscoreList highscoreList) {
	this.tetrisBoard = tetrisBoard;
	this.frameTitle = frameTitle;
	this.highscoreList = highscoreList;
    }

    public enum CardComponent
    {
	NONE, START_SCREEN, TETRIS_BOARD, HIGHSCORE_LIST
    }

    public void initWindow() {
	frame = new JFrame(frameTitle);
	frameLayout = new CardLayout();
	tetrisComponent = new TetrisComponent(tetrisBoard, scoreCounter);
	startScreenComponent = new StartScreenComponent();
	highscoreListComponent = new HighscoreListComponent(tetrisBoard, highscoreList);

	TetrisAction tetrisAction = new TetrisAction(tetrisBoard, frame, frameTitle, scoreCounter);
	tetrisMenuBar = new TetrisMenuBar(tetrisAction);
	restartAction = tetrisAction.createAction(GameAction.RESTART);

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
		    String username = JOptionPane.showInputDialog("Enter your name to save your score");
		    if (username != null) {
			highscoreList.addHighscore(new Highscore(username, scoreCounter.getScore()));
		    }
		    showCardComponent(TetrisViewer.CardComponent.HIGHSCORE_LIST);
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

    /**
     * Creates the frame component mapping. Needs to be called after all components are allocated, otherwise the mapping
     * contains null components.
     *
     * @return void
     */
    private void createComponentMap() {
	componentMap = new EnumMap<>(CardComponent.class);
	componentMap.put(CardComponent.START_SCREEN, startScreenComponent);
	componentMap.put(CardComponent.TETRIS_BOARD, tetrisComponent);
	componentMap.put(CardComponent.HIGHSCORE_LIST, highscoreListComponent);
    }
}
