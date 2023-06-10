package org.klotski.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuitControllerTest {
    @Test
    public void testQuit() {
        QuitController quitController = new QuitController();
        assertTrue(quitController.quit());
        assertFalse(quitController.quit());
    }

}