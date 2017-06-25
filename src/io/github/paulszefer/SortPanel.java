package io.github.paulszefer;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Defines a panel that shows an animation of the steps in various sorting methods.
 * <p>
 * The sorting animation can be modified by changing:
 * <p>
 * <ul>
 * <li>Sort Mode</li>
 * <li>Animation Mode</li>
 * <li>Random Set</li>
 * <li>Delay</li>
 * </ul>
 * <p>
 * <p>
 * Current available sorting methods:
 * </p>
 * <p>
 * <ul>
 * <li>Selection Sort</li>
 * <li>Insertion Sort</li>
 * </ul>
 * <p>
 * <p>
 * Current available animation modes:
 * </p>
 * <p>
 * <ul>
 * <li>Automatic</li>
 * <li>On mouse release</li>
 * </ul>
 *
 * @author Paul Szefer
 * @version 1.0
 */
public class SortPanel extends JPanel {

    /** The width of the panel. */
    public static final int WIDTH = 800;

    /** The height of the panel. */
    public static final int HEIGHT = 800;

    /** The foreground color of the panel. */
    public static final Color FOREGROUND_COLOR = Color.WHITE;

    /** The background color of the panel. */
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    /** The font of the panel text. */
    public static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    /** The scale that the default rectangles should be reduced by. */
    public static final double RECTANGLE_SCALE = 40.0;

    /** A default set of comparable objects to sort. */
    public static final Comparable[] DEFAULT_SET = {
            (int) (HEIGHT * 7 / RECTANGLE_SCALE),
            (int) (HEIGHT * 9 / RECTANGLE_SCALE),
            (int) (HEIGHT * 4 / RECTANGLE_SCALE),
            (int) (HEIGHT * -3 / RECTANGLE_SCALE),
            (int) (HEIGHT * -5 / RECTANGLE_SCALE),
            (int) (HEIGHT * 18 / RECTANGLE_SCALE),
            (int) (HEIGHT * -9 / RECTANGLE_SCALE),
            (int) (HEIGHT * 3 / RECTANGLE_SCALE),
            (int) (HEIGHT * 1 / RECTANGLE_SCALE),
            (int) (HEIGHT * -6 / RECTANGLE_SCALE),
            (int) (HEIGHT * 12 / RECTANGLE_SCALE),
            (int) (HEIGHT * -11 / RECTANGLE_SCALE),
            (int) (HEIGHT * -1 / RECTANGLE_SCALE),
            (int) (HEIGHT * -17 / RECTANGLE_SCALE),
            (int) (HEIGHT * 5 / RECTANGLE_SCALE),
            (int) (HEIGHT * 10 / RECTANGLE_SCALE) };

    /**
     * The sorting mode.
     * <p>
     * <ul>
     * <li>0: Selection sort</li>
     * <li>1: Insertion sort</li>
     * </ul>
     */
    public static final int SORT_MODE = 0;

    /**
     * The animation mode.
     * <p>
     * <ul>
     * <li>0: Automatic</li>
     * <li>1: On mouse release</li>
     * </ul>
     */
    public static final int ANIMATION_MODE = 0;

    /**
     * Specifies whether to generate a random set and if so, what size to generate.
     * <p>
     * <ul>
     * <li>0: Not random (use the default set)</li>
     * <li>1+: Generate a random set with the given number of integers</li>
     * </ul>
     */
    public static final int RANDOM_SET = 128;

    /** The animation delay for automatic animation mode. */
    public static final int DELAY = 10;

    /** The Serial Version UID. */
    private static final long serialVersionUID = 84679360620668509L;

    /** A random number generator. */
    private static Random generator = new Random();

    /** The set of Comparable object to store. */
    private static Comparable[] set;

    /** The set of rectangles to be drawn. */
    private static Rectangle[] rectangles;

    /** A timer to handle the animation. */
    private static Timer timer;

    /** The current frame of the animation. */
    private int animationFrame;

    /** Creates a sorting panel and sets up listeners corresponding to the animation mode. */
    public SortPanel() {

        if (RANDOM_SET < 0) {
            throw new IllegalStateException("The random set size must be positive.");
        } else if (RANDOM_SET == 0) {
            set = DEFAULT_SET;
        } else {
            set = new Comparable[RANDOM_SET];
            final int maxInt = (int) (HEIGHT * 0.90);
            final int maxBound = (int) (HEIGHT * 0.45);
            for (int i = 0; i < set.length; i++) {
                set[i] = generator.nextInt(maxInt) - maxBound - 1;
            }
        }

        rectangles = new Rectangle[set.length];

        for (int i = 0; i < set.length; i++) {
            rectangles[i] = new Rectangle(0, HEIGHT / 2, WIDTH / set.length, (int) set[i]);
        }

        switch (ANIMATION_MODE) {
            case 0:
                timer = new Timer(DELAY, new SortTimer());
                timer.start();
                break;
            case 1:
                addMouseListener(new ClickListener());
                break;
            default:
                throw new IllegalStateException("Illegal animation mode.");
        }

        switch (SORT_MODE) {
            case 0:
                animationFrame = 0;
                break;
            case 1:
                animationFrame = 1;
                break;
            default:
                throw new IllegalStateException("Illegal sorting mode.");
        }

        int sum = 0;
        for (Rectangle r : rectangles) {
            sum += r.getWidth();
        }
        setPreferredSize(new Dimension(sum, HEIGHT));
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Advances the Selection Sort by one step.
     *
     * @param i
     *         the current sorting index
     */
    private static void stepSelectionSort(int i) {

        int min = i;

        for (int j = i + 1; j < set.length; j++) {
            if (set[j].compareTo(set[min]) < 0) {
                min = j;
            }
        }

        Comparable<Comparable> comp;
        Rectangle rect;

        comp = set[min];
        set[min] = set[i];
        set[i] = comp;

        rect = rectangles[min];
        rectangles[min] = rectangles[i];
        rectangles[i] = rect;
    }

    /**
     * Advances the Insertion Sort by one step.
     *
     * @param i
     *         the current sorting index
     */
    private static void stepInsertionSort(int i) {

        Comparable<Comparable<Comparable>> comp = set[i];
        Rectangle rect = rectangles[i];
        int position = i;

        while (position > 0 && comp.compareTo(set[position - 1]) < 0) {
            set[position] = set[position - 1];
            rectangles[position] = rectangles[position - 1];
            position--;
        }
        set[position] = comp;
        rectangles[position] = rect;
    }

    /** {@inheritDoc} */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        int xPosition = 0;

        final int mediumSize = 32;
        final int smallSize = 16;
        final int textXShift = 5;

        for (Rectangle r : rectangles) {

            g.setColor(r.getColor());

            if (r.getHeight() > 0) {

                g.fillRect(r.getX() + xPosition, HEIGHT / 2 - r.getHeight(), r.getWidth(),
                           r.getHeight());

                g.setColor(FOREGROUND_COLOR);

                if (RANDOM_SET <= mediumSize) {
                    g.drawRect(r.getX() + xPosition, HEIGHT / 2 - r.getHeight(), r.getWidth(),
                               r.getHeight());
                }

                if (RANDOM_SET <= smallSize) {
                    g.setFont(TEXT_FONT);

                    final int positiveValueRectangleShiftY = -20;
                    g.drawString("" + r.getHeight(), r.getX() + xPosition + textXShift,
                                 r.getY() - r.getHeight() + positiveValueRectangleShiftY);
                }
            } else if (r.getHeight() < 0) {

                g.fillRect(r.getX() + xPosition, r.getY(), r.getWidth(), -r.getHeight());

                g.setColor(FOREGROUND_COLOR);

                if (RANDOM_SET <= mediumSize) {
                    g.drawRect(r.getX() + xPosition, r.getY(), r.getWidth(), -r.getHeight());
                }

                if (RANDOM_SET <= smallSize) {
                    g.setFont(TEXT_FONT);

                    final int negativeValueRectangleShiftY = 40;
                    g.drawString("" + r.getHeight(), r.getX() + xPosition + textXShift,
                                 r.getY() - r.getHeight() + negativeValueRectangleShiftY);
                }
            }

            xPosition += r.getWidth();
        }

        g.setColor(Color.BLACK);

        final int lineHeight = 1;
        g.fillRect(0, HEIGHT / 2, WIDTH, lineHeight);
    }

    /** Advances the animation by one frame according to the sorting mode. */
    private void advanceSort() {

        switch (SORT_MODE) {
            case 0:
                stepSelectionSort(animationFrame);
                break;
            case 1:
                stepInsertionSort(animationFrame);
                break;
            default:
                throw new IllegalStateException("Illegal sorting mode.");
        }
    }

    /**
     * Advances the animation after each timer "tick".
     *
     * @author Paul Szefer
     * @version 1.0
     */
    private class SortTimer implements ActionListener {

        /** {@inheritDoc} */
        @Override
        public void actionPerformed(ActionEvent e) {

            // advance according to sorting mode
            advanceSort();

            if (animationFrame <= set.length - 2) {
                animationFrame++;
            } else {
                timer.stop();
            }

            repaint();
        }
    }

    /**
     * Advances the animation upon detecting the mouse button being released.
     *
     * @author Paul Szefer
     * @version 1.0
     */
    private class ClickListener extends MouseAdapter {

        /** {@inheritDoc} */
        @Override
        public void mouseReleased(MouseEvent e) {

            // advance according to sorting mode
            advanceSort();

            if (animationFrame <= set.length - 2) {
                animationFrame++;
            }

            repaint();
        }
    }
}