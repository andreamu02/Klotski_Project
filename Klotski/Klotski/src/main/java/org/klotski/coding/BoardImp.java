package org.klotski.coding;

// IMPORT BLOCK AND OTHER UTILITIES
import org.klotski.graphics.Block;

import java.awt.Point;
import java.util.AbstractMap;
import java.util.Stack;

/**
 * <em>BoardImp</em> is the main {@link org.klotski.coding coding} class.<br>
 * It creates the {@link Block} array and sets it in the correct way.<br>
 * It also checks for moves them and selects them.<br>
 * It creates configuration for {@link org.klotski.graphics.Board Board} following Creator design pattern
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class BoardImp {
    /**
     * The {@link Block} array
     */
    private Block[] blocks;
    /**
     * The selected block
     */
    private Block selected;
    /**
     * The stack where to store every move
     */
    private Stack<AbstractMap.SimpleEntry<Integer, Integer>> movesStack;
    /**
     * Boolean value set to true when someone wins the game
     */
    private boolean hasWon;
    /**
     * The index of the selected {@link Block} in {@link BoardImp#blocks the block array}
     */
    private int selectedPosition;
    /**
     * The selected configuration (see static fields)
     */
    private int configuration;
    /**
     * The direction of the last move
     */
    private int lastDirection;

    //* STATIC FIELDS *
    /**
     *The first configuration
     */
    public static final int FIRST_CONFIGURATION = 1;
    /**
     *The second configuration
     */
    public static final int SECOND_CONFIGURATION = 2;
    /**
     *The third configuration
     */
    public static final int THIRD_CONFIGURATION = 3;
    /**
     *The fourth configuration
     */
    public static final int FOURTH_CONFIGURATION = 4;
    /**
     * The up direction
     */
    public static final int UP = 0;
    /**
     * The down direction
     */
    public static final int DOWN = 1;
    /**
     * The right direction
     */
    public static final int RIGHT = 2;
    /**
     * The left direction
     */
    public static final int LEFT = 3;

    /**
     * Constructor that sets initial configuration to {@link BoardImp#FIRST_CONFIGURATION} = {@value FIRST_CONFIGURATION}
     */
    public BoardImp()
    {
        // SETS ALL THE INITIAL PARAMS
        blocks = new Block[10];
        configuration = FIRST_CONFIGURATION;
        restart();
        selected = null;
        selectedPosition = -1;
        hasWon = false;
        movesStack = new Stack<>();
        lastDirection = -1;
    }

    /**
     * Constructor that sets initial configuration to a given configuration
     * @throws IllegalArgumentException If a configuration exceeds the range
     * @param configuration The selected int configuration
     */
    public BoardImp(int configuration)
    {
        if(configuration<FIRST_CONFIGURATION || configuration>FOURTH_CONFIGURATION){
            throw new IllegalArgumentException("Configuration out of range");
        }
        // SETS ALL THE INITIAL PARAMS
        blocks = new Block[10];
        this.configuration = configuration;
        restart();
        selected = null;
        selectedPosition = -1;
        hasWon = false;
        movesStack = new Stack<>();
        lastDirection = -1;
    }

    /**
     * Restart BoardImp with the selected configuration or start a new one
     */
    public void restart() {
        // SET INITIAL PARAMS AND THE INITIAL CONFIGURATION
        movesStack = new Stack<>();
        selected = null;
        selectedPosition = -1;
        hasWon = false;
        blocks = new Block[10];

        // THE MAIN SQUARE IS ALWAYS IN THE 0-POSITION
        if (configuration == FIRST_CONFIGURATION) {
            blocks[0] = new Block(0, 105, 0);
            blocks[1] = new Block(2, 0, 0);
            blocks[2] = new Block(2, 315, 0);
            blocks[3] = new Block(2, 0, 210);
            blocks[4] = new Block(1, 105, 315);
            blocks[5] = new Block(1, 210, 315);
            blocks[6] = new Block(2, 315, 210);
            blocks[7] = new Block(1, 0, 420);
            blocks[8] = new Block(1, 315, 420);
            blocks[9] = new Block(3, 105, 210);
        } else if (configuration == SECOND_CONFIGURATION) {
            blocks[0] = new Block(0, 105, 0);
            blocks[1] = new Block(1, 0, 0);
            blocks[2] = new Block(1, 315, 0);
            blocks[3] = new Block(2, 0, 105);
            blocks[4] = new Block(2, 315, 105);
            blocks[5] = new Block(2, 105, 210);
            blocks[6] = new Block(1, 0, 315);
            blocks[7] = new Block(1, 315, 315);
            blocks[8] = new Block(3, 0, 420);
            blocks[9] = new Block(3, 210, 420);
        } else if (configuration == THIRD_CONFIGURATION) {
            blocks[0] = new Block(0, 210, 105);
            blocks[1] = new Block(2, 0, 0);
            blocks[2] = new Block(1, 105, 0);
            blocks[3] = new Block(1, 210, 0);
            blocks[4] = new Block(1, 315, 0);
            blocks[5] = new Block(2, 105, 105);
            blocks[6] = new Block(2, 0, 210);
            blocks[7] = new Block(3, 105, 315);
            blocks[8] = new Block(1, 315, 315);
            blocks[9] = new Block(3, 210, 420);
        } else if (configuration == FOURTH_CONFIGURATION) {
blocks[0] = new Block(0, 105, 0);
            blocks[1] = new Block(2, 0, 0);
            blocks[2] = new Block(2, 315, 0);
            blocks[3] = new Block(2, 0, 210);
            blocks[4] = new Block(1, 105, 210);
            blocks[5] = new Block(1, 210, 210);
            blocks[6] = new Block(2, 315, 210);
            blocks[7] = new Block(1, 105, 315);
            blocks[8] = new Block(1, 210, 315);
            blocks[9] = new Block(3, 105, 420);
        }
    }


    // * MOVE METHODS *

    /**
     * Moves the selected {@link Block} with the given direction
     * @param dir The direction where to move the block
     * @throws IllegalArgumentException If a direction exceeds the range
     * @return true if the Block moved, false otherwise
     */
    public boolean moveBlock(int dir) {
        // CHECKS IF THE SELECTED BLOCK CANNOT MOVE
        if (selected == null) return false;
        if (dir < UP || dir > LEFT) throw new IllegalArgumentException("Direction over or under the limits");
        // FOR EVERY DIRECTION CHECKS IF THE POINT IS OUT OF THE BOARD, IF NOT CALLS THE RIGHT MOVE CONTROLLER
        if (dir == UP) {
            Point newPosition = new Point(selected.getX(), selected.getY() - 105);
            if (newPosition.getY() < 0) {
                return false;
            }
            if (selected.getType() == 1 || selected.getType() == 2) {
                if(!moveUpSmall()){
                    return false;
                }
            } else {
                if(!moveUpBig()){
                    return false;
                }
            }
        } else if (dir == DOWN) {
            Point newPosition = new Point(selected.getX(), selected.getY() + selected.getHeight());
            if (this.selected.getType() != 0) {
                if (newPosition.getY() > 420) {
                    return false;
                }
            } else {
                if (newPosition.getY() > 420 && newPosition.getX() != 105) {
                    return false;
                } else if (newPosition.getY() > 420) {
                    hasWon = true;
                    return true;
                }
            }
            if (selected.getType() == 1 || selected.getType() == 2) {
                if(!moveDownSmall()){
                    return false;
                }
            } else {
                if(!moveDownBig()){
                    return false;
                }
            }
        } else if (dir == RIGHT) {
            Point newPosition = new Point(selected.getX() + selected.getWidth(), selected.getY());
            if (newPosition.getX() > 315) {
                return false;
            }
            if (selected.getType() == 1 || selected.getType() == 3) {
                if(!moveRightSmall()){
                    return false;
                }
            } else {
                if(!moveRightBig()){
                    return false;
                }
            }
        } else {
            Point newPosition = new Point(selected.getX() - 105, selected.getY());
            if (newPosition.getX() < 0) {
                return false;
            }
            if (selected.getType() == 1 || selected.getType() == 3) {
                if (!moveLeftSmall()) {
                    return false;
                }
            } else {
                if (!moveLeftBig()) {
                    return false;
                }
            }
        }
        // ADDS THE MOVES TO STACK AND MOVES THE SELECTED BLOCK
        AbstractMap.SimpleEntry<Integer, Integer> moves = new AbstractMap.SimpleEntry<>(getSelectedPosition(), dir);
        movesStack.add(moves);
        lastDirection = dir;
        selected.move(dir);
        return true;
    }

    /**
     * Checks if a block with a small up range can move
     * @return True if a block can move up, false otherwise
     */
    private boolean moveUpSmall() {
        // CHECKS EVERY SMALL POSSIBILITIES
        Point positionToCheck = new Point(selected.getX(), selected.getY() - 105);
        for (Block block : blocks) {
            if (new Point(block.getX(), block.getY() + block.getHeight() - 105).equals(positionToCheck)) {
                return false;
            }
        }
        for (Block block : blocks) {
            if (new Point(block.getX() + block.getWidth() - 105, block.getY() + block.getHeight() - 105).equals(positionToCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a block with a big up range can move
     * @return True if a block can move up, false otherwise
     */
    private boolean moveUpBig() {
        // CHECKS EVERY BIG POSSIBILITIES
        Point[] positionsToChecks = {new Point(selected.getX(), selected.getY() - 105), new Point(selected.getX() + 105, selected.getY() - 105)};
        for (Point positionsToCheck : positionsToChecks) {
            for (Block block : blocks) {
                if (new Point(block.getX(), block.getY() + block.getHeight() - 105).equals(positionsToCheck)) {
                    return false;
                }
                if (new Point(block.getX() + block.getWidth() - 105, block.getY() + block.getHeight() - 105).equals(positionsToCheck)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a block with a small down range can move
     * @return True if a block can move down, false otherwise
     */
    private boolean moveDownSmall() {
        // CHECKS EVERY SMALL POSSIBILITIES
        Point positionToCheck = new Point(selected.getX(), selected.getY() + selected.getHeight());
        for (Block block : blocks) {
            if (new Point(block.getX(), block.getY()).equals(positionToCheck)) {
                return false;
            }
        }
        for (Block block : blocks) {
            if (new Point(block.getX() + block.getWidth() - 105, block.getY()).equals(positionToCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a block with a big down range can move
     * @return True if a block can move down, false otherwise
     */
    private boolean moveDownBig() {
        // CHECKS EVERY BIG POSSIBILITIES
        Point[] positionsToChecks = {new Point(selected.getX(), selected.getY() + selected.getHeight()), new Point(selected.getX() + 105, selected.getY() + selected.getHeight())};
        for (Point positionToCheck : positionsToChecks) {
            for (Block block : blocks) {
                if (new Point(block.getX(), block.getY()).equals(positionToCheck)) {
                    return false;
                }
                if (new Point(block.getX() + block.getWidth() - 105, block.getY()).equals(positionToCheck)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a block with a small right range can move
     * @return True if a block can move right, false otherwise
     */
    private boolean moveRightSmall() {
        // CHECKS EVERY SMALL POSSIBILITIES
        Point positionToCheck = new Point(selected.getX() + selected.getWidth(), selected.getY());
        for (Block block : blocks) {
            if (new Point(block.getX(), block.getY()).equals(positionToCheck))
                return false;
        }
        for (Block block : blocks) {
            if (new Point(block.getX(), block.getY() + block.getHeight() - 105).equals(positionToCheck))
                return false;
        }
        return true;
    }

    /**
     * Checks if a block with a big right range can move
     * @return True if a block can move right, false otherwise
     */
    private boolean moveRightBig() {
        // CHECKS EVERY BIG POSSIBILITIES
        Point[] positionsToCheck = {new Point(selected.getX() + selected.getWidth(), selected.getY()), new Point(selected.getX() + selected.getWidth(), selected.getY() + selected.getHeight() - 105)};
        for (Point positionToCheck : positionsToCheck) {
            for (Block block : blocks) {
                if (new Point(block.getX(), block.getY()).equals(positionToCheck)) {
                    return false;
                }
                if (new Point(block.getX(), block.getY() + block.getHeight() - 105).equals(positionToCheck)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a block with a small left range can move
     * @return True if a block can move left, false otherwise
     */
    private boolean moveLeftSmall() {
        // CHECKS EVERY SMALL POSSIBILITIES
        Point positionToCheck = new Point(selected.getX() - 105, selected.getY());
        for (Block block : blocks) {
            if (new Point(block.getX() + block.getWidth() - 105, block.getY()).equals(positionToCheck))
                return false;
        }
        for (Block block : blocks) {
            if (new Point(block.getX() + block.getWidth() - 105, block.getY() + block.getHeight() - 105).equals(positionToCheck))
                return false;
        }
        return true;
    }

    /**
     * Checks if a block with a big left range can move
     * @return True if a block can move left, false otherwise
     */
    private boolean moveLeftBig() {
        // CHECKS EVERY BIG POSSIBILITIES
        Point[] positionsToCheck = {new Point(selected.getX() - 105, selected.getY()), new Point(selected.getX() - 105, selected.getY() + selected.getHeight() - 105)};
        for (Point positionToCheck : positionsToCheck) {
            for (Block block : blocks) {
                if (new Point(block.getX()+ block.getWidth() - 105, block.getY()).equals(positionToCheck)) {
                    return false;
                }
                if (new Point(block.getX()+ block.getWidth() - 105, block.getY() + block.getHeight() - 105).equals(positionToCheck)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Undoes the last move, removing it from the stack and moving back the {@link Block}
     */
    public void undo() {
        if (movesStack.isEmpty()) {
            return;
        }
        // POP THE LAST MOVE AND MAKES THE MOVES TO THE OTHER SENSE
        AbstractMap.SimpleEntry<Integer, Integer> element = movesStack.pop();
        int index = element.getKey();
        int direction = element.getValue();
        selectedPosition = index;
        selected = blocks[selectedPosition];
        if (direction == UP) {
            selected.moveBack(DOWN);
        } else if (direction == DOWN) {
            selected.moveBack(UP);
        } else if (direction == RIGHT) {
            selected.moveBack(LEFT);
        } else if (direction == LEFT) {
            selected.moveBack(RIGHT);
        }
        selected = null;
        selectedPosition = -1;
    }


    // * SETTER METHODS *

    /**
     * Set a preferred configuration and restart the BoardImp information
     * @throws IllegalArgumentException If a configuration exceeds the range
     * @param configuration The selected configuration
     */
    public void setConfiguration(int configuration) {
        if ((configuration < FIRST_CONFIGURATION) || (configuration > FOURTH_CONFIGURATION)) {
            throw new IllegalArgumentException();
        }
        this.configuration = configuration;
        // RESTART BOARDIMP
        restart();
    }

    /**
     * Sets the selected block (useful if it became selected from {@link org.klotski.controller Controller} classes
     * @param selected The index of the selected block in the array. If it's equal to -1 removes the selection
     */
    public void setSelected(int selected) {
        if (selected == -1) {
            // NO BLOCKS SELECTED
            this.selected = null;
            selectedPosition = -1;
            return;
        }
        // SELECTS THE BLOCK
        this.selected = blocks[selected];
        selectedPosition = selected;
    }


    // * GETTER METHODS *

    /**
     * Returns the direction for a drag and drop movement given previous and next params with a math formula
     * @param xPrev The X previous position
     * @param yPrev The Y previous position
     * @param xPos The X next position
     * @param yPos The Y next position
     * @return One of the fourth directions possible
     */
    public int getDirection(int xPrev, int yPrev, int xPos, int yPos) {
        // CHECKS FOR VERTICAL DIRECTION
        if (Math.abs(xPos - xPrev) <= Math.abs(yPos - yPrev)) {
            return ((yPos - yPrev) < 0) ? Block.UP : Block.DOWN;
        } else {
            // CHECKS FOR HORIZONTAL DIRECTION
            return ((xPos - xPrev) < 0) ? Block.LEFT : Block.RIGHT;
        }
    }

    /**
     * Get the current index of the selected block
     * @return The selected block index
     */
    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    /**
     * Get the direction of the last move
     * @return The last direction
     */
    public int getLastDirection() {
        return lastDirection;
    }

    /**
     * Get the {@link Block} array
     * @return The array of block
     */
    public Block[] getBlocks() {
        return blocks;
    }

    /**
     * Get the configuration in use
     * @return The used configuration
     */
    public int getConfiguration() {
        return configuration;
    }


    // * HAS METHODS *

    /**
     * Gives the boolean value to indicate if a user won
     * @return True if a user won
     */
    public boolean hasWon() {
        return hasWon;
    }
}
