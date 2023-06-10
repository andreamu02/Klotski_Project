package org.klotski.coding;

// IMPORT I/O CLASSES
import java.io.FileReader;
import java.io.IOException;

/**
 * <em>NextBestMove</em> gives the user the best possible move.
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class NextBestMove {
    /**
     * FileRead of solver.txt, to get the stored best move
     */
    private final FileRead fileRead;

    /**
     * Constructor that stores a fileRead to get the moves
     * @throws IllegalArgumentException If the file isn't in the right directory or non exists
     */
    public NextBestMove() {
        try {
            fileRead = new FileRead(new FileReader("src/main/resources/best_solver/solver.txt"));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the next Move from fileRead
     * @return the next move (position - direction), null if there is no stored move
     */
    public String nextMove() {
        if (fileRead.hasNextLine()) {
            return fileRead.getLine();
        }
        return null;
    }
}
