package org.klotski.graphics;

// IMPORT GRAPHIC LIBRARIES
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;


/**
 * Class Block that extends JPanel to set graphical pieces for basics elements in Klotski.
 * Every block has a type and his large and width.
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class Block extends JPanel {
    // * STATIC FIELDS *
    /** Define the 2-block width
     */
    public static final int LARGE_WIDTH = 210;
    /** Define the 1-block width
     */
    public static final int SMALL_WIDTH = 105;
    /** Define the 2-block height
     */
    public static final int LARGE_HEIGHT = 210;
    /** Define the 1-block height
     */
    public static final int SMALL_HEIGHT = 105;
    /** Define the UP direction
     */
    public static final int UP = 0;
    /** Define the DOWN direction
     */
    public static final int DOWN = 1;
    /** Define the RIGHT direction
     */
    public static final int RIGHT = 2;
    /** Define the LEFT direction
     */
    public static final int LEFT = 3;

    // * PRIVATE FIELDS *
    /**
     * Store X and Y Coordinates
     */
    private final Point point;
    /**
     * Background Color
     */
    private final Color colorPieces;
    /**
     * Background Color for the main square
     */
    private final Color redColor;
    /**
     * Border for blocks
     */
    private final Border border;
    /**
     * Border for blocks
     */
    private final Border borderLine;
    /**
     * Type of the block<br>
     * 0 - Main square, 1 - Small square, 2 - Vertical rectangle, 3 - Horizontal rectangle
     */
    private final int type;

    /**
     * Construct a new Block to instantiate a single piece with coordinates and type.
     * <p> Here is an example of code:
     * <blockquote><pre>{@code
     *     Block block = new Block(0, 105, 210);
     *     block.move(Block.UP);
     * }</pre></blockquote>
     *
     * @param type type of the block (0 = large square, 1 = little square, 2 = vertical rectangle, 3 = horizontal rectangle)
     * @param x the x-coordinate of the left top corner
     * @param y the y-coordinate of the left top corner
     *
     * @throws IllegalArgumentException If coordinates are negative or over the limits
     */
    public Block(int type, int x, int y) {
        if (x < 0 || y < 0 || x > 315 || y > 420) {                     // checks position x and y
            throw new IllegalArgumentException("Non valid coordinates");
        }
        // instantiates local variables
        this.type = type;
        this.colorPieces = new Color(208, 197, 182, 252);
        this.redColor = new Color(192, 90, 90, 250);
        this.border = BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(217, 214, 214));
        this.borderLine = BorderFactory.createLineBorder(Color.black, 1);
        this.point = new Point(x, y);

        // calls setStart function
        setStart();
    }

    /**
     * Set graphical parameters - Called by the constructor.
     */
    private void setStart() {
        // set Panel graphic elements
        this.requestFocus();
        this.setOpaque(true);
        this.setBorder(new CompoundBorder(border, borderLine));
        this.rebounds();
    }

    /**
     * Set the background color when a block is clicked.
     */
    protected void mouseClickedBlock() {
        setBackground(Color.BLUE);
    }

    /**
     * Reset the correct background color of the block.
     */
    protected void removeMouseClickedBlock() {
        if (this.type == 0) {   // check if this block is a large square
            this.setBackground(redColor);
        } else {
            this.setBackground(colorPieces);
        }
    }

    /**
     * Move this block to a specified direction. The block is moved by only one position
     * Every position is identified by 105 pixels of difference.
     * It calls the {@link Point#move(int, int)} function of the class {@link Point}.
     * It Calls the {@link Block#reboundsNoColor()} function to repaint the block without changing colors.
     * @param direction <p>
     *                  Integer that represent one of the fourth possible direction using static constants:
     *                  (
     *                    {@link Block#UP} - {@value UP},
     *                    {@link Block#DOWN} - {@value DOWN},
     *                    {@link Block#RIGHT} - {@value RIGHT},
     *                    {@link Block#LEFT} - {@value LEFT},
     *                  )
     *
     * @throws IllegalArgumentException If <em>direction</em> is out of params
     */
    public void move(int direction) {
        // checks the direction and move this block
        switch (direction) {
            case UP -> {
                point.move((int) point.getX(), (int) point.getY() - 105);
                this.reboundsNoColor();
            }
            case DOWN -> {
                point.move((int) point.getX(), (int) point.getY() + 105);
                this.reboundsNoColor();
            }
            case RIGHT -> {
                point.move((int) point.getX() + 105, (int) point.getY());
                this.reboundsNoColor();
            }
            case LEFT -> {
                point.move((int) point.getX() - 105, (int) point.getY());
                this.reboundsNoColor();
            }
            default -> throw new IllegalArgumentException("Direction out of range");
        }
    }

    /**
     * Move this block to a specified direction. It is used to undo the previous movement.
     * The block is moved by only one position
     * Every position is identified by 105 pixels of difference.
     * It calls the {@link Point#move(int, int)} function of the class {@link Point}.
     * It Calls the {@link Block#rebounds()} function to repaint the block changing it with default colors.
     * @param direction <p>
     *                  Integer that represent one of the fourth possible direction using static constants:
     *                  <blockquote>( {@link Block#UP} - {@value UP}
     *                    </blockquote>
     *                  <blockquote>  {@link Block#DOWN} - {@value DOWN}
     *                  </blockquote>
     *                    <blockquote>  {@link Block#RIGHT} - {@value RIGHT}
     *                  </blockquote>
     *                    <blockquote>  {@link Block#LEFT} - {@value LEFT} )
     *                  </blockquote>
     *
     * @throws IllegalArgumentException If <em>direction</em> is out of params
     */
    public void moveBack(int direction) {
        // checks the direction and move this block
        switch (direction) {
            case UP -> {
                point.move((int) point.getX(), (int) point.getY() - 105);
                this.rebounds();
            }
            case DOWN -> {
                point.move((int) point.getX(), (int) point.getY() + 105);
                this.rebounds();
            }
            case RIGHT -> {
                point.move((int) point.getX() + 105, (int) point.getY());
                this.rebounds();
            }
            case LEFT -> {
                point.move((int) point.getX() - 105, (int) point.getY());
                this.rebounds();
            }
        }
    }

    /**
     * Set new bounds for this block.<br>
     * It is useful if it's used in a container with null Layout, for example:
     * <blockquote><pre>{@code
     *     Block block = new Block(0, 105, 210);
     *     JPanel panel = new JPanel();
     *     panel.setLayout(null);
     *     panel.add(block);
     *     block.reboundsNoColor();
     * }</pre></blockquote>
     *
     * It uses static constants for the dimensions X and Y: <br>
     * ({@link Block#LARGE_WIDTH} - {@value LARGE_WIDTH}
     *  {@link Block#SMALL_WIDTH} - {@value SMALL_WIDTH}
     *  {@link Block#LARGE_HEIGHT} - {@value LARGE_HEIGHT}
     *  {@link Block#SMALL_HEIGHT} - {@value SMALL_HEIGHT}
     * )
     */
    public void reboundsNoColor() {
        if (this.type == 0) {  // main square
            this.setBounds((int) (point.getX()), (int) (point.getY()), LARGE_WIDTH, LARGE_HEIGHT);
        } else if (this.type == 1) { // small square
            this.setBounds((int) (point.getX()), (int) (point.getY()), SMALL_WIDTH, SMALL_HEIGHT);
        } else if (this.type == 2) { // vertical rectangle
            this.setBounds((int) (point.getX()), (int) (point.getY()), SMALL_WIDTH, LARGE_HEIGHT);
        } else if (this.type == 3) { // horizontal rectangle
            this.setBounds((int) (point.getX()), (int) (point.getY()), LARGE_WIDTH, SMALL_HEIGHT);
        }
    }

    /**
     * Set new bounds for this block and the default correct color.
     * It is useful if it's used in a container with null Layout, for example:
     * <blockquote><pre>{@code
     *     Block block = new Block(0, 105, 210);
     *     JPanel panel = new JPanel();
     *     panel.setLayout(null);
     *     panel.add(block);
     *     block.reboundsNoColor();
     * }</pre></blockquote>
     *
     * It uses static constants for the dimensions X and Y: <br>
     * ({@link Block#LARGE_WIDTH} - {@value LARGE_WIDTH}
     *  {@link Block#SMALL_WIDTH} - {@value SMALL_WIDTH}
     *  {@link Block#LARGE_HEIGHT} - {@value LARGE_HEIGHT}
     *  {@link Block#SMALL_HEIGHT} - {@value SMALL_HEIGHT}
     * )
     */
    public void rebounds() {
        if (this.type == 0) {  // main square
            this.setBounds((int) (point.getX()), (int) (point.getY()), LARGE_WIDTH, LARGE_HEIGHT);
            this.setBackground(redColor);
        } else if (this.type == 1) { // small square
            this.setBounds((int) (point.getX()), (int) (point.getY()), SMALL_WIDTH, SMALL_HEIGHT);
            this.setBackground(colorPieces);
        } else if (this.type == 2) { // vertical rectangle
            this.setBounds((int) (point.getX()), (int) (point.getY()), SMALL_WIDTH, LARGE_HEIGHT);
            this.setBackground(colorPieces);
        } else if (this.type == 3) { // horizontal rectangle
            this.setBounds((int) (point.getX()), (int) (point.getY()), LARGE_WIDTH, SMALL_HEIGHT);
            this.setBackground(colorPieces);
        }
    }

    /*
          * GETTER METHODS *
     */

    /**
     * Get the X coordinate of the block.
     * @return X coord
     */
    @Override
    public int getX() {
        return (int) point.getX();
    }

    /**
     * Get the Y coordinate of the block.
     * @return Y coord
     */
    @Override
    public int getY() {
        return (int) point.getY();
    }

    /**
     * Get the Y coordinate of the block.
     * @return block type
     */
    public int getType() {
        return type;
    }
}
