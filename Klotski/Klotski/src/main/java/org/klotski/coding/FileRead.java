package org.klotski.coding;

import java.io.FileReader;
import java.util.Scanner;

/**
 * <em>FileRead</em> reads a saved file to get information about the game to restore.<br>
 * It connects to [name_of_the_user].txt that has to have this form:<br>
 * <blockquote><pre>
 * {
 *     [int_Configuration]
 *     [int_selected1] [int_direction1]
 *     [int_selected2] [int_direction2]
 *     [int_selected3] [int_direction3]
 *     ...
 *     [int_Timer] [int_Moves_Number]
 * }
 * </pre></blockquote>
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class FileRead {
    /**
     * The file where to read from
     */
    private final FileReader file;
    /**
     * The array of moves
     */
    private String[] moves;
    /**
     * The selected configuration
     */
    private int configuration;
    /**
     * The index of the array moves
     */
    private int index;
    /**
     * The timer in seconds
     */
    private int time;
    /**
     * The Number of the moves
     */
    private int movesNumber;

    /**
     * Constructor that given a fileReader calls the {@link FileRead#saveVariables()} function to store information
     * @param file The {@link FileReader} file from where to store data
     * @see FileRead#saveVariables()
     */
    public FileRead(FileReader file) {
        this.file = file;
        index = -1;
        saveVariables();
    }

    /**
     * Save data and stores it in Configuration, timer, movesCounter and moves[]. Calls {@link FileRead#controlLines()}.
     * @throws IllegalArgumentException If the file is not in the right format
     * @see StringBuilder
     * @see Scanner
     */
    private void saveVariables() {
        // CREATES A TEMP STRING AND ADDS EVERY LINE OF FILE
        StringBuilder temp = new StringBuilder();
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            if (temp.toString().equals("")) {
                temp.append(in.nextLine());
            }
            temp.append("\n").append(in.nextLine());
        }
        in.close();
        // SPLITS STRING
        String[] tempLines = temp.toString().split("\n");
        temp = new StringBuilder();

        // DIVIDES THE STRING AND STORES VARIABLES IN THE RIGHT POSITION
        for (int i = 0; i < tempLines.length; i++) {
            if (i == 0) {
                try {
                    configuration = Integer.parseInt(tempLines[i]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("File not conformed");
                }
            } else if (i == tempLines.length - 1) {
                try {
                    Scanner words = new Scanner(tempLines[i]);
                    time = Integer.parseInt(words.next());
                    movesNumber = Integer.parseInt(words.next());
                    words.close();
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("File not conformed");
                }
            } else if (i != tempLines.length - 2) {
                if (i == tempLines.length - 3) {
                    temp.append(tempLines[i]);
                } else {
                    temp.append(tempLines[i]).append("\n");
                }
            }
        }
        // THROWS EXCEPTION IF THE FILE IS NOT IN THE RIGHT FORM
        if(!temp.isEmpty()){
            if(tempLines.length>=3){
                if (!tempLines[tempLines.length - 2].equals("*")) {
                    throw new IllegalArgumentException("File not conformed");
                }
            }
            moves = temp.toString().split("\n");
        }else{
            moves = new String[1];
            moves[0] = "";
        }
        // CALLS CONTROL LINES TO SEE IF EVERY LINE IS IN THE RIGHT FORM
        controlLines();
    }

    /**
     * Checks if the lines are all correct to stores every move in the right way
     * @throws IllegalArgumentException If the file is not in the right format
     */
    private void controlLines() {
        for (String line : moves) {
            try {
                if(line.equals("")){
                    // IF IT'S VOID, RETURNS
                    return;
                }
                Scanner in = new Scanner(line);
                int x = Integer.parseInt(in.next());
                int y = Integer.parseInt(in.next());
                // CHECKS IF DIRECTION AND POSITION ARE POSSIBLE
                if (in.hasNext() || x < 0 || x > 9 || y < 0 || y > 3) {
                    throw new IllegalArgumentException("File not conformed");
                }
                in.close();
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("File not conformed");
            }
        }
    }

    /**
     * Get the selected configuration
     * @return The selected configuration
     */
    public int getConfiguration() {
        return configuration;
    }

    /**
     * Get the stored timer
     * @return The timer in seconds
     */
    public int getTime() {
        return time;
    }

    /**
     * Get the number of the moves
     * @return The number of the moves
     */
    public int getMoves() {
        return movesNumber;
    }

    /**
     * Get the next move written as {[position] [direction]}
     * @return The next line
     */
    public String getLine() {
        // INCREMENTS INDEX
        index++;
        return moves[index];
    }

    /**
     * Checks if the array has a next element
     * @return True if there is a next line
     */
    public boolean hasNextLine() {
        return !(index == moves.length - 1);
    }
}
