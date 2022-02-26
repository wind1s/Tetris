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
	scoreMap.put(1, 100);
	scoreMap.put(2, 300);
	scoreMap.put(3, 500);
	scoreMap.put(4, 800);

	return scoreMap;
    }
}
