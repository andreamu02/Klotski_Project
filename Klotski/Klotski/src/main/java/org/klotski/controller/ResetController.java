package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.BoardImp;
import org.klotski.graphics.Window;

/**
 * <em>ResetController</em> is a controller class that is used to reset current game.<br>
 * It dialogues with {@link Window} and {@link BoardImp}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class ResetController {
    /**
     * The used Board Configuration
     */
    private final BoardImp boardImp;
    /**
     * The used Window
     */
    private final Window window;

    /**
     * This constructor saved data from {@link Window}
     * @param window {@link Window} object from which obtain variables
     */
    public ResetController(Window window){
        this.window = window;
        this.boardImp = window.getBoardImp();
    }

    /**
     * Function to reset the game with the current configuration.<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new ResetController(this).reset();
     * }</pre></blockquote>
     * @see Window
     * @see BoardImp
     */
    public void reset(){
        boardImp.setConfiguration(boardImp.getConfiguration());
        window.newConfiguration();
        window.reset();
    }
}
