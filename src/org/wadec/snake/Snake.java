package org.wadec.snake;

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
        System.out.printf("d:%s\th:%s\n", d, h);

        //generates new heading
        System.out.println("d-h=" + (d-h));
        if (d - h == 0 ) {
        }
    }

    @Override
    public void paint(Graphics g) {
        //draw main box
        g.fillRect(currLocation[0] - 5, currLocation[1] + 5, 10, 10);

        //draw tail
        for (int i = 0; i < tailPlot.length; i++) {
            if (tailPlot[i][0] == -1) {
                break;
            }
            g.fillRect(tailPlot[i][0] - 1, tailPlot[i][1] + 1, 2, 2);
        }
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(200);
                updatePlot();
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
    public static final int NORTH = 0;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int WEST = 270;
    public static final int LEFT_ARROW = 37;
    public static final int UP_ARROW = 38;
    public static final int RIGHT_ARROW = 39;
    public static final int DOWN_ARROW = 40;
}
