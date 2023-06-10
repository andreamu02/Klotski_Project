package org.klotski.graphics;

// IMPORT BOARDIMP FOR CONFIGURATIONS AND GRAPHICAL LIBRARIES
import org.klotski.coding.BoardImp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Class Board that extends JPanel to set graphical game board for Klotski game.
 * It is filled with {@link Block} pieces, it follows {@link BoardImp} guidelines.
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class Board extends JPanel {
    // * PRIVATE FIELDS *
    /**
     * Blocks array to set colors
     */
    private Block[] blocks;
    /**
     * Configuration, final type because Board never change it
     */
    private final BoardImp bImp;
    /**
     * Current X position
     */
    private int xCurrent;
    /**
     * X position for move
     */
    private int xFuture;
    /**
     * Current Y position
     */
    private int yCurrent;
    /**
     Y position for move
     */
    private int yFuture;

    /**
     * Construct a new {@link Board} object that represent board graphically. <br>
     * It sets graphical elements and get the pieces from current configuration in {@link BoardImp}.
     * <p> Here is an example of code:
     * <blockquote><pre>{@code
     *     BoardImp boardImp = new boardImp();
     *     Board board = new Board(boardImp);
     * }</pre></blockquote>
     *
     * @param boardImp {@link BoardImp} object to link graphics with codings
     *
     * @throws IllegalArgumentException If boardImp is null
     */
    public Board(BoardImp boardImp) {
        if(boardImp == null){
            throw new IllegalArgumentException("BoardImp cannot be null");
        }
        this.bImp = boardImp;
        createPieces();
        activate();
    }

    /**
     * Get blocks from boardImp. Called by the constructor.
     */
    private void createPieces() {
        blocks = bImp.getBlocks();
    }

    /**
     * Set basic graphical elements and add Block (panel) to this (panel). Called by the constructor
     */
    private void activate() {
        // SET GRAPHICAL ELEMENTS WITH SUPER() METHODS
        this.setLayout(null);
        this.setBackground(new Color(217, 214, 214));
        this.setOpaque(true);
        this.requestFocus();
        for (Block block : blocks) {
            this.add(block);
        }
        this.setPreferredSize(new Dimension(420, 525));
        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(192, 90, 90, 250));
        linePanel.setBounds(100, 528, 220, 5);
        this.add(linePanel);
        this.xCurrent = -1;
        this.yCurrent = -1;
        this.xFuture = -1;
        this.yFuture = -1;
    }

    /**
     * Get this associated {@link BoardImp} object
     * @return current boardImp
     */
    public BoardImp getBoardImp() {
        return bImp;
    }

    /**
     * Remove selection from any block
     */
    public void deSelect() {
        int selected = bImp.getSelectedPosition();
        if (selected != -1) {
            blocks[selected].removeMouseClickedBlock();
            bImp.setSelected(-1);
        }
        xCurrent = -1;
        yCurrent = -1;
    }

    /**
     * Select one block given his position in the Block[] array. Check if it's already selected
     * @param selected Position of the selected block in the array
     */
    public void select(int selected) {
        if (bImp.getSelectedPosition() == selected) {
            return;
        }
        if (bImp.getSelectedPosition() != -1) {
            blocks[getBoardImp().getSelectedPosition()].removeMouseClickedBlock();
        }
        blocks[selected].mouseClickedBlock();
        bImp.setSelected(selected);
        xCurrent = blocks[selected].getX();
        yCurrent = blocks[selected].getY();
    }

    /**
     * Called when there's a win, remove the main square and repaint
     */
    public void won() {
        this.remove(blocks[0]);
        revalidate();
        repaint();
    }

    /**
     * Select one block given his position in the Block[] array and the current {@link MouseEvent}. Check if it's already selected
     * It is useful when called by the drag and drop movement (pressed)
     * @param selected Position of the selected block in the array
     * @param e {@link MouseEvent} event
     */
    public void moveSelect(int selected, MouseEvent e) {
        xCurrent = e.getX();
        yCurrent = e.getY();
        if (bImp.getSelectedPosition() == selected) {
            return;
        }
        if (bImp.getSelectedPosition() != -1) {
            blocks[getBoardImp().getSelectedPosition()].removeMouseClickedBlock();
        }
        blocks[selected].mouseClickedBlock();
        bImp.setSelected(selected);
    }

    /**
     * Move the block calling {@link BoardImp#moveBlock(int)} function.
     * It is useful when called by the drag and drop movement (released)
     * @param e {@link MouseEvent} event
     * @return True if a block is moves, false otherwise
     */
    public boolean moveReleased(MouseEvent e) {
        xFuture = e.getX();
        yFuture = e.getY();
        if (xCurrent == xFuture && yCurrent == yFuture) {
            return false;
        }
        int direction = bImp.getDirection(xCurrent, yCurrent, xFuture, yFuture);
        return bImp.moveBlock(direction);
    }
}


