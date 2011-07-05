package org.wadec.snake;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Wade C
 */
public class Snake extends Component implements KeyListener {

    private int currentDirection = NORTH;
    private int currentHeading = currentDirection;
    private int[] currLocation;
    private int[][] tailPlot = new int[25][2];

    public Snake(int startX, int startY) {
        currLocation = new int[]{startX, startY};
        for (int i = 0; i < tailPlot.length; i++) {
            tailPlot[i] = new int[]{-1, -1};
        }
    }

    public void changeDirection(int direction) {
        currentDirection = direction;
    }

    public void updatePlot() {
        int d = currentDirection, h = currentHeading;
        //generates new heading
        if (d - h == 0) {
        }
    }

    @Override
    public void paint(Graphics g) {
        //draw main box
        g.fillRect(currLocation[0] - 5, currLocation[1] + 5, 10, 10);

        //draw tail
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("key typed: " + e.getKeyCode());
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed: " + e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("key released: " + e.getKeyCode());
    }
    public static final int NORTH = 0;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int WEST = 270;
}
