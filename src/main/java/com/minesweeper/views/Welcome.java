package com.minesweeper.views;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Welcome extends JPanel implements ActionListener {

    JButton play;
    JPanel parent;

    public Welcome(JPanel parent) {
        this.parent = parent;
        this.setBackground(Color.blue);
        this.setSize(800, 600);
        this.setLayout(new FlowLayout());

        play = new JButton("Play");
        play.setSize(100, 30);
        play.addActionListener(this);

        this.add(play);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            CardLayout card = (CardLayout) parent.getLayout();
            card.show(parent, "2");
        }

    }

}
