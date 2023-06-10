package org.klotski.controller;

// IMPORT BOARD AND MOUSEEVENT
import org.klotski.coding.BoardImp;
import org.klotski.coding.NextBestMove;
import org.klotski.graphics.Window;

import java.util.Scanner;

/**
 * <em>BestMoveController</em> is a controller class that is used to do the best next move.<br>
 * It dialogues with {@link Window}, {@link NextBestMove} and {@link BoardImp}.<br>
 * It is useful if it's created to use function inline from {@link org.klotski.graphics.Window Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class BestMoveController {
    /**
     * The used NextBestMove
     */
    private final NextBestMove nextBestMove;
    /**
     * The used Window
     */
    private final Window window;
    /**
     * The used Board Configuration
     */
    private final BoardImp boardImp;

    /**
     * This constructor saved data from {@link Window}
     * @param window {@link Window} object from where to store data
     * @param nextBestMove {@link NextBestMove} object from where to get the move
     */
    public BestMoveController(Window window, NextBestMove nextBestMove) {
        this.window = window;
        this.boardImp = window.getBoardImp();
        this.nextBestMove = nextBestMove;
    }

    /**
     * Function to make the next best move.<br>
     * If the best possible move isn't allowed, it gives one random move.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new BestMoveController(this, nextBestMove).nextBestMove();
     * }</pre></blockquote>
     * @see NextBestMove
     */
    public void nextBestMove() {
        String move = nextBestMove.nextMove();  // GET THE STORED MOVE
        if (move != null) {
            Scanner in = new Scanner(move);
            int blockPos = Integer.parseInt(in.next().trim());
            int direction = Integer.parseInt(in.next().trim());
            boardImp.setSelected(blockPos);
            if (boardImp.moveBlock(direction)) {            // CHECK IF THAT MOVE IS POSSIBLE AND DO IT
                window.incrementsMovesCounter(boardImp.getSelectedPosition(), direction);
                in.close();
                return;
            }
            in.close();
        }
        int direction;      // IF MOVE IS NOT POSSIBLE IT CREATES A RANDOM MOVE
        do {
            int blockPos = (int) Math.floor(Math.random() * 10);
            direction = (int) Math.floor(Math.random() * 4);
            boardImp.setSelected(blockPos);
        } while (!boardImp.moveBlock(direction));
        window.incrementsMovesCounter(boardImp.getSelectedPosition(), direction);
    }
}
