package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class MoveControllerTest {
    @Test
    public void testMoveController() {
        MoveController moveController = new MoveController(new Window("userTest"));
        moveController.move(3);
    }
}