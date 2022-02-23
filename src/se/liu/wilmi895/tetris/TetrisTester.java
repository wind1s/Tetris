package se.liu.wilmi895.tetris;

import java.util.ArrayList;

public class TetrisTester
{
    public static void main(String[] args) {
	final HighscoreList highscoreList = new HighscoreList(new ArrayList<>()); // Supply list of highscores to retrieve them from json file.
	final Board board = new Board(10, 15);
	final TetrisViewer tetrisViewer = new TetrisViewer("Tetris", board, highscoreList);
	tetrisViewer.initWindow();
	try {
	    tetrisViewer.showWindow();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
