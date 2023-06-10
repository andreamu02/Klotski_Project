package org.klotski.coding;

/**
 * Main class to play klotski game. It calls the {@link Klotski#start()} method
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class Main {
    /**
     * The runnable function to play the game. Instantiates a klotski object and starts it
     * @param args Are not used so they are unnecessary
     */
    public static void main(String[] args) {
        Klotski klotski = new Klotski();
        klotski.start();
    }
}