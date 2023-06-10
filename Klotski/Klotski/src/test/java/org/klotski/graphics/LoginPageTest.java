package org.klotski.graphics;

import org.junit.jupiter.api.Test;
import org.klotski.coding.UsernameCollector;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class LoginPageTest {

    @Test
    public void testLoginPage() {
        LoginPage loginPage = new LoginPage(new UsernameCollector());
        assertTrue(loginPage.hasChanged());
        assertTrue(loginPage.isFirst());
        assertFalse(loginPage.isExiting());
        assertEquals(350, loginPage.getWidth());
    }

    @Test
    public void testReactivate() {
        LoginPage loginPage = new LoginPage(new UsernameCollector());
        loginPage.reactivate();
        assertFalse(loginPage.isFirst());
        assertEquals(350, loginPage.getWidth());
    }


}