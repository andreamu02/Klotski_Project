package org.klotski.coding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameCollectorTest {
    @Test
    public void testUsernameCollector() {
        UsernameCollector usernameCollector = new UsernameCollector();
        assertTrue(usernameCollector.hasUsers());
    }

    @Test
    public void testAddUser() {
        UsernameCollector usernameCollector = new UsernameCollector();
        usernameCollector.addUser("USERTEST");
        usernameCollector.setSelectedUser("USERTEST");
        assertEquals("USERTEST", usernameCollector.getSelectedUser());
    }
}