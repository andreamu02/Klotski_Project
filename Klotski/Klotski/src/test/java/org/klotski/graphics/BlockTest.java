package org.klotski.graphics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    @Test
    public void testBlock() {
        assertThrows(IllegalArgumentException.class, () -> new Block(2, 9, 600));
        assertThrows(IllegalArgumentException.class, () -> new Block(2, -4, 30));
        assertDoesNotThrow(() -> new Block(2, 5, 5));
        assertDoesNotThrow(() -> new Block(9, 5, 5));
    }

    @Test
    public void testMove() {
        Block block = new Block(1, 0, 0);
        block.move(1);
        assertEquals(105, block.getY());
        assertEquals(0, block.getX());
        block.move(2);
        assertEquals(105, block.getY());
        assertEquals(105, block.getX());
        block.move(0);
        assertEquals(0, block.getY());
        assertEquals(105, block.getX());
        block.move(3);
        assertEquals(0, block.getY());
        assertEquals(0, block.getX());
    }
}