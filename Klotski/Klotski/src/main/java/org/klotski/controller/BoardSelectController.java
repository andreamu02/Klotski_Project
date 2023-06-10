package org.klotski.controller;

// IMPORT BOARD AND MOUSEEVENT
import org.klotski.graphics.Board;

import java.awt.event.MouseEvent;

/**
 * <em>BoardSelectController</em> is a controller class that is used to Select or Deselect a {@link org.klotski.graphics.Block Block}.<br>
 * It dialogues with {@link Board}.<br>
 * It is useful if it's created to use function inline from {@link org.klotski.graphics.Window Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class BoardSelectController {
    /**
     * The used Board
     */
    private final Board board;

    /**
     * This constructor saved data from {@link Board}
     * @param board {@link Board} object from where to store data
     */
    public BoardSelectController(Board board) {
        this.board = board;
    }

    /**
     * Function to deSelect a single {@link org.klotski.graphics.Block Block}.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new BoardSelectController(this.getBoard()).deSelect();
     * }</pre></blockquote>
     * @see Board
     */
    public void deSelect() {
        board.deSelect();
    }

    /**
     * Function to Select a single {@link org.klotski.graphics.Block Block}.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new BoardSelectController(this.getBoard()).select(4);
     * }</pre></blockquote>
     * @see Board
     * @param i Index int the Block[] array of the selected block
     */
    public void select(int i) {
        board.select(i);
    }

    /**
     * Function to Select a single {@link org.klotski.graphics.Block Block}.<br>
     * It is used with drag and drop movement.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new BoardSelectController(this.getBoard()).moveSelect(0, e);
     * }</pre></blockquote>
     * @param i Index int the Block[] array of the selected block
     * @param e The Mouse Event
     * @see Board
     */
    public void moveSelect(int i, MouseEvent e) {
        board.moveSelect(i, e);
    }
}
