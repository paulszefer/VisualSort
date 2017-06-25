package io.github.paulszefer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Displays the sorting of the set of Comparables using the specified sorting method as an
 * animation.
 *
 * @author Paul Szefer
 * @version 1.0
 */
public class VisualSort {

    /**
     * Creates and sets up the GUI frame.
     */
    public VisualSort() {

        JFrame frame = new JFrame("Visual Sort");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new SortPanel());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Drives the program.
     *
     * @param args
     *         unused
     */
    public static void main(String[] args) {

        new VisualSort();
    }

}
