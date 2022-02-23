package se.liu.wilmi895.tetris;

import java.util.HashMap;
import java.util.Map;

public class ScoreCounter
{
    private final Map<Integer, Integer> scoreMap = createScoreMap();
    private int score = 0;

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
	final int[] scoreForIRows = { 100, 300, 500, 800 };

	for (int i = 0; i < scoreForIRows.length; i++) {
	    scoreMap.put(i + 1, scoreForIRows[i]);
	}
	return scoreMap;
    }
}
