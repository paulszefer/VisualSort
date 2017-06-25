package io.github.paulszefer;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Defines a list of colours sorted in a rainbow gradient.
 * <p>
 * This is intended to be used to associate colours with randomly selected integers within a range.
 *
 * @author Paul Szefer
 * @version 1.0
 */
public class Colors extends ArrayList<Color> {

    /** The Serial Version UID. */
    private static final long serialVersionUID = -7632643197105736605L;

    /** Creates a list of colours sorted in a rainbow gradient. */
    public Colors() {

        final int colourMax = 255;
        final double colourRange = 256.0;
        double magnitude;

        // red -> yellow
        for (int green = 0; green <= colourMax; green++) {
            magnitude = green * colourMax / colourRange;
            add(new Color(colourMax, (int) magnitude, 0));
        }

        // yellow -> green
        for (int red = colourMax; red >= 0; red--) {
            magnitude = red * colourMax / colourRange;
            add(new Color((int) magnitude, colourMax, 0));
        }

        for (int blue = 0; blue <= colourMax; blue++) {
            magnitude = blue * colourMax / colourRange;
            add(new Color(0, colourMax, (int) magnitude));
        }

        for (int green = colourMax; green >= 0; green--) {
            magnitude = green * colourMax / colourRange;
            add(new Color(0, (int) magnitude, colourMax));
        }

        for (int red = 0; red <= colourMax; red++) {
            magnitude = red * colourMax / colourRange;
            add(new Color((int) magnitude, 0, colourMax));
        }

        for (int blue = colourMax; blue >= 0; blue--) {
            magnitude = blue * colourMax / colourRange;
            add(new Color(colourMax, 0, (int) magnitude));
        }
    }
}
