package org.wadec.snake;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Wade C
 */
public class Snake extends Component implements KeyListener, Runnable {

    private int currentDirection = NORTH;
    private int currentHeading = currentDirection;
    private int[] currLocation;
    private int[][] tailPlot = new int[25][2];

    public Snake(int startX, int startY) {
        currLocation = new int[]{startX, startY};
        for (int i = 0; i < tailPlot.length; i++) {
            tailPlot[i] = new int[]{-1, 0};
        }
    }

    public void changeDirection(int direction) {
        currentDirection = direction;
    }

    public void updatePlot() {
        int d = currentDirection, h = currentHeading;
        //System.out.printf("d:%s\th:%s\n", d, h);

        //gets new header
        if (isAcceptable(d,h))
            h = d;

        //generates new head plot
        int prevPlot[] = currLocation;
        switch (h) {
            case NORTH:
                currLocation = new int[] {prevPlot[0], prevPlot[1] - MOVE_RATE};
                break;
            case SOUTH:
                currLocation = new int[] {prevPlot[0], prevPlot[1] + MOVE_RATE};
                break;
            case EAST:
                currLocation = new int[] {prevPlot[0] + MOVE_RATE, prevPlot[1]};
                break;
            case WEST:
                currLocation = new int[] {prevPlot[0] - MOVE_RATE, prevPlot[1]};
                break;
        }

        //makes tail plot
        int[][] tail = new int[tailPlot.length][2];
        tail[0] = prevPlot;
        for (int i = 1; i < tail.length; i++)
            tail[i] = tailPlot[i - 1];

        //declare new values
        currentHeading = h;
        tailPlot = tail;

        //redraw frame
        repaint();
    }

    private boolean isAcceptable(int d, int h) {
        return (h == WEST && d == NORTH) || (h == NORTH && d == WEST) || (h - 90 == d || h + 90 == d);
    }

    @Override
    public void paint(Graphics g) {
        //draw tail
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < tailPlot.length; i++) {
            if (tailPlot[i][0] == -1) {
                break;
            }
            g.fillRect(tailPlot[i][0] - 5, tailPlot[i][1] + 5, 10, 10);
        }

        //draw main box
        g.setColor(Color.BLACK);
        g.fillRect(currLocation[0] - 5, currLocation[1] + 5, 10, 10);
    }

    public void run() {
        try {
            while (true) {
                long s = System.currentTimeMillis();

                //update coord grid
                updatePlot();

                Thread.sleep(100 - (System.currentTimeMillis() - s));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case UP_ARROW:
                changeDirection(NORTH);
                return;
            case DOWN_ARROW:
                changeDirection(SOUTH);
                return;
            case LEFT_ARROW:
                changeDirection(WEST);
                return;
            case RIGHT_ARROW:
                changeDirection(EAST);
                return;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
    public static final int MOVE_RATE = 10;
    public static final int NORTH = 0;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int WEST = 270;
    public static final int LEFT_ARROW = 37;
    public static final int UP_ARROW = 38;
    public static final int RIGHT_ARROW = 39;
    public static final int DOWN_ARROW = 40;
}
