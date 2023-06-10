package org.klotski.controller;

// IMPORT WINDOW E JOPTIONPANE
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Window;

import javax.swing.JOptionPane;

/**
 * <em>InfoController</em> is a controller class that is used to display info about the game.<br>
 * It dialogues with {@link Window}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class InfoController {
    /**
     * The used Window
     */
    private final Window window;

    /**
     * This constructor saved data from {@link Window} and {@link HighScoreUpdater}
     * @param window {@link Window} object from which obtain variables
     */
    public InfoController(Window window) {
        this.window = window;
    }

    /**
     * Function to display info about the game and the authors.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new InfoController(this).info();
     * }</pre></blockquote>
     * @see JOptionPane
     */
    public void info() {
        JOptionPane.showMessageDialog(window,
                /* Message */
                """
                        This application is a simple puzzle game implemented in Java.
                        The goal is to release the large 2x2 tile from the board
                        through the opening centered at the bottom of the board. To
                        select a piece, click on it. Then it can be moved in any
                        direction with the WASD keys or the arrow buttons.
                        You can also move pieces by dragging them across the
                        board with the mouse.

                        Only the large 2x2 tile can leave the board, so you have to
                        move the right pieces to complete the puzzle in the fewest moves.

                        Authors: Alba Casanica, Alessandro Girlanda, Andrea Mutti, Matteo Meneghin\t
                        Date: 2023""",

                /* Title */
                "Info Klotski Game",

                /* Type. */
                JOptionPane.INFORMATION_MESSAGE);
    }
}
