package org.klotski.graphics;

// IMPORT USERNAMECOLLECTOR TO DIALOGUE WITH IT AND GRAPHICAL LIBRARIES
import org.klotski.coding.UsernameCollector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * <em>LoginPage</em> class is a graphical class that helps user to select one username or create one.<br>
 * It dialogues with {@link UsernameCollector} to obtain stored users or to add a new one.<br>
 * It extends JFrame and implements various Listeners, {@link ActionListener}, {@link WindowListener}, {@link ListSelectionListener}, {@link KeyListener}<br>
 * For more information about Listeners
 * @see java.util.EventListener
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class LoginPage extends JFrame implements ActionListener, WindowListener, ListSelectionListener, KeyListener {
    /**
     * Final {@link UsernameCollector} object to collect and store users
     */
    private final UsernameCollector usernameCollector;
    /**
     * List of user to display
     */
    private JList<String> list;
    /**
     * String[] of users to pass to list
     */
    private String[] users;
    /**
     * The selected users
     */
    private String selectedUser;
    /**
     * Enter button to close <em>LoginPage</em> with a user stored
     */
    private JButton enter;
    /**
     * Quit button to exit the game
     */
    private JButton quit;
    /**
     * Field where to write the username
     */
    private JTextField fieldName;
    /**
     * Panel that contains all the users' information
     */
    private JPanel panel;
    /**
     * Boolean value to indicate if the username is changed
     */
    private boolean changed;
    /**
     * Boolean value to indicate if it's the first time opening <em>LoginPage</em>
     */
    private boolean first;
    /**
     * Boolean value to indicate if it's closing the program
     */
    private boolean exiting;

    /**
     * Constructor that stores an {@link UsernameCollector} object and sets default boolean values for some methods.<br>
     * It has to be used in connection with {@link UsernameCollector} to collect username to show, so an example of usage can be:
     * <blockquote><pre>{@code
     *      UsernameCollector usernameCollector = new UsernameCollector();
     *      LoginPage loginPage = new LoginPage(usernameCollector);
     *      loginPage.setVisible(true);
     * }
     * </pre></blockquote>
     * It calls {@link LoginPage#activate()} function to set initial graphical elements
     * @param usernameCollector {@link UsernameCollector} object to get Usernames
     * @throws IllegalArgumentException If <em>usernameCollector</em> is null
     */
    public LoginPage(UsernameCollector usernameCollector) {
        if(usernameCollector == null){
            throw new IllegalArgumentException("UsernameCollector cannot be null");
        }
        this.usernameCollector = usernameCollector;
        changed = true;
        activate();
        first = true;
        exiting = false;
    }

    /**
     * Private function called by the constructor to set initial graphical values.<br>
     * It also calls {@link LoginPage#setElements()} to set changeable graphical elements
     */
    private void activate() {
        // SET SIZE, BACKGROUND, ICON AND CONNECT WINDOW AND KEY LISTENER
        this.setSize(new Dimension(350, 300));
        this.setBackground(new Color(218, 185, 185));
        this.setTitle("Login Page");
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/main/resources/images/klotski.png");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(null);
        this.addWindowListener(this);

        setElements();

        enter = new JButton("Start");
        enter.setBounds(230, 160, 80, 30);
        enter.setBackground(new Color(184, 207, 229));
        enter.setFocusable(false);
        enter.addActionListener(this);

        quit = new JButton("Quit");
        quit.setBounds(230, 210, 80, 30);
        quit.setBackground(new Color(220, 202, 164));
        quit.setFocusable(false);
        quit.addActionListener(this);

        JTextArea textArea = new JTextArea("Select one username from the list below\nor insert a new username in the field.");
        textArea.setBounds(30, 15, 250, 60);
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setOpaque(false);

        this.add(textArea);
        this.add(quit);
        this.add(enter);

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    /**
     * This function has to be called when {@link LoginPage} has to be re-set visible, for example:
     * <blockquote><pre>{@code
     *      loginPage.reactivate();
     *      loginPage.setVisible(true);
     * }</pre></blockquote>
     * It calls {@link LoginPage#setElements()} function
     */
    public void reactivate() {
        first = false; // FIRST TO FALSE CAUSE IT IS NOT THE FIRST TIME LOGIN PAGE IS CALLED
        this.remove(fieldName);
        this.remove(panel);
        setElements();
        revalidate();
        repaint();
    }

    /**
     * Function called by {@link LoginPage#activate()} and {@link LoginPage#reactivate()} to set changeable graphical elements.<br>
     * It acquires users from {@link UsernameCollector#getUsers()} and changes it in a String[] array.<br>
     * Sets users' list and scrollpane or shows "No usernames to show"
     */
    private void setElements(){
        // OBTAIN VECTOR AND CHANGED IT TO STRING[]
        Object[] arr = usernameCollector.getUsers().toArray();
        users = Arrays.copyOf(arr, arr.length, String[].class);
        if (users.length != 0) {
            selectedUser = users[0];
        }
        list = new JList<>(users);
        list.setVisibleRowCount(3);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this); // ADD LIST SELECTION LISTENERS

        JScrollPane scrollPane = new JScrollPane(list); // ADD SCROLL PANE TO SHOWS ALL USERS
        panel = new JPanel();
        if (usernameCollector.hasUsers()) {
            panel.add(scrollPane);
        } else {
            JLabel lab = new JLabel("No usernames to show");
            lab.setBounds(60, 70, 150, 65);
            panel.add(lab);
        }
        panel.setBounds(60, 70, 150, 65);

        fieldName = new JTextField(selectedUser);
        fieldName.setBounds(60, 160, 150, 30);
        fieldName.addMouseListener(new MouseAdapter() { // ADD MOUSE LISTENER TO SELECT WITH ONE CLICK FIELD NAME ONLY THE FIRST TIME IS CLICKED
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == fieldName) {
                    fieldName.selectAll();
                    fieldName.removeMouseListener(this);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource() == fieldName) {
                    fieldName.selectAll();
                    fieldName.removeMouseListener(this);
                }
            }
        });
        fieldName.addKeyListener(this);

        this.add(fieldName);
        this.add(panel);
    }


    // * HAS FUNCTION *

    /**
     * Obtain boolean value changed - It referrers to a change of username after it reactivates
     * @return Boolean value that indicates if the username is changed
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * Obtain boolean value first if it's the first time it opens.<br>
     * {@link LoginPage#reactivate()} is the only function that changes the value to false
     * @return Boolean value that indicates if the user is using that for the first time
     */
    public boolean isFirst() {
        return first;
    }

    /**
     * Obtain boolean value exiting if the window is closing. It changed by the EventsListeners
     * @return Boolean value that indicates if the program is closing
     */
    public boolean isExiting() {
        return exiting;
    }


    /*
        * EXIT AND ENTER PHASE *
     */

    /**
     * How to do when quit is clicked.<br>
     * It shows a confirmation message and if true disposes the window with boolean value exiting = true
     */
    private void quit(){
        String[] options = new String[]{"Yes", "No"}; // EXIT DIALOGUE
        int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Exit Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
        if (choice == JOptionPane.YES_OPTION) {
            exiting = true;
            dispose();
        }
    }

    /**
     * How to do when a user is selected and enter is pressed.<br>
     * It changes selected value if necessary.<br>
     * If a user does not change, it shows a confirmation message.
     * Add a user if it's not in the list.<br>
     * Dialogue with {@link UsernameCollector} to store users
     */
    private void enter(){
        String text;
        if (list.getSelectedValue() != null) {
            text = list.getSelectedValue();
        } else {
            text = fieldName.getText().toUpperCase();
        }
        for (String user : users) {
            if (text.equalsIgnoreCase(user)) {
                if (user.equals(usernameCollector.getSelectedUser())) { // EXIT OPTION WITHOUT CHANGING USER
                    String[] options = new String[]{"Yes", "No"};
                    int choice = JOptionPane.showOptionDialog(null, "Do you want to continue with the previous username?", "Exit Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
                    if (choice == JOptionPane.YES_OPTION) {
                        changed = false;
                        dispose();
                    }
                } else {    // EXIT OPTION CHANGING USER
                    usernameCollector.setSelectedUser(user);
                    selectedUser = user;
                    changed = true;
                    dispose();
                }
                return;
            }
        }                                   // ADD USER TO USERNAMECOLLECTOR AND EXIT
        usernameCollector.addUser(text);
        selectedUser = text;
        changed = true;
        dispose();
    }


    /*
        * EVENT LISTENERS *
     */
    
    // * ACTION EVENTS *
    /**
     * Implementation of {@link  ActionListener#actionPerformed(ActionEvent)} function.<br>
     * It controls if quit button or enter button are clicked and exit to close the program or to store the selected user.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {                      // EXIT DIALOGUE
            quit();
        }
        if (e.getSource() == enter) {
            enter();
        }
    }
    
    
    // * WINDOW EVENTS * 

    /**
     * Checks if the window exit button is clicked to exit the program. It changes the exiting boolean to true.<br>
     * Implementation of {@link WindowListener#windowClosing(WindowEvent)}
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        String[] options = new String[]{"Yes", "No"};       // CLOSING DIALOGUE
        int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Exit Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
        if (choice == JOptionPane.YES_OPTION) {
            exiting = true;
            dispose();
        }
    }
    
    /**
     * Implementation of {@link  WindowListener#windowOpened(WindowEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {
    }

    /**
     * Implementation of {@link  WindowListener#windowClosed(WindowEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {
    }

    /**
     * Implementation of {@link  WindowListener#windowIconified(WindowEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowIconified(WindowEvent e) {
    }

    /**
     * Implementation of {@link  WindowListener#windowDeiconified(WindowEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     * Implementation of {@link  WindowListener#windowActivated(WindowEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {
    }

    /**
     * Implementation of {@link  WindowListener#windowDeactivated(WindowEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
    
    // * LIST SELECTION EVENTS

    /**
     * Checks if the selected value in the user list is changed and select that value writing it in the user field.<br>
     * Implementation of {@link ListSelectionListener#valueChanged(ListSelectionEvent)} function
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            selectedUser = list.getSelectedValue();
            fieldName.setText(selectedUser);

            /*
                ADD MOUSE LISTENER TO SELECT WITH ONE CLICK FIELD NAME ONLY THE FIRST TIME IS CLICKED.
                EVERYTIME ONE ITEM IN THE LIST IS CLICKED MOUSE LISTENER IS RE-ADDED,
                SO AGAIN IT IS POSSIBLE TO SELECT WITH THE FIRST CLICK
            */
            fieldName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getSource() == fieldName) {
                        fieldName.selectAll();
                        fieldName.removeMouseListener(this);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getSource() == fieldName) {
                        fieldName.selectAll();
                        fieldName.removeMouseListener(this);
                    }
                }
            });
        }
    }


    // * KEY EVENTS

    /**
     * Implementation of {@link  KeyListener#keyTyped(KeyEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Implementation of {@link  KeyListener#keyPressed(KeyEvent)}  function.<br>
     * It is useless in this case, so it is void.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * If something is typed it controls if the text is the same of a user in the list or select what is written.<br>
     * If enter is clicked, it stores the selected user and exits the {@link LoginPage}
     * Implementation of {@link  KeyListener#keyReleased(KeyEvent)}  function.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            enter();
        }
        String text = fieldName.getText();      // SELECT USER IN THE LIST IF IT'S TYPED IN THE FIELD
        for (int i = 0; i < users.length; i++) {
            if (text.equals(users[i])) {
                list.setSelectedIndex(i);
                list.ensureIndexIsVisible(i);
                return;
            }
        }
        list.clearSelection();
    }
}
