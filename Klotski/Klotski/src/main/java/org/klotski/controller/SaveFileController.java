package org.klotski.controller;

// IMPORT CODING AND GRAPHICS USEFUL CLASSES
import org.klotski.graphics.Window;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * <em>SaveFileController</em> is a controller class that is used to change where to store current game log.<br>
 * It dialogues with {@link Window}<br>
 * It is useful if it's created to use function inline from {@link Window}
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class SaveFileController {
    /**
     * The used Window
     */
    private final Window window;

    /**
     * This constructor saved data from {@link Window}
     * @param window {@link Window} object from which obtain variables
     */
    public SaveFileController(Window window) {
        this.window = window;
    }

    /**
     * Function to select where to store data. It is preferable to call this function inline, for example:
     * <blockquote><pre>{@code
     *      new SaveFileController(this).save();
     * }</pre></blockquote>
     * @see JFileChooser
     * @see Window
     */
    public void save() {
        // CREATE FILE CHOOSER THAT ACCEPTS ONLY .TXT FILES
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("Klotski/Klotski/src//main//resources//users_history"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Change the default saver");
        fileChooser.setApproveButtonText("Save");
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        fileChooser.addChoosableFileFilter(restrict);
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (new File(path).isFile()) {
                String[] options = new String[]{"Yes", "Cancel"};   // CONFIRMING DIALOGUE
                int choice = JOptionPane.showOptionDialog(null, "This file will be overwritten.\nAre you sure you want to continue?", "Exit Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
                if (choice == JOptionPane.YES_OPTION) {
                    window.newSaver(path);
                } else {
                    save();
                }
            }
        }
    }
}