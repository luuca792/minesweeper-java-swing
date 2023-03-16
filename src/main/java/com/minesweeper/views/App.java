package com.minesweeper.views;

import javax.swing.JFrame;

public class App {
    public App() {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cont = new Container();
        frame.add(cont);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
