package se.liu.wilmi895.tetris;

import java.util.Comparator;

public class HighscoreComparator implements Comparator<Highscore>
{
    @Override public int compare(final Highscore o1, final Highscore o2) {
	final int o1Score = o1.getScore();
	final int o2Score = o2.getScore();

	if (o1Score > o2Score) {
	    return -1;
	} else if (o1Score < o2Score) {
	    return 1;
	}

	return 0;
    }
}
