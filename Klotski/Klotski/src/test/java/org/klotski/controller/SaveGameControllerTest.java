package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class SaveGameControllerTest {
    @Test
    public void testSaveGameController() {
        SaveGameController saveGameController = new SaveGameController(new Window("userTest"));
        saveGameController.saveGame();
    }

}