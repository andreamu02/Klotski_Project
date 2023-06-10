package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class UndoControllerTest {
    @Test
    public void testUndoController() {
        UndoController undoController = new UndoController(new Window("userText"));
        undoController.undo();
    }

}