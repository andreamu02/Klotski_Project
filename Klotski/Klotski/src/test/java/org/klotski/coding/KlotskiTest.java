package org.klotski.coding;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.event.WindowEvent;

import static org.junit.jupiter.api.Assertions.*;

class KlotskiTest {

    @Test
    public void testStart() {
        Klotski klotski = new Klotski();
        klotski.start();
        assertTrue(klotski.login.isVisible());
    }
}
