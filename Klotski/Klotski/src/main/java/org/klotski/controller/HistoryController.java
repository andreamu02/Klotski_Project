package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.BoardImp;
import org.klotski.coding.FileRead;
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Board;
import org.klotski.graphics.Window;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * <em>HistoryController</em> is a controller class that is used to set a game starting by a configuration or get a score.<br>
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
public class HistoryController {
    /**
     * The used Window
     */
    private final Window window;
    /**
     * The used Board Configuration
     */
    private final BoardImp boardImp;
    /**
     * The used HighScoreUpdater
     */
    private final HighScoreUpdater highScoreUpdater;
    /**
     * The used Board
     */
    private Board board;

    /**
     * This constructor saved data from {@link Window} and {@link HighScoreUpdater}
     * @param window {@link Window} object from which obtain variables
     * @param highScoreUpdater {@link HighScoreUpdater} object
     * @param board {@link Board} object to repaint
     */
    public HistoryController(Window window, HighScoreUpdater highScoreUpdater, Board board) {
        this.window = window;
        this.boardImp = window.getBoardImp();
        this.highScoreUpdater = highScoreUpdater;
        this.board = board;
    }

    /**
     * Function to get the highScore from a user.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new HistoryController(this, highScoreUpdater, this.getBoard()).getScore();
     * }</pre></blockquote>
     * @see HighScoreUpdater
     * @return The score of the selected player
     */
    public int getScore() {
        String player = window.getPlayer(); // GET THE USER FROM THE WINDOW AND RETURN THE SCORE
                                            // AND ADD THE PLAYER IF NOT STORED
        if (!highScoreUpdater.isInside(player)) {
            highScoreUpdater.addUserScore(player, 0);
        }
        return highScoreUpdater.getScore(player);
    }

    /**
     * Function to get the highScore from a user.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new HistoryController(this, highScoreUpdater, this.getBoard()).getScore();
     * }</pre></blockquote>
     * @see HighScoreUpdater
     * @see HistoryController#setHistory()
     */
    public void viewHistory() {
        File file = new File("src/main/resources/users_history/" + window.getPlayer() + ".txt");
        if (file.isFile()) {                                // ASKS FOR LOADING THE SAVED CONFIGURATION
            String[] options = new String[]{"Yes", "No"};
            int choice = JOptionPane.showOptionDialog(null, "Do you want to load the saved configuration?", "Load Panel", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, window.getIcon_won(), options, 0);
            if (choice == JOptionPane.YES_OPTION) {
                setHistory();
            }
        }
    }

    /**
     * Function to load the configuration from the name player in the window.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new HistoryController(this, highScoreUpdater, this.getBoard()).setHistory();
     * }</pre></blockquote>
     * @see FileRead
     * @see HistoryController#actionsHistory(FileRead)
     */
    public void setHistory() {
        try {
            FileReader file = new FileReader("src/main/resources/users_history/" + window.getPlayer() + ".txt");
            FileRead fileRead = new FileRead(file);
            actionsHistory(fileRead);
        } catch (IOException | IllegalArgumentException e) {
            JOptionPane.showOptionDialog(null, "Configuration not found", "Load Error", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 0);
        }
    }
    /**
     * Function to load the configuration from a file.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new HistoryController(this, highScoreUpdater, this.getBoard()).setHistory(fileRead);
     * }</pre></blockquote>
     * @see FileRead
     * @see HistoryController#actionsHistory(FileRead)
     * @param fileRead The {@link FileRead} object from where to store data
     */
    public void setHistory(FileRead fileRead) {
        try {
            actionsHistory(fileRead);
        } catch (IllegalArgumentException e) {
            JOptionPane.showOptionDialog(null, "Configuration not found", "Load Error", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 0);
        }
    }

    /**
     * Function called by other functions in {@link HistoryController} class.<br>
     * It creates a new {@link Board} and set the right configuration, after that it makes all the moves.<br>
     * @see FileRead
     * @see HistoryController#actionsHistory(FileRead)
     */
    private void actionsHistory(FileRead read) {
        boardImp.setConfiguration(read.getConfiguration());
        this.board = new Board(boardImp);
        window.newConfiguration();      // NEW BOARD AND NEW CONFIGURATION
        while (read.hasNextLine()) {
            String nextMoves = read.getLine();
            if (!nextMoves.equals("")) {        // MAKE ALL THE MOVES
                Scanner in = new Scanner(nextMoves);
                int blockPos = Integer.parseInt(in.next().trim());
                int direction = Integer.parseInt(in.next().trim());
                boardImp.setSelected(blockPos);
                if (boardImp.moveBlock(direction)) {
                    window.incrementsMovesCounter(boardImp.getSelectedPosition(), direction);
                }
                in.close();
                board.revalidate();
                board.repaint();
            }
        }
        board.deSelect();
        window.setMovesCounter(read.getMoves());   // SET THE RIGHT TIMER AND MOVES
        window.setTimerTime(read.getTime() - 1);
        window.timerTextUpdate();
        window.resetHistory();
    }
}
