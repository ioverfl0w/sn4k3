package org.wadec.snake;

import javax.swing.JFrame;

/**
 *
 * @author Wade C
 */
public class Frame extends JFrame {

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


        //create play field
        Snake snake = new Snake(getWidth() / 2, getHeight() / 2);
        add(snake);
        snake.addKeyListener(snake);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
