package se.liu.wilmi895.tetris;

import java.util.HashMap;
import java.util.Map;

public class ScoreCounter
{
    private final Map<Integer, Integer> scoreMap = createScoreMap();
    private int score = 0;

    private enum RowScore
    {
	ONE_ROW(100), TWO_ROWS(300), THREE_ROWS(500), FOUR_ROWS(800);

	final private int score;

	private RowScore(final int score) {
	    this.score = score;
	}

	public int getScore() {
	    return score;
	}
    }


    public void resetScore() {
	score = 0;
    }

    public int getScore() {
	return score;
    }

    public void increaseScore(final int rowsRemoved) {
	score += scoreMap.getOrDefault(rowsRemoved, 0);
    }

    private static Map<Integer, Integer> createScoreMap() {
	final Map<Integer, Integer> scoreMap = new HashMap<>();
	int i = 1;

	for (final RowScore rowScore : RowScore.values()) {
	    scoreMap.put(i, rowScore.getScore());
	    ++i;
	}
	return scoreMap;
    }
}
