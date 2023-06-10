package org.klotski.coding;

// IMPORT LOGIN PAGE AND WINDOW
import org.klotski.graphics.LoginPage;
import org.klotski.graphics.Window;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Klotski class that connects {@link LoginPage} with {@link Window} passing the user.<br>
 * It also implements {@link WindowListener} to catches whenever {@link Window} and {@link LoginPage} are closing.
 * @see Window
 * @see LoginPage
 * @see WindowListener
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class Klotski implements WindowListener {
    /**
     * Window object to create graphical game
     */
    Window window;
    /**
     * Login Page to select a user
     */
    final LoginPage login;
    /**
     * Username Collector to collect and show usernames
     */
    private final UsernameCollector usernameCollector;

    /**
     * Klotski constructor that creates UsernameCollector that is creator for LoginPage
     */
    public Klotski() {
        usernameCollector = new UsernameCollector();
        login = new LoginPage(usernameCollector);
        // ADD LISTENERS
        login.addWindowListener(this);
    }

    /**
     * Start the game, setting LoginPage visible
     */
    public void start() {
        login.setVisible(true);
    }

    /**
     * Starts a new Window when is necessary and saves the current game or exits it
     */
    void windowStart(){
        // CHECKS IF THE USER IS CHANGED
        if(login.hasChanged()){
            if(!login.isFirst()){
                window.saveGame();
                if(login.isExiting()){
                    System.exit(0);
                }
            }
            if (login.isExiting()) {
                System.exit(0);
            }
            // CREATES WINDOW AND ADD LISTENERS
            window = new Window(usernameCollector.getSelectedUser());
            window.addWindowListener(this);
        }
        window.setVisible(true);
    }


    // * WINDOW EVENTS *

    /**
     * Override of {@link WindowListener#windowClosed(WindowEvent)} function.<br>
     * Watches window boolean values to exit the game or reOpen Login Page
     * @see LoginPage
     * @see Window
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {
        if (e.getSource() == login) {
            // WINDOW START IF LOGIN IS CLOSED
            windowStart();
        }
        if (e.getSource() == window) {
            // IF THE WINDOW IS CLOSED, ACTIVATES USER-CHANGED OR EXITS
            if (window.hasChangedUser()) {
                login.reactivate();
                login.setVisible(true);
                window.setChangedUser(false);
            } else {
                if(window.hasWon()){
                    window.saveGameWon();
                    System.exit(0);
                }else{
                    window.saveGame();
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Forced implementation of {@link  WindowListener#windowOpened(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Forced implementation of {@link  WindowListener#windowClosing(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {

    }

    /**
     * Forced implementation of {@link  WindowListener#windowIconified(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowIconified(WindowEvent e) {
    }

    /**
     * Forced implementation of {@link  WindowListener#windowDeiconified(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     * Forced implementation of {@link  WindowListener#windowActivated(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {
    }

    /**
     * Forced implementation of {@link  WindowListener#windowDeactivated(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
