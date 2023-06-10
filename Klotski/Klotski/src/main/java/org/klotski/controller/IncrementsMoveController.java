package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.BoardImp;
import org.klotski.coding.GameSaver;
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Board;
import org.klotski.graphics.Window;

import javax.swing.*;

/**
 * <em>IncrementsMoveController</em> is a controller class that is used to increment or decrement the number of moves.<br>
 * It dialogues with {@link Window}, {@link Board}, {@link BoardImp}, {@link GameSaver}, {@link HighScoreUpdater}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class IncrementsMoveController {
    /**
     * The used Game Saver
     */
    private GameSaver gameSaver;
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
    private final Board board;

    /**
     * This constructor saved data from {@link Window} and {@link HighScoreUpdater}
     * @param window {@link Window} object from which obtain variables
     * @param highScoreUpdater {@link HighScoreUpdater} object from which obtain variables
     */
    public IncrementsMoveController(Window window, HighScoreUpdater highScoreUpdater) {
        this.window = window;
        this.boardImp = window.getBoardImp();
        this.gameSaver = window.getGameSaver();
        this.board = window.getBoard();
        this.highScoreUpdater = highScoreUpdater;
    }

    /**
     * Function called when decrements the number of moves.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new IncrementsMoveController(this, highScoreUpdater).decrementsMoveCounter();
     * }</pre></blockquote>
     * @see IncrementsMoveController#increments()
     */
    public void decrementsMoveCounter() {
        increments();
    }

    /**
     * Function called when increments the number of moves.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new IncrementsMoveController(this, highScoreUpdater).incrementsMoveCounter(1, BoardImp.DOWN);
     * }</pre></blockquote>
     * @see IncrementsMoveController#increments()
     * @see GameSaver
     * @param selected The index of the selected block
     * @param direction The direction of this movement
     */
    public void incrementsMoveCounter(int selected, int direction) {
        gameSaver.addLine(selected, direction);
        increments();
    }

    /**
     * Function called by other functions ({@link IncrementsMoveController#incrementsMoveCounter(int, int)}, {@link IncrementsMoveController#decrementsMoveCounter()}).<br>
     * Controls if the user won.<br>
     * @see Window
     * @see HighScoreUpdater
     * @see GameSaver
     */
    private void increments() {
        if (boardImp.hasWon()) { // ASKS BOARDIMP IF USER WON
            String message = "       YOU WON!";
            if (window.getMovesCounter() < highScoreUpdater.getScore(window.getPlayer()) || highScoreUpdater.getScore(window.getPlayer()) == 0) {
                highScoreUpdater.updateScore(window.getPlayer(), window.getMovesCounter());
                message += "\n\n       NEW HIGH SCORE: " + window.getMovesCounter() + "\n";
            }else{
                message += "\n\n       YOUR SCORE: " + window.getMovesCounter() + "\n";
            }
            window.setChangedUser(false);
            // SETS CHANGED USER TO FALSE AND REVALIDATES THE BOARD
            board.won();
            window.revalidate();
            window.repaint();
            String[] options = new String[]{"Retry", "Exit"};   // RETRY-EXIT DIALOGUE
            int choice = JOptionPane.showOptionDialog(null, message, "Win", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, window.getIcon_won(), options, 0);
            if (choice == JOptionPane.NO_OPTION) {
                // IF EXIT SET BOOLEAN WON TO TRUE
                window.setWon(true);
                window.dispose();
            } else {
                // START A NEW GAME
                gameSaver = new GameSaver(window.getPlayer(), boardImp.getConfiguration());
                boardImp.setConfiguration(boardImp.getConfiguration());
                window.newConfiguration();
                window.resetHistory();
                window.setChangedUser(false);
            }
        }
    }
}
