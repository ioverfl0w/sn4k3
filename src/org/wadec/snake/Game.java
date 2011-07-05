package org.wadec.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * used to keep stats, score, etc
 * @author Wade C
 */
public class Game {

    private int[] gframe, bounds;
    private boolean active = false, dead = false;
    private int score = 0;

    public Game(int frameX, int frameY) {
        gframe = new int[]{14, 14, frameX - 40, frameY - 100};
        bounds = new int[]{gframe[0] - 5, gframe[1] - 5, gframe[2] + 11, gframe[3] + 1};
    }

    public void addPoint() {
        score += 100;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean bool) {
        active = bool;
        dead = !bool;
    }

    public void update(Snake s) {
        int[] c = s.getCurrentLocation();
        int x = c[0], y = c[1];

        //check if collide with bounds
        if (x < bounds[0] || x > bounds[2] || y < bounds[1] || y > bounds[3]) {
            kill(s);
            return;
        }

        //check if collide with tail
        for (int[] z : s.getTailPlot()) {
            if (z[0] == -1)
                break;
            if (z[0] == c[0] && z[1] == c[1]) {
                kill(s);
                return;
            }
        }
    }

    public void kill(Snake s) {
        s.clearGrid();
        setActive(false);
    }

    public void draw(Graphics g) {
        //declare graphics settings
        g.setColor(Color.BLACK);
        g.setFont(new Font("Sans Serif", Font.PLAIN, 14));

        //if inactive, show menu
        if (!isActive()) {
            g.drawString("Press 's' to start", 150, 150);

            //if died
            if (dead) {
                g.setColor(Color.RED);
                g.setFont(new Font("Sans Serif", Font.BOLD, 36));
                g.drawString("OH SHIT, YOU DIED", 50, 250);
            }
            return;
        }

        //if active, print game info
        g.drawRect(gframe[0], gframe[1], gframe[2], gframe[3]);//game frame
        g.drawString("Score: " + score + " €", 25, gframe[3] + 45);//score board
    }
}
