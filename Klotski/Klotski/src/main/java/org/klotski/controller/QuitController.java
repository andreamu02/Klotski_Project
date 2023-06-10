package org.klotski.controller;

// IMPORT JOPTIONPANE
import javax.swing.JOptionPane;

/**
 * <em>QuitController</em> is a controller class that is used to quit the game.<br>
 * It is useful if it's created to use function inline from {@link org.klotski.graphics.Window}<br>
 * It uses the default Constructor cause there is no necessity of storing any other data from other classes
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class QuitController {

    /**
     * Function to quit the game. It returns a boolean that indicates if the user want to close the game or no<br>
     * It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      if(new QuitController(this).quit()){
     *          return true;
     *      }
     * }</pre></blockquote>
     * @see JOptionPane
     * @return The quitting choice made by the user
     */
    public boolean quit() {
        String[] options = new String[]{"Yes", "No"}; // SHOW CONFIRM DIALOGUE
        int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Exit Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
        return choice == JOptionPane.YES_OPTION;
    }
}
