package org.klotski.coding;

// IMPORT I/O CLASSES
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <em>GameSaver</em> create an object for saving the current game with every move,<br>
 * the timer, moves counter and configuration.<br>
 * It connects to [name_of_the_user].txt and writes text following this form:<br>
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
public class GameSaver {
    /**
     * The file where to write the text
     */
    private FileWriter file;
    /**
     * The text with all the information
     */
    private String text;
    /**
     * The name of the player
     */
    private String name;

    /**
     * Constructor that initializes the Saver with the right name of the player
     * @throws IllegalArgumentException If the file got I/O errors
     * @see GameSaver#createFile(int)
     * @param name The name of the player
     * @param configuration The selected initial configuration
     */
    public GameSaver(String name, int configuration) {
        try {
            this.name = name;
            createFile(configuration);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructor that initializes the Saver with the path where to save data
     * @throws IllegalArgumentException If the file got I/O errors
     * @see GameSaver#createFile(int, String)
     * @param path The path of the file
     * @param configuration The selected initial configuration
     * @param conf Boolean value (is preferable to set it to true)
     */
    public GameSaver(String path, int configuration, boolean conf) {
        if (conf) {
            try {
                this.name = null;
                createFile(configuration, path);
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Sets where to write the file and saves the configuration
     * @param configuration The selected initial configuration
     * @param path The path of the file
     * @throws IOException If the file is non-readable
     */
    private void createFile(int configuration, String path) throws IOException {
        file = new FileWriter(path);
        text = String.valueOf(configuration);
    }

    /**
     * Sets where to write the file and saves the configuration
     * @param configuration The selected initial configuration
     * @throws IOException If the file is non-readable
     */
    private void createFile(int configuration) throws IOException {
        file = new FileWriter("src/main/resources/users_history/" + name + ".txt");
        text = String.valueOf(configuration);
    }

    /**
     * Add one line [block] [direction]
     * @param block The selected block position
     * @param direction The direction of this move
     */
    public void addLine(int block, int direction) {
        text = text + "\n" + block + " " + direction;
    }

    /**
     * Remove the last line (for undo move)
     * @see StringBuilder
     */
    public void removeLine(){
        if(!text.isEmpty()){
            // SPLITS THE TEST AND RE-MAKES IT WITHOUT THE LAST LINE
            String[] moves = text.split("\n");
            StringBuilder temp = new StringBuilder();
            for(int i = 0; i< moves.length-1; i++){
                if(moves.length-2 == i){
                    temp.append(moves[i]);
                }else{
                    temp.append(moves[i]).append("\n");
                }
            }
            if(moves.length == 1){
                text = "";
            }else{
                text = temp.toString();
            }
        }
    }

    /**
     * Get the current text (useful when the path of the saving file is changing)
     * @return Current text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text (useful when the path of the saving file is changed)
     * @param text The text to save
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Close the {@link FileWriter} adding timer and moves, it can delete the file is requested.<br>
     * Deleting the file is useful when someone wins the game and wants to exit is
     * @param timer The timer in seconds
     * @param moves The number of the moves
     * @param delete If you want to delete this file
     */
    public void close(int timer, int moves, boolean delete) {
        try {
            // WRITES THE LAST THINGS
            text = text + "\n*\n" + timer + " " + moves;
            file.write(text);
            file.close();
            if (delete) {
                    // DELETES THE FILE IS REQUESTED
                File file2 = new File("src/main/resources/users_history/" + name + ".txt");
                file2.delete();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
