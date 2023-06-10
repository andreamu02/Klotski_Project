package org.klotski.controller;

import org.junit.jupiter.api.Test;
import org.klotski.coding.NextBestMove;
import org.klotski.graphics.Window;

import static org.junit.jupiter.api.Assertions.*;

class BestMoveControllerTest {
    @Test
    public void testBestMoveController() {
        BestMoveController bestMoveController = new BestMoveController(new Window("userTest"), new NextBestMove());
        bestMoveController.nextBestMove();
    }
}