package org.klotski.coding;

// IMPORT I/O CLASSES AND UTIL CLASSES
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <em>HighScoreUpdater</em> get the Best score for all the users and update them if necessary.<br>
 * It stores elements in a Map with user as the key
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class HighScoreUpdater {
    /**
     * The map that contains all the users and the associated score
     */
    private final Map<String, Integer> scoresMap;

    /**
     * Initializes the map and calls the right function to collect values
     * @see HighScoreUpdater#createTable()
     */
    public HighScoreUpdater() {
        scoresMap = new HashMap<>();
        createTable();
    }

    /**
     * Set the map with users and scores from users_high_score.txt
     * @throws IllegalArgumentException If there's no file, or it's written in a bad way
     */
    private void createTable() {
        try {
            // CONNECT TO THE FILE AND READ EVERY COUPLE USER-SCORE
            FileReader file = new FileReader("src/main/resources/users_high_score/users_high_score.txt");
            Scanner inFile = new Scanner(file);
            while (inFile.hasNext()) {
                String user = inFile.next();
                int score = Integer.parseInt(inFile.next());
                scoresMap.put(user.toUpperCase(), score);
            }
            inFile.close();
            file.close();
        } catch (IOException | NumberFormatException | NoSuchElementException e) {
            // CATCH VARIOUS TYPES OF EXCEPTIONS AND THROWS ONLY THE ILLEGAL ARGUMENT ONE
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds a user and its relative high score to the map
     * @param user The selected user
     * @param score The user's score
     */
    public void addUserScore(String user, int score) {
        scoresMap.put(user.toUpperCase(), score);
        writeScore();
    }

    /**
     * Check if there are users stored
     * @return True if the map isn't empty, false otherwise
     */
    public boolean hasUsers() {
        return !scoresMap.isEmpty();
    }

    /**
     * Check if a user is inside the map
     * @param user The user to search
     * @return True if there's the user associated with a key, false otherwise
     */
    public boolean isInside(String user) {
        return hasUsers() && scoresMap.containsKey(user);
    }

    /**
     * Update a user's score if there's the user in the list
     * @param user The selected user
     * @param score The score to update
     * @throws IllegalArgumentException If there is no user in the map
     * @see HighScoreUpdater#writeScore()
     */
    public void updateScore(String user, int score) {
        if (isInside(user.toUpperCase())) {
            scoresMap.replace(user.toUpperCase(), score);
        } else {
            throw new IllegalArgumentException();
        }
        // IF THERE IS A USER RE-WRITES ALL OF THEM
        writeScore();
    }

    /**
     * Get the score from a given user
     * @param user The selected user
     * @return The score of this user
     * @throws IllegalArgumentException If there is no user
     */
    public int getScore(String user) {
        if (isInside(user.toUpperCase())) {
            return scoresMap.get(user.toUpperCase());
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Write all the users and scores in the correct file
     * @throws IllegalArgumentException If there are issues with the file, or it's missing
     */
    private void writeScore() {
        try {
            // OPEN THE FILE AND CREATE THE STRING BUILDER
            FileWriter file = new FileWriter("src/main/resources/users_high_score/users_high_score.txt");
            StringBuilder temp = new StringBuilder();
            for (Map.Entry<String, Integer> entry : scoresMap.entrySet()) {
                // APPEND EVERY COUPLE
                temp.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }
            // REMOVE THE LAST "\N"
            if (temp.length() > 0) {
                temp.deleteCharAt(temp.length() - 1);
            }
            file.write(temp.toString());
            file.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
