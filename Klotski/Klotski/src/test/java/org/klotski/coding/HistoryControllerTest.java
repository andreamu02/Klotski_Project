package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.coding.BoardImp;
import org.klotski.coding.HighScoreUpdater;
import org.klotski.graphics.Board;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class HistoryControllerTest {
    @Test
    public void testHistoryControllerTest() {
        HistoryController  historyController = new HistoryController(new Window("userTest"), new HighScoreUpdater(), new Board(new BoardImp()));
    }

}
