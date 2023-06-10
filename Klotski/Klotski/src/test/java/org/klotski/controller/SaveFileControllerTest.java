package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class SaveFileControllerTest {
    @Test
    public void testSaveFileController() {
        SaveFileController saveFileController = new SaveFileController(new Window("userTest"));
        saveFileController.save();
    }
}