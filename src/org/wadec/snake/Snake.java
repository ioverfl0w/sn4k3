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

    private Game game;
    private int currentDirection = NORTH;
    private int currentHeading = currentDirection;
    private int[] currLocation;
    private int[][] tailPlot = new int[TAIL_PLOT_LEN][2];
    private final int[] start;

    public Snake(int startX, int startY, Game g) {
        currLocation = new int[]{startX, startY};
        for (int i = 0; i < tailPlot.length; i++) {
            tailPlot[i] = new int[]{-1, 0};
        }
        game = g;
        start = currLocation;
    }

    public void clearGrid() {
        currentDirection = NORTH;
        currentHeading = currentDirection;
        currLocation = start;
        tailPlot = new int[TAIL_PLOT_LEN][2];
    }

    public void changeDirection(int direction) {
        currentDirection = direction;
    }

    public void updatePlot() {
        //gets new header
        if (isAcceptable(currentDirection, currentHeading)) {
            currentHeading = currentDirection;
        }

        //generates new head plot
        int prevPlot[] = currLocation;
        switch (currentHeading) {
            case NORTH:
                currLocation = new int[]{prevPlot[0], prevPlot[1] - MOVE_RATE};
                break;
            case SOUTH:
                currLocation = new int[]{prevPlot[0], prevPlot[1] + MOVE_RATE};
                break;
            case EAST:
                currLocation = new int[]{prevPlot[0] + MOVE_RATE, prevPlot[1]};
                break;
            case WEST:
                currLocation = new int[]{prevPlot[0] - MOVE_RATE, prevPlot[1]};
                break;
        }

        //creates new tail plot
        int[][] tail = new int[tailPlot.length][2];
        tail[0] = prevPlot;
        for (int i = 1; i < tail.length; i++) {
            tail[i] = tailPlot[i - 1];
        }
        tailPlot = tail;

        //redraw frame
        repaint();
    }

    private boolean isAcceptable(int d, int h) {
        return (h == WEST && d == NORTH) || (h == NORTH && d == WEST) || (h - 90 == d || h + 90 == d);
    }

    @Override
    public void paint(Graphics g) {

        //checks for active game session
        if (game.isActive()) {
            //draw tail
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < tailPlot.length; i++) {
                if (tailPlot[i][0] == -1) {
                    break;
                }
                if (i > (tailPlot.length / 2)) {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(tailPlot[i][0] - 5, tailPlot[i][1] + 5, 10, 10);
            }

            //draw main box
            g.setColor(Color.RED);
            g.fillRect(currLocation[0] - 5, currLocation[1] + 5, 10, 10);
        }

        //draw game frame, score, items, etc
        game.draw(g);
    }

    public void run() {
        try {
            while (true) {
                long s = System.currentTimeMillis();

                //update coord grid
                if (game.isActive()) {
                    updatePlot();
                    game.update(this);
                }

                Thread.sleep(100 - (System.currentTimeMillis() - s));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int[] getCurrentLocation() {
        return currLocation;
    }

    public int[][] getTailPlot() {
        return tailPlot;
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
            case 83:// S
                if (!game.isActive()) {
                    game.setActive(true);
                    System.out.println("game now active");
                }
                return;
        }
        //System.out.println("unhandled char: " + e.getKeyChar() + "\t" + e.getKeyCode());
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
    public static final int MOVE_RATE = 10;
    public static final int TAIL_PLOT_LEN = 15;
    public static final int NORTH = 0;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int WEST = 270;
    public static final int LEFT_ARROW = 37;
    public static final int UP_ARROW = 38;
    public static final int RIGHT_ARROW = 39;
    public static final int DOWN_ARROW = 40;
}
