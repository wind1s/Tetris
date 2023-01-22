package se.liu.wilmi895.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class HighscoreList
{
    public static final String FILE_NAME = "highscores.json";
    private static final String SAVE_FILE_PATH =
	    System.getProperty("user.dir") + File.separator + "resources" + File.separator + FILE_NAME;
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

    public void saveToJson() throws IOException, SecurityException, FileNotFoundException {
	final String tempFile = SAVE_FILE_PATH + ".tmp";
	try (final PrintWriter writer = new PrintWriter(tempFile)) {
	    writer.write(GSON.toJson(this));
	}

	final Path targetFile = Paths.get(SAVE_FILE_PATH);
	Files.deleteIfExists(targetFile);
	Files.move(Paths.get(tempFile), targetFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public static HighscoreList readSavedJson() throws IOException, JsonSyntaxException, JsonIOException {
	try (final FileReader reader = new FileReader(SAVE_FILE_PATH)) {
	    return GSON.fromJson(reader, HighscoreList.class);
	} catch (FileNotFoundException ignored) {
	    return new HighscoreList(new ArrayList<>());
	}
    }
}
