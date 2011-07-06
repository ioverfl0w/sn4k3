package org.wadec.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * used to generate game options, keep stats, score, etc
 * @author Wade C
 */
public class Game {

    public static final Random rand = new Random();
    private int[] gframe, bounds, currObj = new int[]{-1, 0};
    private boolean active = false, dead = false;
    private int score = 0, old = 0, prevLen = 0, difficulty = 3;
    private Snake snake = null;

    public Game(int frameX, int frameY) {
        gframe = new int[]{15, 15, frameX - 40, frameY - 100};
        bounds = new int[]{gframe[0] - 1, gframe[1] - 6, gframe[2] + 11, gframe[3] + 1};
    }

    public void set(Snake s) {
        snake = s;
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

    public void addDifficulty() {
        difficulty++;
    }

    public void delDifficulty() {
        if (difficulty - 1 == 0) {
            return;
        }
        difficulty--;
    }

    public void generateObject() {
        currObj = new int[]{
                    (rand.nextInt(59) + 2) * 10, // X coord
                    (rand.nextInt(29) + 1) * 10, // Y coord
                };

        //System.out.println("OBJECT: " + currObj[0] + "," + currObj[1]);
    }

    public void update() {
        int[] c = snake.getCurrentLocation();
        int x = c[0], y = c[1];

        //check if collide with object
        if (x == currObj[0] && y == currObj[1]) {
            addPoint();
            generateObject();
            snake.boostTailLen(difficulty);
        }

        //check if collide with bounds
        if (x < bounds[0] || x > bounds[2] || y < bounds[1] || y > bounds[3]) {
            kill();
            return;
        }

        //check if collide with tail
        for (int[] z : snake.getTailPlot()) {
            if (z[0] == -1) {
                break;
            }
            if (z[0] == c[0] && z[1] == c[1]) {
                kill();
                return;
            }
        }
    }

    public void kill() {
        prevLen = snake.getTailLen();
        snake.clearGrid();
        setActive(false);
        old = score;
        score = 0;
    }

    public void draw(Graphics g) {
        //declare graphics settings
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        g.drawString("Game by wadec", 500, 12);

        //if inactive, show menu
        if (!isActive()) {
            g.drawString("Press 's' to start", 150, 150);
            g.drawString("Difficulty level: " + difficulty, 400, gframe[3] + 15);//difficulty level
            g.drawString("(u)p / (d)own to change difficulty.", 400, gframe[3] + 35);

            //if died
            if (dead) {
                g.drawString("You scored " + old + " €", 25, gframe[3] + 45);//score board
                g.drawString("You were " + prevLen + " blocks long!", 400, gframe[3] + 55);//snake len
                g.setColor(Color.RED);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
                g.drawString("OH SHIT, YOU DIED", 50, 250);
            }
            return;
        }

        //if active, print game info
        g.drawRect(gframe[0], gframe[1], gframe[2], gframe[3]);//game frame
        g.drawString("Score: " + score + " €", 25, gframe[3] + 45);//score board
        g.drawString("Size: " + snake.getTailLen() + " blocks.", 400, gframe[3] + 45);//snake len

        g.setColor(Color.GREEN);
        g.fillRect(currObj[0] - 5, currObj[1] + 5, 10, 10);//object
    }
}
