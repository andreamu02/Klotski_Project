package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.coding.BoardImp;
import org.klotski.graphics.Window;

import javax.swing.*;

/**
 * <em>SelectConfigurationController</em> is a controller class that is used to change the current configuration.<br>
 * It dialogues with {@link Window}, {@link BoardImp}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class SelectConfigurationController {
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
    public SelectConfigurationController(Window window) {
        this.window = window;
        this.boardImp = window.getBoardImp();
    }

    /**
     * Function to change configuration. It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new SelectConfigurationController(this).selectConfiguration(BoardImp.SECOND_CONFIGURATION);
     * }</pre></blockquote>
     * To see what configuration are allowed see {@link BoardImp}
     * @see BoardImp
     * @see Window
     * @param configuration One of the fourth possible configurations
     * @throws IllegalArgumentException If the configuration number is over or under the limits
     */
    public void selectConfiguration(int configuration) {
        if (configuration < 1 || configuration > 4) {
            throw new IllegalArgumentException("Configuration out of range");
        }
        String[] options = {"Continue", "Cancel"};      // SHOW CONFIRMATION MESSAGE
        int choice = JOptionPane.showOptionDialog(null, "If you continue with this new configuration you lose your progress", "Change Configuration", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
        if (choice == JOptionPane.YES_OPTION) {
            boardImp.setConfiguration(configuration);
            window.newConfiguration();
            window.resetHistory();
        }
    }
}
