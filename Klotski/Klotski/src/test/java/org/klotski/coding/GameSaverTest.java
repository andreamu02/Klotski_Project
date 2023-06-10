package org.klotski.coding;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameSaverTest {
    @Test
    public void testGameSaver() {
        GameSaver gameSaver = new GameSaver("USERTEST", 2);
        assertEquals("2", gameSaver.getText());
    }

    @Test
    public void testAddLine() {
        GameSaver gameSaver = new GameSaver("USERTEST", 2);
        gameSaver.addLine(4, 2);
        assertEquals("2\n4 2", gameSaver.getText());
    }

    @Test
    public void testRemoveLine() {
        GameSaver gameSaver = new GameSaver("USERTEST", 2);
        gameSaver.addLine(4, 2);
        gameSaver.removeLine();
        assertEquals("2", gameSaver.getText());
    }

    @Test
    public void testClose() {
        GameSaver gameSaver = new GameSaver("USERTEST", 2);
        gameSaver.addLine(4, 2);
        gameSaver.close(123, 23, false);
        assertEquals("2\n4 2\n*\n123 23", gameSaver.getText());
    }

}