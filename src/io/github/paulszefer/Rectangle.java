package io.github.paulszefer;

import java.awt.Color;
import java.util.List;

/**
 * Defines a rectangle to be drawn.
 *
 * @author Paul Szefer
 * @version 1.0
 */
public class Rectangle {

    /** A collection of colours sorted in a rainbow gradient. */
    private static List<Color> colors = new Colors();

    /** The x coordinate of the rectangle. */
    private int x;

    /** The y coordinate of the rectangle. */
    private int y;

    /** The width of the rectangle. */
    private int width;

    /** The height of the rectangle. */
    private int height;

    /** The colour of the rectangle's fill. */
    private Color color;

    /**
     * Creates a rectangle.
     *
     * @param x
     *         the x position
     * @param y
     *         the y position
     * @param width
     *         the width of the rectangle
     * @param height
     *         the height of the rectangle
     */
    public Rectangle(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        final double maxHeight = SortPanel.HEIGHT * 0.95;
        final int colourOptions = 256 * 6;
        int score = (int) ((height + (int) (maxHeight / 2) - 1) * colourOptions / maxHeight);
        color = colors.get(score);
    }

    /**
     * Returns the x position.
     *
     * @return the x position
     */
    public int getX() {

        return x;
    }

    /**
     * Returns the y position.
     *
     * @return the y position
     */
    public int getY() {

        return y;
    }

    /**
     * Returns the width.
     *
     * @return the width
     */
    public int getWidth() {

        return width;
    }

    /**
     * Returns the height.
     *
     * @return the height
     */
    public int getHeight() {

        return height;
    }

    /**
     * Returns the color.
     *
     * @return the color
     */
    public Color getColor() {

        return color;
    }
}
