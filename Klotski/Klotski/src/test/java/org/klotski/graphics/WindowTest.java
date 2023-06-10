package org.klotski.graphics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindowTest {
    @Test
    public void testWindow() {
        Window window = new Window("userTest");
        assertFalse(window.hasChangedUser());
    }

    @Test
    public void testMovesCounter() {
        Window window = new Window("userTest");
        assertEquals(0, window.getMovesCounter());
        window.incrementsMovesCounter(1, 1);
        assertEquals(1, window.getMovesCounter());
        window.decrementsMovesCounter();
        assertEquals(0, window.getMovesCounter());
    }
}