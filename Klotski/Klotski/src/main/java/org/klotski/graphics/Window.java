package org.klotski.graphics;

// IMPORT CODING, GRAPHICS AND SWING/AWT LIBRARIES

import org.klotski.coding.*;
import org.klotski.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is the main Graphical Class that connect all the Coding Classes with all the Graphics Classes.<br>
 * It does not access and calls methods from other {@link org.klotski.graphics Graphics} or {@link org.klotski.coding Coding}.<br>
 * It extends JFrame to set Frame parameters and implements various Listeners.<br>
 * Thanks to them, <em>Window</em> is able to call {@link org.klotski.controller Controllers} handling the Events.<br>
 * It only instantiates every object and calls every controller in the right situation, handling various Listeners.<br>
 * It is important to make <em>Window</em> it visible,<br>
 * after it becomes instantiated, and to save the game with the right command.<br>
 * For example:
 * <blockquote><pre>{@code
 *      Window window = new Window(user);
 *      window.setVisible(true);
 *      if(!window.isVisible()){
 *          window.saveGame();
 *      }
 * }</pre></blockquote>
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class Window extends JFrame implements ActionListener, MouseListener, KeyListener, WindowListener {
    /**
     * The graphical game board that contains every {@link Block Blocks}
     */
    private Board board;
    /**
     * The non-graphical part of the game board, with configuration and moving methods.<br>
     * It is final because it can be reset.
     */
    private final BoardImp bImp;
    /**
     * {@link Block} array that contains every 10 pieces
     */
    private Block[] blocks;
    /**
     * The chosen configuration from {@link BoardImp}
     */
    private int boardConfiguration;
    /**
     * The number of the moves, it can be incremented or decremented
     */
    private int movesCounter;
    /**
     * The small icon displayed when someone wins or in other {@link JOptionPane}
     */
    private ImageIcon icon_won;
    /**
     * The left Panel. It's a class field because it can be accessed from many different methods
     */
    private JPanel leftPanel;
    /**
     * The right Panel. It's a class field because it can be accessed from many different methods
     */
    private JPanel rightPanel;
    /**
     * The right Arrow button, checked for Event to go right
     */
    private JButton rightArrow;
    /**
     * The left Arrow button, checked for Event to go left
     */
    private JButton leftArrow;
    /**
     * The up Arrow button, checked for Event to go up
     */
    private JButton upArrow;
    /**
     * The bottom Arrow button, checked for Event to go down
     */
    private JButton bottomArrow;
    /**
     * The undo button, checked for Event to undo the previous Move
     */
    private JButton undoButton;
    /**
     * The reset button, checked for Event to Reset the game
     */
    private JButton buttonReset;
    /**
     * Parts of the Menu, where to change Saving directory
     */
    private JMenuItem saveMenu;
    /**
     * Parts of the Menu, where to open a Saved Game
     */
    private JMenuItem openMenu;
    /**
     * Parts of the Menu, where to change a username
     */
    private JMenuItem changeMenu;
    /**
     * Parts of the Menu, where to quit the game
     */
    private JMenuItem quitMenu;
    /**
     * Parts of the Menu, where to reset the game with the selected configuration
     */
    private JMenuItem resetMenu;
    /**
     * Parts of the Menu, where to reset the game with the first configuration
     */
    private JMenuItem fConfiguration;
    /**
     * Parts of the Menu, where to reset the game with the second configuration
     */
    private JMenuItem sConfiguration;
    /**
     * Parts of the Menu, where to reset the game with the third configuration
     */
    private JMenuItem tConfiguration;
    /**
     * Parts of the Menu, where to reset the game with the fourth configuration
     */
    private JMenuItem qConfiguration;
    /**
     * Parts of the Menu, where to get the Next Best Move
     */
    private JMenuItem nextMoveMenu;
    /**
     * Parts of the Menu, where to show info about the game
     */
    private JMenuItem infoMenu;
    /**
     * The label that displays the Game Timer
     */
    private JLabel clockPanel;
    /**
     * The label that displays the number of moves
     */
    private JLabel movesLabel;
    /**
     * The label that displays the High score for a user
     */
    private JLabel highScoreField;
    /**
     * The Timer of the game
     */
    private Timer timer;
    /**
     * The used String to store the converted timer from int to visible text
     */
    private String timerText;
    /**
     * The number updated by the Timer
     *
     * @see Timer
     */
    private int timerTime;
    /**
     * The name of the current player
     */
    private final String namePlayer;
    /**
     * The object where to save all the moves
     *
     * @see GameSaver
     */
    private GameSaver gameSaver;
    /**
     * The object to get the next Best Move possible
     *
     * @see NextBestMove
     */
    private NextBestMove nextBestMove;
    /**
     * Boolean field to know if a player wants to change user
     */
    private boolean changedUser;
    /**
     * Boolean field to know if a block can be moved by drag and drop
     */
    private boolean canMove;
    /**
     * The object to update the best score and read it
     *
     * @see HighScoreUpdater
     */
    private final HighScoreUpdater highScoreUpdater;
    /**
     * The boolean field to know if a user won and chooses to quit
     */
    private boolean won;

    // * COLORS *
    /**
     * The background color of this JPanel
     */
    public final Color colorBackground;
    /**
     * The background color of all the JButtons
     */
    public final Color buttonBackground;

    /**
     * This is the only constructor for a <em>Window</em> object.<br>
     * It sets the initial params and instantiates many objects.<br>
     * It calls many starting functions to divide the work.<br>
     *
     * @param namePlayer The UserName selected by the user
     * @throws IllegalArgumentException If the name is null
     */
    public Window(String namePlayer) {
        if (namePlayer == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        // SET INITIAL PARAMS AND INSTANTIATES OBJECTS
        this.bImp = new BoardImp();
        this.board = new Board(bImp);
        this.boardConfiguration = bImp.getConfiguration();
        this.colorBackground = new Color(196, 185, 185, 255);
        this.buttonBackground = new Color(166, 199, 213);
        this.blocks = bImp.getBlocks();
        this.namePlayer = namePlayer;
        this.canMove = false;
        this.nextBestMove = new NextBestMove();
        this.highScoreUpdater = new HighScoreUpdater();
        this.changedUser = false;
        this.won = false;
        // CALL THE RIGHT FUNCTIONS
        activate();
        addComponent();
        setMenu();
        initializeTimer();
        new HistoryController(this, highScoreUpdater, board).viewHistory();
        if (gameSaver == null) {
            activateSaver();
        }
        timer.start();
    }


    // * FIELDS METHODS *

    /**
     * This function is called only by the constructor and set JFrame parameters and icons
     */
    private void activate() {
        // SET THE ICONS
        ImageIcon icon = new ImageIcon("src/main/resources/images/klotski.png");
        icon_won = new ImageIcon("src/main/resources/images/klotski_2.png");

        // JFRAME PARAMS
        this.setSize(800, 642);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBackground(new Color(227, 225, 225));
        this.setIconImage(icon.getImage());
        this.setTitle("KLOTSKI");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.requestFocus();
        this.addKeyListener(this);
        this.addWindowListener(this);
    }

    /**
     * This function is called by the constructor and instantiates graphical object and set them in the correct way.<br>
     * <em>Window</em> uses null Layout, so every component has the right bounds
     */
    private void addComponent() {
        // CREATES LEFT PANEL
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(460, 622));

        // CREATES BOARD BOARDER PANELS
        JPanel rPanel = new JPanel();
        rPanel.setBackground(colorBackground);
        rPanel.setPreferredSize(new Dimension(20, 525));

        JPanel lPanel = new JPanel();
        lPanel.setBackground(colorBackground);
        lPanel.setPreferredSize(new Dimension(20, 525));

        JPanel bPanel = new JPanel();
        bPanel.setBackground(colorBackground);
        bPanel.setPreferredSize(new Dimension(460, 20));

        JPanel uPanel = new JPanel();
        uPanel.setBackground(colorBackground);
        uPanel.setPreferredSize(new Dimension(460, 20));

        // ADDS PANELS IN THE LEFT PANEL WITH BORDER LAYOUT
        leftPanel.add(lPanel, BorderLayout.WEST);
        leftPanel.add(rPanel, BorderLayout.EAST);
        leftPanel.add(bPanel, BorderLayout.SOUTH);
        leftPanel.add(uPanel, BorderLayout.NORTH);
        leftPanel.add(board, BorderLayout.CENTER);

        // CREATES RIGHT PANEL
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(340, 622));
        rightPanel.setLayout(null);
        rightPanel.setBackground(colorBackground);
        rightPanel.setOpaque(true);

        // CREATES AND ADD RESET BUTTON AND ADD LISTENER
        buttonReset = new JButton("Reset");
        buttonReset.setBounds(30, 90, 110, 20);
        buttonReset.setFocusable(false);
        buttonReset.setBackground(buttonBackground);
        buttonReset.addActionListener(this);
        rightPanel.add(buttonReset);

        // CREATES THE PANEL WHERE TO SHOW THE GAME TIMER
        clockPanel = new JLabel();
        clockPanel.setBounds(238, 90, 56, 20);
        clockPanel.setBackground(new Color(197, 194, 194));
        clockPanel.setOpaque(true);
        clockPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel displayTime = new JLabel("Time: ");
        displayTime.setBounds(200, 90, 50, 20);
        displayTime.setOpaque(false);
        rightPanel.add(displayTime);

        // CREATES THE PANEL WHERE TO SHOW THE MOVES NUMBER
        movesLabel = new JLabel("0");
        movesLabel.setBounds(238, 140, 52, 20);
        movesLabel.setOpaque(true);
        movesLabel.setBackground(new Color(197, 194, 194));
        movesLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        movesLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel displayMoves = new JLabel("Moves: ");
        displayMoves.setBounds(195, 140, 50, 20);
        displayMoves.setOpaque(false);
        rightPanel.add(displayMoves);

        // CREATES DIRECTIONAL BUTTONS AND ADDS LISTENERS
        rightArrow = new JButton("\u2192");
        rightArrow.setBounds(200, 300, 60, 26);
        rightArrow.setFocusable(false);
        rightArrow.setFont(new Font("Arial", Font.PLAIN, 20));
        rightArrow.setHorizontalAlignment(JButton.CENTER);
        rightArrow.setVerticalAlignment(JButton.CENTER);
        rightArrow.setBackground(buttonBackground);
        rightArrow.addActionListener(this);
        rightPanel.add(rightArrow);

        leftArrow = new JButton("\u2190");
        leftArrow.setBounds(100, 300, 60, 26);
        leftArrow.setFocusable(false);
        leftArrow.setFont(new Font("Arial", Font.PLAIN, 20));
        leftArrow.setHorizontalAlignment(JButton.CENTER);
        leftArrow.setVerticalAlignment(JButton.CENTER);
        leftArrow.setBackground(buttonBackground);
        leftArrow.addActionListener(this);
        rightPanel.add(leftArrow);

        upArrow = new JButton("\u2191");
        upArrow.setBounds(155, 250, 50, 30);
        upArrow.setFocusable(false);
        upArrow.setFont(new Font("Arial", Font.PLAIN, 20));
        upArrow.setBackground(buttonBackground);
        upArrow.addActionListener(this);
        rightPanel.add(upArrow);

        bottomArrow = new JButton("\u2193");
        bottomArrow.setBounds(155, 346, 50, 30);
        bottomArrow.setFocusable(false);
        bottomArrow.setFont(new Font("Arial", Font.PLAIN, 20));
        bottomArrow.setBackground(buttonBackground);
        bottomArrow.addActionListener(this);
        rightPanel.add(bottomArrow);

        // CREATES UNDO BUTTON AND ADDS LISTENERS
        undoButton = new JButton("UNDO");
        undoButton.setBounds(135, 450, 90, 40);
        undoButton.setFocusable(false);
        undoButton.setBackground(buttonBackground);
        undoButton.addActionListener(this);
        rightPanel.add(undoButton);

        // CREATES LABEL WHERE TO SHOW THE USERNAME
        JLabel userField = new JLabel("User: " + namePlayer);
        userField.setBounds(30, 20, 150, 30);
        userField.setOpaque(false);
        rightPanel.add(userField);

        // CREATES LABEL WHERE TO SHOW THE HIGH SCORE OF THE CURRENT PLAYER
        highScoreField = new JLabel("High Score: " + new HistoryController(this, highScoreUpdater, board).getScore());
        highScoreField.setBounds(200, 20, 150, 30);
        highScoreField.setOpaque(false);
        rightPanel.add(highScoreField);

        // ADDS LEFT AND RIGHT PANEL TO THIS JFRAME
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    /**
     * This function is called by the constructor and set the Menu, adding the right Events and KeyStrokes
     *
     * @see JMenuItem
     * @see JMenuBar
     * @see JMenu
     */
    private void setMenu() {
        // CREATES THE MENU BAR
        JMenuBar menuBar = new JMenuBar();

        // CREATES THE FILE MENU
        JMenu fileMenu = new JMenu("File");

        // CREATES EVERY ITEM AND ADDS THE KEY STROKES
        saveMenu = new JMenuItem("Save as");
        saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        openMenu = new JMenuItem("Open");
        openMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        changeMenu = new JMenuItem("Change User");
        changeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        quitMenu = new JMenuItem("Quit");
        quitMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        saveMenu.addActionListener(this);
        fileMenu.add(saveMenu);
        openMenu.addActionListener(this);
        fileMenu.add(openMenu);
        changeMenu.addActionListener(this);
        fileMenu.add(changeMenu);
        quitMenu.addActionListener(this);
        fileMenu.add(quitMenu);

        // CREATES THE KLOTSKI MENU
        JMenu klotskiMenu = new JMenu("Klotski");

        // CREATES EVERY ITEM AND ADDS THE KEY STROKES
        resetMenu = new JMenuItem("Reset");
        resetMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        fConfiguration = new JMenuItem("Configuration 1");
        fConfiguration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
        sConfiguration = new JMenuItem("Configuration 2");
        sConfiguration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));
        tConfiguration = new JMenuItem("Configuration 3");
        tConfiguration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_DOWN_MASK));
        qConfiguration = new JMenuItem("Configuration 4");
        qConfiguration.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_DOWN_MASK));
        resetMenu.addActionListener(this);
        fConfiguration.addActionListener(this);
        sConfiguration.addActionListener(this);
        tConfiguration.addActionListener(this);
        qConfiguration.addActionListener(this);
        klotskiMenu.add(resetMenu);
        klotskiMenu.add(fConfiguration);
        klotskiMenu.add(sConfiguration);
        klotskiMenu.add(tConfiguration);
        klotskiMenu.add(qConfiguration);

        // CREATES THE HELP MENU
        JMenu helpMenu = new JMenu("Help");

        // CREATES EVERY ITEM AND ADDS THE KEY STROKES
        nextMoveMenu = new JMenuItem("Next Move");
        nextMoveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        nextMoveMenu.addActionListener(this);
        infoMenu = new JMenuItem("Info");
        infoMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        infoMenu.addActionListener(this);
        helpMenu.add(nextMoveMenu);
        helpMenu.add(infoMenu);

        menuBar.add(fileMenu);
        menuBar.add(klotskiMenu);
        menuBar.add(helpMenu);

        // ADD THE MENU TO THIS JFRAME
        this.setJMenuBar(menuBar);
    }

    /**
     * Function called to instantiate a new {@link GameSaver} object
     *
     * @see GameSaver
     */
    private void activateSaver() {
        gameSaver = new GameSaver(namePlayer, boardConfiguration);
    }

    /**
     * Function to start a new Timer from 0 and add Listeners also to {@link Block} and {@link Board}
     *
     * @see Timer
     */
    private void initializeTimer() {
        // SET TIMER TO 0 GRAPHICALLY
        timerText = "00:00:00";
        movesCounter = 0;
        timerTime = 0;
        clockPanel.setText(timerText);
        clockPanel.setHorizontalTextPosition(JLabel.RIGHT);
        rightPanel.add(clockPanel);

        // SET MOVES TO 0
        movesLabel.setText(String.valueOf(movesCounter));
        rightPanel.add(movesLabel);
        this.revalidate();
        this.repaint();
        timer = new Timer(1000, this);

        // ADD LISTENERS
        for (Block block : blocks) {
            block.addMouseListener(this);
        }
        board.addMouseListener(this);
    }

    /**
     * Function called to update the timer. It is already called when the timer is updating,<br>
     * but it can be called by other methods to update manually the timer
     */
    public void timerTextUpdate() {
        /*
            INCREMENT TIMER, AND UPDATE TIMER TEXT WITH 60-DIVISION AND REMAINDER
         */
        timerTime++;
        if (timerTime < 60) {
            if (timerTime < 10) {
                timerText = "00:00:0" + timerTime;
            } else {
                timerText = "00:00:" + timerTime;
            }
        } else if (timerTime < 3600) {
            int seconds = timerTime % 60;
            int minutes = timerTime / 60;
            if (minutes < 10) {
                if (seconds < 10) {
                    timerText = "00:0" + minutes + ":0" + seconds;
                } else {
                    timerText = "00:0" + minutes + ":" + seconds;
                }
            } else {
                if (seconds < 10) {
                    timerText = "00:" + minutes + ":0" + seconds;
                } else {
                    timerText = "00:" + minutes + ":" + seconds;
                }
            }
        } else {
            int hour = timerTime / 3600;
            int minutes = (timerTime - 3600) / 60;
            int seconds = timerTime % 60;
            if (hour < 10) {
                if (minutes < 10) {
                    if (seconds < 10) {
                        timerText = "0" + hour + ":0" + minutes + ":0" + seconds;
                    } else {
                        timerText = "0" + hour + ":0" + minutes + ":" + seconds;
                    }
                } else {
                    if (seconds < 10) {
                        timerText = "0" + hour + ":" + minutes + ":0" + seconds;
                    } else {
                        timerText = "0" + hour + ":" + minutes + ":" + seconds;
                    }
                }
            } else {
                if (minutes < 10) {
                    if (seconds < 10) {
                        timerText = hour + ":0" + minutes + ":0" + seconds;
                    } else {
                        timerText = hour + ":0" + minutes + ":" + seconds;
                    }
                } else {
                    if (seconds < 10) {
                        timerText = hour + ":" + minutes + ":0" + seconds;
                    } else {
                        timerText = hour + ":" + minutes + ":" + seconds;
                    }
                }
            }
        }

        // UPDATE THE PANEL
        clockPanel.setText(timerText);
        clockPanel.revalidate();
        clockPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    /**
     * Method called to upDate the number of the moves and call the right Controller, also to save the move
     *
     * @param selected  The index of the selected {@link Block} piece
     * @param direction The direction (One of the fourth in {@link BoardImp})
     * @see IncrementsMoveController
     */
    public void incrementsMovesCounter(int selected, int direction) {
        canMove = false;
        movesCounter++;
        movesLabel.setText(String.valueOf(movesCounter));
        this.revalidate();
        this.repaint();

        // CALLS THE RIGHT CONTROLLER FUNCTION
        new IncrementsMoveController(this, highScoreUpdater).incrementsMoveCounter(selected, direction);
        highScoreField.setText("High Score: " + highScoreUpdater.getScore(namePlayer));
        this.revalidate();
        this.repaint();
    }

    /**
     * Method called to upDate the number of the moves and call the right Controller, also to cancel saved move
     * @see IncrementsMoveController
     */
    public void decrementsMovesCounter() {
        canMove = false;
        if (movesCounter != 0)
            movesCounter--;
        movesLabel.setText(String.valueOf(movesCounter));

        // CALLS THE RIGHT CONTROLLER FUNCTION
        new IncrementsMoveController(this, highScoreUpdater).decrementsMoveCounter();
        highScoreField.setText("High Score: " + highScoreUpdater.getScore(namePlayer));
        this.revalidate();
        this.repaint();
    }

    /**
     * Save current game
     *
     * @see SaveGameController
     */
    public void saveGame() {
        new SaveGameController(this).saveGame();
    }

    /**
     * Save game deleting every previous data storage.<br>
     * Used when someone wins the game and exit
     *
     * @see SaveGameController
     */
    public void saveGameWon() {
        new SaveGameController(this).saveGame(true);
    }

    /**
     * Function called by other {@link Window} functions to underline button when clicked.<br>
     * It also underlines buttons when arrow-keys or WASD keys are clicked.<br>
     * It activates a timer of 150 milliseconds, after that it returns normal.
     *
     * @param button The button to underline
     */
    private void underlineButton(JButton button) {
        // SETS COLORS
        int b = 253;
        int r = 126;
        int g = 159;
        button.setBackground(new Color(r, g, b));
        this.revalidate();
        this.repaint();

        // CREATES TIMER
        Timer tim1 = new Timer(150, null);
        tim1.addActionListener(e -> {
            if (e.getSource() == tim1) {
                button.setBackground(buttonBackground);
                revalidate();
                repaint();
                tim1.stop();
            }
        });
        tim1.start();
    }

    /**
     * Create a new GameSaver object given the file path.<br>
     * It is useful when it's used to change the current save file
     *
     * @param path The path of the new Saving file
     * @see GameSaver
     */
    public void newSaver(String path) {
        // SAVES THE TEXT, CREATES A NEW FILE AND GIVES IT THE TEXT
        String text = gameSaver.getText();
        new SaveGameController(this).saveGame();
        gameSaver = new GameSaver(path, bImp.getConfiguration(), true);
        gameSaver.setText(text);
    }

    /**
     * Restart the game with a new selected configuration, taken from {@link BoardImp}
     *
     * @see Window#activateSaver()
     * @see Window#initializeTimer()
     */
    public void newConfiguration() {
        this.boardConfiguration = bImp.getConfiguration();
        leftPanel.remove(board);
        board = new Board(bImp);
        if (gameSaver != null) {
            new SaveGameController(this).saveGame();
        }
        activateSaver();
        initializeTimer();
    }

    /**
     * Reset the game with the selected configuration and start the timer
     */
    public void reset() {
        // RE-ADDS LISTENERS TO NEW BOARD AND BLOCKS, REVALIDATE AND START THE TIMER
        board.addMouseListener(this);
        blocks = bImp.getBlocks();
        for (Block block : blocks) {
            block.addMouseListener(this);
        }
        underlineButton(buttonReset);
        movesLabel.setText(String.valueOf(movesCounter));
        leftPanel.add(board, BorderLayout.CENTER);
        nextBestMove = new NextBestMove();
        this.revalidate();
        this.repaint();
        timer.start();
    }

    /**
     * Reset the game starting with a saved configuration, after a login or loading a file
     */
    public void resetHistory() {
        // NOT UNDERLINE BUTTON
        board.addMouseListener(this);
        blocks = bImp.getBlocks();
        for (Block block : blocks) {
            block.addMouseListener(this);
        }
        movesLabel.setText(String.valueOf(movesCounter));
        leftPanel.add(board, BorderLayout.CENTER);
        nextBestMove = new NextBestMove();
        this.revalidate();
        this.repaint();
        timer.start();
    }

    /**
     * Called when undoes the previous move, underline the correct button
     */
    public void undo() {
        underlineButton(undoButton);
    }


    // * SETTER METHODS *

    /**
     * Set the boolean value if the user is changed
     *
     * @param changedUser If the user is changed
     */
    public void setChangedUser(boolean changedUser) {
        this.changedUser = changedUser;
    }

    /**
     * Set the boolean value if the user has won
     *
     * @param won If the user won
     */
    public void setWon(boolean won) {
        this.won = won;
    }

    /**
     * Set the correct Timer Time
     *
     * @param timerTime Timer of the game in seconds
     */
    public void setTimerTime(int timerTime) {
        this.timerTime = timerTime;
    }

    /**
     * Set the correct number of moves
     *
     * @param movesCounter Number of moves
     */
    public void setMovesCounter(int movesCounter) {
        this.movesCounter = movesCounter;
    }


    // * HAS METHODS *

    /**
     * Get the boolean value if the user has won
     *
     * @return If the user has won
     */
    public boolean hasWon() {
        return won;
    }

    /**
     * Get the boolean value if the user has changed
     *
     * @return If the user has changed
     */
    public boolean hasChangedUser() {
        return changedUser;
    }


    // * METODI GETTER *

    /**
     * Get the current counter of the moves
     *
     * @return The number of moves
     */
    public int getMovesCounter() {
        return movesCounter;
    }

    /**
     * Get the current timer time
     *
     * @return The time in seconds
     */
    public int getTimerTime() {
        return timerTime;
    }

    /**
     * Get the Icon for displaying it in JOptionPane
     *
     * @return Icon in small dimensions
     */
    public ImageIcon getIcon_won() {
        return icon_won;
    }

    /**
     * Get the Board Configuration
     *
     * @return the {@link BoardImp Board Configuration}
     */
    public BoardImp getBoardImp() {
        return bImp;
    }

    /**
     * Get the Board of the game
     *
     * @return the {@link Board} game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get the {@link GameSaver} object in use
     *
     * @return the {@link GameSaver} object
     */
    public GameSaver getGameSaver() {
        return gameSaver;
    }

    /**
     * Get the name of the current player
     *
     * @return the current username
     */
    public String getPlayer() {
        return namePlayer;
    }


    // * ACTION LISTENERS *

    /**
     * Override of {@link ActionListener#actionPerformed(ActionEvent)} function.<br>
     * Every button, when performing an action, calls the right controller and stops or starts the timer if necessary
     * @param e the event to be processed
     * @see org.klotski.controller Controller
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            // UPDATE THE TIMER
            timerTextUpdate();
        }
        if (e.getSource() == saveMenu) {
            // CALLS THE SAVE FILE CONTROLLER, STOPS AND RE-STARTS THE TIMER
            timer.stop();
            new SaveFileController(this).save();
            timer.start();
        }
        if (e.getSource() == openMenu) {
            // CALLS THE OPEN FILE CONTROLLER, STOPS AND RE-STARTS THE TIMER
            timer.stop();
            new OpenFileController(this, highScoreUpdater).open();
            timer.start();
        }
        if (e.getSource() == changeMenu) {
            // DISPOSE CHANGING BOOLEAN PARAMS CHANGED-USER TO TRUE
            changedUser = true;
            dispose();
        }
        if (e.getSource() == quitMenu) {
            // CALLS THE QUIT CONTROLLER, STOPS AND RE-STARTS THE TIMER
            timer.stop();
            if (new QuitController().quit()) {
                this.dispose();
            }
            timer.start();
        }
        if (e.getSource() == resetMenu) {
            // CALLS THE RESET CONTROLLER
            new ResetController(this).reset();
        }
        if (e.getSource() == fConfiguration) {
            // CALLS THE SELECT CONFIGURATION CONTROLLER WITH THE FIRST CONFIGURATION
            new SelectConfigurationController(this).selectConfiguration(BoardImp.FIRST_CONFIGURATION);
        }
        if (e.getSource() == sConfiguration) {
            // CALLS THE SELECT CONFIGURATION CONTROLLER WITH THE SECOND CONFIGURATION
            new SelectConfigurationController(this).selectConfiguration(BoardImp.SECOND_CONFIGURATION);
        }
        if (e.getSource() == tConfiguration) {
            // CALLS THE SELECT CONFIGURATION CONTROLLER WITH THE THIRD CONFIGURATION
            new SelectConfigurationController(this).selectConfiguration(BoardImp.THIRD_CONFIGURATION);
        }
        if (e.getSource() == qConfiguration) {
            // CALLS THE SELECT CONFIGURATION CONTROLLER WITH THE FOURTH CONFIGURATION
            new SelectConfigurationController(this).selectConfiguration(BoardImp.FOURTH_CONFIGURATION);
        }
        if (e.getSource() == nextMoveMenu) {
            // CALLS THE NEXT MOVE CONTROLLER
            new BestMoveController(this, nextBestMove).nextBestMove();
        }
        if (e.getSource() == infoMenu) {
            // CALLS THE INFO CONTROLLER TO SHOW INFO
            new InfoController(this).info();
        }
        if (e.getSource() == buttonReset) {
            // CALLS THE RESET CONTROLLER
            new ResetController(this).reset();
        }
        if (e.getSource() == rightArrow) {
            // CALLS THE MOVE CONTROLLER WITH THE RIGHT DIRECTION
            underlineButton(rightArrow);
            new MoveController(this).move(BoardImp.RIGHT);
        }
        if (e.getSource() == leftArrow) {
            // CALLS THE MOVE CONTROLLER WITH THE LEFT DIRECTION
            underlineButton(leftArrow);
            new MoveController(this).move(BoardImp.LEFT);
        }
        if (e.getSource() == upArrow) {
            // CALLS THE MOVE CONTROLLER WITH THE UP DIRECTION
            underlineButton(upArrow);
            new MoveController(this).move(BoardImp.UP);
        }
        if (e.getSource() == bottomArrow) {
            // CALLS THE MOVE CONTROLLER WITH THE DOWN DIRECTION
            underlineButton(bottomArrow);
            new MoveController(this).move(BoardImp.DOWN);
        }
        if (e.getSource() == undoButton) {
            // CALLS THE UNDO CONTROLLER
            new UndoController(this).undo();
        }
    }


    // * MOUSE EVENTS *

    /**
     * Override of {@link MouseListener#mouseClicked(MouseEvent)} function.<br>
     * Calls the {@link BoardSelectController} to select or deSelect a piece when is clicked
     *
     * @param e the event to be processed
     * @see BoardSelectController Controller
     * @see MouseListener
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == board) {
            new BoardSelectController(this.getBoard()).deSelect();
        }
        for (int i = 0; i < blocks.length; i++) {
            if (e.getSource() == blocks[i]) {
                new BoardSelectController(this.getBoard()).select(i);
                canMove = true;
            }
        }
    }

    /**
     * Override of {@link MouseListener#mousePressed(MouseEvent)} function.<br>
     * Calls the {@link BoardSelectController} to select or deSelect a piece when is pressed
     *
     * @param e the event to be processed
     * @see BoardSelectController Controller
     * @see MouseListener
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == board) {
            new BoardSelectController(this.getBoard()).deSelect();
        }
        for (int i = 0; i < blocks.length; i++) {
            if (e.getSource() == blocks[i]) {
                new BoardSelectController(this.getBoard()).moveSelect(i, e);
                // CAN MOVE SETS TO TRUE TO PERFORM drag and drop
                canMove = true;
            }
        }
    }

    /**
     * Override of {@link MouseListener#mouseReleased(MouseEvent)} function.<br>
     * Calls the {@link MoveController} to move a piece with drag and drop
     *
     * @param e the event to be processed
     * @see MoveController Controller
     * @see MouseListener
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (Block block : blocks) {
            if (e.getSource() == block) {
                if (canMove) {
                    new MoveController(this).move(e);
                }
                // SETS CAN MOVE TO FALSE FOR AVOIDING BUGS
                canMove = false;
            }
        }
    }

    /**
     * Forced implementation of {@link  MouseListener#mouseEntered(MouseEvent)} function.<br>
     * It is useless in this case, so it is void.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Forced implementation of {@link  MouseListener#mouseExited(MouseEvent)} function.<br>
     * It is useless in this case, so it is void.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }


    // * KEY EVENTS *

    /**
     * Override of {@link KeyListener#keyTyped(KeyEvent)} function.<br>
     * Calls the {@link MoveController} to move a piece with select and move
     *
     * @param e the event to be processed
     * @see MoveController Controller
     * @see KeyListener
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // CALLS THE RIGHT CONTROLLER WITH THE RIGHT DIRECTION BASED OF THE KEY CHAR
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
            underlineButton(rightArrow);
            new MoveController(this).move(BoardImp.RIGHT);
        }
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
            underlineButton(leftArrow);
            new MoveController(this).move(BoardImp.LEFT);
        }
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
            underlineButton(upArrow);
            new MoveController(this).move(BoardImp.UP);
        }
        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
            underlineButton(bottomArrow);
            new MoveController(this).move(BoardImp.DOWN);
        }
    }

    /**
     * Override of {@link KeyListener#keyPressed(KeyEvent)} function.<br>
     * Calls the {@link MoveController} to move a piece with select and move or undo/reset keystrokes
     *
     * @param e the event to be processed
     * @see MoveController Controller
     * @see KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // CALLS THE RIGHT CONTROLLER WITH THE RIGHT DIRECTION BASED OF THE KEY CODE
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            underlineButton(rightArrow);
            new MoveController(this).move(BoardImp.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            underlineButton(leftArrow);
            new MoveController(this).move(BoardImp.LEFT);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            underlineButton(upArrow);
            new MoveController(this).move(BoardImp.UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            underlineButton(bottomArrow);
            new MoveController(this).move(BoardImp.DOWN);
        }
        if (e.isControlDown() && e.getKeyChar() != 'r' && e.getKeyChar() != 'R' && e.getKeyCode() == KeyEvent.VK_R) {
            // UNDERLINE BUTTON RESET IF CTRL+R IS CLICKED
            underlineButton(buttonReset);
        }

        if (e.isControlDown() && e.getKeyChar() != 'z' && e.getKeyChar() != 'Z' && e.getKeyCode() == KeyEvent.VK_Z) {
            // UNDERLINE BUTTON RESET IF CTRL+Z IS CLICKED
            underlineButton(undoButton);
            new UndoController(this).undo();
        }
    }

    /**
     * Forced implementation of {@link  KeyListener#keyReleased(KeyEvent)} function.<br>
     * It is useless in this case, so it is void.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }


    // * WINDOW EVENTS *

    /**
     * Override of {@link WindowListener#windowClosing(WindowEvent)} function.<br>
     * Calls the {@link QuitController} to confirm closing
     *
     * @param e the event to be processed
     * @see QuitController Controller
     * @see WindowListener
     */
    @Override
    public void windowClosing(WindowEvent e) {
        timer.stop();
        if (new QuitController().quit()) {
            this.dispose();
        } else {
            timer.start();
        }
    }

    /**
     * Override of {@link WindowListener#windowClosing(WindowEvent)} function.<br>
     * Stops the timer when the window is closed
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {
        timer.stop();
    }

    /**
     * Override of {@link WindowListener#windowClosing(WindowEvent)} function.<br>
     * Stops the timer when the window is iconified
     *
     * @param e the event to be processed
     */
    @Override
    public void windowIconified(WindowEvent e) {
        timer.stop();
    }

    /**
     * Override of {@link WindowListener#windowClosing(WindowEvent)} function.<br>
     * Starts the timer when the window is deIconified
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
        timer.start();
    }

    /**
     * Override of {@link WindowListener#windowClosing(WindowEvent)} function.<br>
     * Starts the timer when the window is deIconified
     *
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {
        timer.start();
    }

    /**
     * Forced implementation of {@link  WindowListener#windowActivated(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {
    }

    /**
     * Forced implementation of {@link  WindowListener#windowDeactivated(WindowEvent)} function.<br>
     * It is useless in this case, so it is void.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}