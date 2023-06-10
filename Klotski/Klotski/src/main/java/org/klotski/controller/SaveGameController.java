package org.klotski.controller;

// IMPORT GRAPHICS USEFUL CLASS
import org.klotski.graphics.Window;

/**
 * <em>SaveGameController</em> is a controller class that is used to save current game log.<br>
 * It dialogues with {@link Window} and {@link org.klotski.coding.GameSaver}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class SaveGameController {
    /**
     * The used Window
     */
    private final Window window;

    /**
     * This constructor saved data from {@link Window}
     * @param window {@link Window} object from which obtain variables
     */
    public SaveGameController(Window window) {
        this.window = window;
    }

    /**
     * Function to save the current game.<br>
     * Is it preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new SaveGameController(this).saveGame();
     * }</pre></blockquote>
     * @see org.klotski.coding.GameSaver
     */
    public void saveGame() {
        window.getGameSaver().close(window.getTimerTime(), window.getMovesCounter(), false);
    }
    /**
     * Function to save the current game.<br>
     * If delete is true, the current game will be deleted.<br>
     * It is useful when someone wins the game and exits it.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new SaveGameController(this).saveGame(true);
     * }</pre></blockquote>
     * @param delete Boolean that indicates if the file has to be deleted
     * @see org.klotski.coding.GameSaver
     */
    public void saveGame(boolean delete) {
        window.getGameSaver().close(window.getTimerTime(), window.getMovesCounter(), delete);
    }
}
