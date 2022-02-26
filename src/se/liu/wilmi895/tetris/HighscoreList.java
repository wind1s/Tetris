package se.liu.wilmi895.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HighscoreList
{
    private static final String SAVE_FILE_PATH = System.getProperty("user.dir") + "/resources/highscores.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private List<Highscore> highscoreList = null;

    public HighscoreList(final List<Highscore> highscoreList) {
	this.highscoreList = highscoreList;
    }

    public HighscoreList() {
    }

    public void addHighscore(final Highscore highscore) {
	highscoreList.add(highscore);
	highscoreList.sort(new HighscoreComparator());
    }

    public Highscore getHighscore(final int index) {
	if (index < 0 || index >= getSize()) {
	    throw new IllegalArgumentException("HighscoreList index out of bounds.");
	}
	return highscoreList.get(index);
    }

    public int getSize() {
	return highscoreList.size();
    }

    public void saveToJson() throws FileNotFoundException, SecurityException {
	try (final PrintWriter writer = new PrintWriter(SAVE_FILE_PATH)) {
	    writer.write(GSON.toJson(this));
	}
    }

    public static HighscoreList readSavedJson() throws IOException, JsonSyntaxException, JsonIOException {
	try (final FileReader reader = new FileReader(SAVE_FILE_PATH)) {
	    return GSON.fromJson(reader, HighscoreList.class);
	} catch (FileNotFoundException ignored) {
	    return new HighscoreList(new ArrayList<>());
	}
    }
}
