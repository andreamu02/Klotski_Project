package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.FileRead;
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Board;
import org.klotski.graphics.Window;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * <em>OpenFileController</em> is a controller class that is used to Open a saved game.<br>
 * It dialogues with {@link Window}, {@link Board}, {@link HighScoreUpdater}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class OpenFileController {
    /**
     * The used Window
     */
    private final Window window;
    /**
     * The used HighScoreUpdater
     */
    private final HighScoreUpdater highScoreUpdater;
    /**
     * The used Board
     */
    private final Board board;

    /**
     * This constructor saved data from {@link Window} and {@link HighScoreUpdater}
     * @param window {@link Window} object from which obtain variables
     * @param highScoreUpdater {@link HighScoreUpdater} object
     */
    public OpenFileController(Window window, HighScoreUpdater highScoreUpdater) {
        this.window = window;
        this.highScoreUpdater = highScoreUpdater;
        this.board = window.getBoard();
    }

    /**
     * Function to select and load a saved configuration.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new OpenFileController(this).open();
     * }</pre></blockquote>
     * @see JFileChooser
     * @see JOptionPane
     * @see HistoryController
     */
    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("Klotski/Klotski//src//main//resources//users_history"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Open a configuration");
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        fileChooser.addChoosableFileFilter(restrict);
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                FileReader file = new FileReader(path);
                FileRead read = new FileRead(file);
                new HistoryController(window, highScoreUpdater, board).setHistory(read);
            } catch (IllegalArgumentException | IOException ex) {
                JOptionPane.showMessageDialog(null, "File invalid", "Wrong File", JOptionPane.ERROR_MESSAGE, window.getIcon_won());
            }
        }
    }
}
