package org.klotski.graphics;

import org.junit.jupiter.api.Test;
import org.klotski.coding.BoardImp;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void testBoard() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new Board(null));
        assertDoesNotThrow(() -> new Board(new BoardImp()));
    }

}