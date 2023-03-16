package com.minesweeper.views;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class Container extends JPanel {
    public Container() {
        CardLayout card = new CardLayout();
        this.setBackground(Color.black);
        this.setSize(800, 600);

        Game game = new Game(this);
        Welcome welcome = new Welcome(this);

        this.setLayout(card);
        this.add(welcome, "1");
        this.add(game, "2");
        card.show(this, "1");
        this.setVisible(true);
    }

}
