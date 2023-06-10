package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.BoardImp;
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Board;
import org.klotski.graphics.Window;

import java.awt.event.MouseEvent;

/**
 * <em>MoveController</em> is a controller class that is used to Move a single {@link org.klotski.graphics.Block Block} piece.<br>
 * It dialogues with {@link Window}, {@link Board}, {@link BoardImp}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class MoveController {
    /**
     * The used Board Configuration
     */
    private final BoardImp boardImp;
    /**
     * The used Board
     */
    private final Board board;
    /**
     * The used Window
     */
    private final Window window;

    /**
     * This constructor saved data from {@link Window} and {@link HighScoreUpdater}
     * @param window {@link Window} object from which obtain variables
     */
    public MoveController(Window window) {
        this.window = window;
        this.boardImp = this.window.getBoardImp();
        this.board = this.window.getBoard();
    }

    /**
     * Function to move a single {@link org.klotski.graphics.Block Block}.<br>
     * It is used for drag and drop movement with MouseEvent.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new MoveController(this).move(e);
     * }</pre></blockquote>
     * @param e The {@link MouseEvent} event
     * @see Window
     * @see Board
     * @see java.awt.event.MouseListener MouseListener
     */
    public void move(MouseEvent e) {
        if (board.moveReleased(e)) {
            window.incrementsMovesCounter(boardImp.getSelectedPosition(), boardImp.getLastDirection());
        }
    }

    /**
     * Function to move a single {@link org.klotski.graphics.Block Block}.<br>
     * It is used for select and move movement with directions.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new MoveController(this).move(BoardImp.UP);
     * }</pre></blockquote>
     * @param direction The selected direction
     * @see Window
     * @see org.klotski.coding.BoardImp BoardImp
     */
    public void move(int direction) {
        if (boardImp.moveBlock(direction)) {
            window.incrementsMovesCounter(boardImp.getSelectedPosition(), direction);
        }
    }

}
