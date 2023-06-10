package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class IncrementsMoveControllerTest {
    @Test
    public void testIncrementsMoveController() {
        IncrementsMoveController incrementsMoveController = new IncrementsMoveController(new Window("userTest"), new HighScoreUpdater());
    }
}
