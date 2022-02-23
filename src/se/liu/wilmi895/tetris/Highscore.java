package se.liu.wilmi895.tetris;

public class Highscore
{
    private final String name;
    private final int score;

    public Highscore(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    @Override public String toString() {
        return String.format("%s %d", name, score);
    }
}
