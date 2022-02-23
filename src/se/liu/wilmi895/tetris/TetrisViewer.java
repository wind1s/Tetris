package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;

public class TetrisViewer
{
    private final Board tetrisBoard;
    private final String frameTitle;
    private EnumMap<FrameComponent, JComponent> componentMap = null;
    private FrameComponent visibleComponent = FrameComponent.NONE;
    private JFrame frame = null;
    private CardLayout frameLayout = null;
    private TetrisComponent tetrisComponent = null;
    private StartScreenComponent startScreenComponent = null;
    // private HighscoreListComponent highscoreListComponent = null;
    private TetrisMenuBar tetrisMenuBar = null;

    public TetrisViewer(final String frameTitle, final Board tetrisBoard) {
	this.tetrisBoard = tetrisBoard;
	this.frameTitle = frameTitle;
    }

    public enum FrameComponent
    {
	NONE, START_SCREEN, TETRIS_BOARD, HIGHSCORE_LIST
    }

    public void initWindow() {
	frame = new JFrame(frameTitle);
	frameLayout = new CardLayout();
	tetrisComponent = new TetrisComponent(tetrisBoard);
	startScreenComponent = new StartScreenComponent();
	//highscoreListComponent = new HighscoreListComponent();

	TetrisAction tetrisAction = new TetrisAction(tetrisBoard, frame, frameTitle);
	tetrisMenuBar = new TetrisMenuBar(tetrisAction);

	final KeyBinder keyBinder = new KeyBinder(frame, tetrisAction);
	keyBinder.initKeyBindings();

	tetrisBoard.addBoardListener(tetrisComponent);
	createComponentMap();
	initFrame();
    }

    public void showWindow() {
	frame.setVisible(true);
	showFrameComponent(FrameComponent.START_SCREEN);

	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		showFrameComponent(FrameComponent.TETRIS_BOARD);
		tetrisBoard.tick();
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
	addFrameComponent(FrameComponent.TETRIS_BOARD, BorderLayout.CENTER);
	addFrameComponent(FrameComponent.START_SCREEN, BorderLayout.CENTER);

	frame.setLayout(frameLayout);

	tetrisMenuBar.initMenuBar();
	frame.setJMenuBar(tetrisMenuBar.getMenuBar());
	frame.pack();
    }

    private void addFrameComponent(final FrameComponent component, final Object constraints) {
	final JComponent viewComponent = componentMap.get(component);
	frameLayout.addLayoutComponent(viewComponent, component.name());
	frame.add(viewComponent, constraints);
    }

    public void showFrameComponent(final FrameComponent component) {
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
	componentMap = new EnumMap<>(FrameComponent.class);
	componentMap.put(FrameComponent.START_SCREEN, startScreenComponent);
	componentMap.put(FrameComponent.TETRIS_BOARD, tetrisComponent);
	// componentMap.put(FrameComponent.HIGHSCORE_LIST, highscoreListComponent);
    }
}
