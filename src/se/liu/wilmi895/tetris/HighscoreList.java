package se.liu.wilmi895.tetris;

import java.util.ArrayList;
import java.util.List;

public class HighscoreList
{
    private final List<Highscore> highscoreList;

    public HighscoreList(final List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }

    public void addHighscore(final Highscore highscore) {
        highscoreList.add(highscore);
    }

    public Highscore getHighscore(final int index) {
        if(index < 0 || index > getSize()) {
            throw new IllegalArgumentException("HighscoreList index out of bounds.");
        }
        return highscoreList.get(index);
    }

    public int getSize() {
        return highscoreList.size();
    }
}
