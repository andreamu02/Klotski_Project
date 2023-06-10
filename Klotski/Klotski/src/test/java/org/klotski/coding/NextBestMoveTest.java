package org.klotski.coding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextBestMoveTest {
    @Test
    public void testNextMove() {
        NextBestMove nextBestMove = new NextBestMove();
        assertEquals("7 2", nextBestMove.nextMove());
    }
}