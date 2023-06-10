package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class InfoControllerTest {
    @Test
    public void testInfoController() {
        InfoController infoController = new InfoController(new Window("userTest"));
        infoController.info();
    }
}