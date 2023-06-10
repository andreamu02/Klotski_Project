package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.coding.BoardImp;
import org.klotski.graphics.Board;

import static org.junit.jupiter.api.Assertions.*;

class BoardSelectControllerTest {
    @Test
    public void testBoardSelectController() {
        BoardSelectController boardSelectController = new BoardSelectController(new Board(new BoardImp()));
        boardSelectController.select(3);
        boardSelectController.deSelect();
    }

}