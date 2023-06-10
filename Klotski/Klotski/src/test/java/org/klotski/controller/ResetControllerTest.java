package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class ResetControllerTest {
    @Test
    public void testResetController() {
        ResetController resetController = new ResetController(new Window("userTest"));
        resetController.reset();
    }

}