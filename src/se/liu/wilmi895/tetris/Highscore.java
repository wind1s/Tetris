package se.liu.wilmi895.tetris;

public class Highscore
{
    private final String name;
    private final int score;

    public Highscore(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
