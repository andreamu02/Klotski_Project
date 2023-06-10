package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.BoardImp;
import org.klotski.coding.GameSaver;
import org.klotski.graphics.Board;
import org.klotski.graphics.Window;

/**
 * <em>UndoController</em> is a controller class that is used to undo previous move.<br>
 * It dialogues with {@link Window}, {@link GameSaver}, {@link BoardImp}, {@link Board}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class UndoController {
    /**
     * The used Board
     */
    private final Board board;
    /**
     * The used Board Configuration
     */
    private final BoardImp bImp;
    /**
     * The used Window
     */
    private final Window window;
    /**
     * The used Game Saver
     */
    private final GameSaver gameSaver;

    /**
     * This constructor saved data from {@link Window}
     * @param window {@link Window} object from which obtain variables
     */
    public UndoController(Window window) {
        this.window = window;
        this.bImp = window.getBoardImp();
        this.board = window.getBoard();
        this.gameSaver = window.getGameSaver();
    }

    /**
     * Function to undo the previous move. It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new UndoController(this).undo();
     * }</pre></blockquote>
     * @see Board
     * @see BoardImp
     * @see Window
     * @see GameSaver
     */
    public void undo() {
        // CALLS THE RIGHT FUNCTIONS
        bImp.undo();
        board.deSelect();
        window.undo();
        window.decrementsMovesCounter();
        gameSaver.removeLine();
    }
}
