package org.klotski.coding;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Block;

import static org.junit.jupiter.api.Assertions.*;

class BoardImpTest {

    @Test
    public void testBoardImp() {
        BoardImp boardImp = new BoardImp(3);
        assertEquals(3, boardImp.getConfiguration());
    }

    @Test
    public void testSetConfiguration() {
        BoardImp boardImp = new BoardImp();
        boardImp.setConfiguration(2);
        assertEquals(2, boardImp.getConfiguration());
        assertThrowsExactly(IllegalArgumentException.class, () -> boardImp.setConfiguration(6));
        assertThrowsExactly(IllegalArgumentException.class, () -> boardImp.setConfiguration(0));
    }

    @Test
    public void testGetConfiguration() {
        BoardImp boardImp = new BoardImp();
        assertEquals(1, boardImp.getConfiguration());
        boardImp.setConfiguration(2);
        assertEquals(2, boardImp.getConfiguration());
        boardImp.setConfiguration(3);
        assertEquals(3, boardImp.getConfiguration());
        boardImp.setConfiguration(4);
        assertEquals(4, boardImp.getConfiguration());
    }

    @Test
    public void testSetSelected() {
        BoardImp boardImp = new BoardImp();
        assertEquals(-1, boardImp.getSelectedPosition());
        boardImp.setSelected(0);
        assertEquals(0, boardImp.getSelectedPosition());
        boardImp.setSelected(-1);
        assertEquals(-1, boardImp.getSelectedPosition());
    }

    @Test
    public void testGetLastDirection() {
        BoardImp boardImp = new BoardImp();
        assertEquals(-1, boardImp.getLastDirection());
    }

    @Test
    public void testMoveBlock() {
        BoardImp boardImp = new BoardImp();
        assertFalse(boardImp.moveBlock(1));
        boardImp.setSelected(0);
        assertThrowsExactly(IllegalArgumentException.class, () -> boardImp.moveBlock(7));
        assertThrowsExactly(IllegalArgumentException.class, () -> boardImp.moveBlock(-2));
    }

    @Test
    public void testMoveBlockUp() {
        BoardImp boardImp = new BoardImp(4);
        boardImp.setSelected(4);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(5);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(3);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(9);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(0);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(1);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(3);
        boardImp.moveBlock(1);
        boardImp.setSelected(4);
        boardImp.moveBlock(3);
        assertFalse(boardImp.moveBlock(0));
        boardImp.moveBlock(2);
        boardImp.setSelected(3);
        assertTrue(boardImp.moveBlock(0));
        boardImp.setSelected(9);
        boardImp.moveBlock(3);
        boardImp.setSelected(8);
        boardImp.moveBlock(1);
        assertTrue(boardImp.moveBlock(0));
        boardImp.moveBlock(1);
        boardImp.moveBlock(2);
        boardImp.setSelected(9);
        boardImp.moveBlock(2);
        assertFalse(boardImp.moveBlock(0));
        boardImp.moveBlock(3);
        boardImp.setSelected(8);
        boardImp.moveBlock(3);
        boardImp.moveBlock(0);
        boardImp.setSelected(9);
        boardImp.moveBlock(2);
        boardImp.moveBlock(2);
        boardImp.setSelected(7);
        boardImp.moveBlock(1);
        boardImp.moveBlock(3);
        boardImp.setSelected(9);
        boardImp.moveBlock(3);
        assertFalse(boardImp.moveBlock(0));
        boardImp.setSelected(0);
        assertFalse(boardImp.moveBlock(0));
    }

    @Test
    public void testMoveBlockDown() {
        BoardImp boardImp = new BoardImp(4);
        boardImp.setSelected(6);
        assertTrue(boardImp.moveBlock(1));
        boardImp.setSelected(5);
        assertFalse(boardImp.moveBlock(1));
        boardImp.moveBlock(2);
        boardImp.setSelected(0);
        assertFalse(boardImp.moveBlock(1));
        boardImp.setSelected(3);
        boardImp.moveBlock(1);
        boardImp.setSelected(4);
        boardImp.moveBlock(3);
        boardImp.setSelected(5);
        boardImp.moveBlock(3);
        boardImp.setSelected(0);
        assertFalse(boardImp.moveBlock(1));
        boardImp.setSelected(5);
        boardImp.moveBlock(2);
        boardImp.setSelected(0);
        assertTrue(boardImp.moveBlock(1));
        boardImp.setSelected(7);
        assertFalse(boardImp.moveBlock(1));
        boardImp.setSelected(8);
        assertFalse(boardImp.moveBlock(1));
        boardImp.setSelected(9);
        assertFalse(boardImp.moveBlock(1));
    }

    @Test
    public void testMoveBlockRight() {
        BoardImp boardImp = new BoardImp(4);
        boardImp.setSelected(9);
        boardImp.moveBlock(3);
        boardImp.setSelected(3);
        assertFalse(boardImp.moveBlock(2));
        boardImp.setSelected(5);
        assertFalse(boardImp.moveBlock(2));
        boardImp.setSelected(8);
        assertFalse(boardImp.moveBlock(2));
        boardImp.moveBlock(1);
        assertTrue(boardImp.moveBlock(2));
        boardImp.moveBlock(3);
        boardImp.moveBlock(0);
        boardImp.setSelected(6);
        boardImp.moveBlock(1);
        boardImp.setSelected(9);
        assertTrue(boardImp.moveBlock(2));
        assertFalse(boardImp.moveBlock(2));
        boardImp.setSelected(2);
        boardImp.moveBlock(1);
        assertFalse(boardImp.moveBlock(2));
        boardImp.setSelected(0);
        assertFalse(boardImp.moveBlock(2));
        boardImp.setSelected(4);
        assertFalse(boardImp.moveBlock(2));
    }

    @Test
    public void testMoveBlockLeft() {
        BoardImp boardImp = new BoardImp(4);
        boardImp.setSelected(9);
        assertTrue(boardImp.moveBlock(3));
        assertFalse(boardImp.moveBlock(3));
        boardImp.moveBlock(2);
        boardImp.setSelected(3);
        boardImp.moveBlock(1);
        boardImp.setSelected(6);
        boardImp.moveBlock(1);
        boardImp.setSelected(4);
        assertTrue(boardImp.moveBlock(3));
        boardImp.setSelected(5);
        boardImp.moveBlock(2);
        boardImp.setSelected(0);
        boardImp.moveBlock(1);
        boardImp.setSelected(2);
        assertFalse(boardImp.moveBlock(3));
        boardImp.setSelected(7);
        assertFalse(boardImp.moveBlock(3));
        boardImp.setSelected(9);
        assertFalse(boardImp.moveBlock(3));
        boardImp.setSelected(0);
        assertTrue(boardImp.moveBlock(0));
    }

    @Test
    public void testUndo() {
        BoardImp boardImp = new BoardImp(4);
        boardImp.setSelected(9);
        boardImp.moveBlock(2);
        boardImp.undo();
        assertEquals(105, boardImp.getBlocks()[9].getX());
        boardImp.moveBlock(3);
        boardImp.undo();
        assertEquals(105, boardImp.getBlocks()[9].getX());
        boardImp.setSelected(6);
        boardImp.moveBlock(1);
        boardImp.undo();
        assertEquals(210, boardImp.getBlocks()[6].getY());
        boardImp.moveBlock(1);
        boardImp.moveBlock(0);
        boardImp.undo();
        assertEquals(210, boardImp.getBlocks()[6].getY());
    }

}
