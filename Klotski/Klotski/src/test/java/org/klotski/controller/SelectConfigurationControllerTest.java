package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class SelectConfigurationControllerTest {
    @Test
    public void testSelectConfigurationController() {
        SelectConfigurationController selectConfigurationController = new SelectConfigurationController(new Window("userText"));
        selectConfigurationController.selectConfiguration(2);
    }
}