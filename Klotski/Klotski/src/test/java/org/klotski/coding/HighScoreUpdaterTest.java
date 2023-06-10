package org.klotski.coding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreUpdaterTest {
    @Test
    public void testHighScoreUpdater() {
        HighScoreUpdater highScoreUpdater = new HighScoreUpdater();
        assertTrue(highScoreUpdater.hasUsers());
    }

    @Test
    public void testAddUserScore() {
        HighScoreUpdater highScoreUpdater = new HighScoreUpdater();
        highScoreUpdater.addUserScore("userTest", 300);
        assertEquals(300, highScoreUpdater.getScore("userTest"));
    }

    @Test
    public void testUpdateScore() {
        HighScoreUpdater highScoreUpdater = new HighScoreUpdater();
        highScoreUpdater.addUserScore("userTest", 300);
        highScoreUpdater.updateScore("userTest", 325);
        assertEquals(325, highScoreUpdater.getScore("userTest"));
        assertThrowsExactly(IllegalArgumentException.class, () -> highScoreUpdater.updateScore("userNonExistent", 400));
    }
}