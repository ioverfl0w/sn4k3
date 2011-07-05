package org.wadec.snake;

import javax.swing.JFrame;

/**
 *
 * @author Wade C
 */
public class Frame extends JFrame {

    public Snake snake;

    public Frame() {
        setTitle("sn4k3");
        construct();
        setVisible(true);
    }

    private void construct() {
        //set basic vars
        setSize(640, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create game frame
        Game frame = new Game(getWidth(), getHeight());

        //create play field
        snake = new Snake(getWidth() / 2, getHeight() / 2, frame);
        add(snake);
        addKeyListener(snake);

        //start game thread
        new Thread(snake).start();
    }

    public static void main(String[] args) {
        new Frame();
    }
}
