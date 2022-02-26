package se.liu.wilmi895.tetris;

public class Highscore
{
    private String name = null;
    private int score = 0;

    public Highscore(final String name, final int score) {
	this.name = name;
	this.score = score;
    }

    public Highscore() {
    }

    public String getName() {
	return name;
    }

    public int getScore() {
	return score;
    }
}
