package com.minesweeper.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends JPanel implements ActionListener {

    private final int ROW = 16;
    private final int COL = 16;
    private final int MINE = -1;
    private final int MINE_NUM = 40;

    JPanel parent;
    JPanel map;

    JButton[][] cells;
    int[][] cell_state;
    int[][] mine_map;

    public Game(JPanel parent) {
        this.parent = parent;
        this.setBackground(Color.red);
        this.setSize(800, 600);

        map = new JPanel();
        cells = new JButton[ROW][COL];

        this.setLayout(new BorderLayout());
        this.add(map, BorderLayout.CENTER);
        map.setLayout(new GridLayout(ROW, COL));

        // Initialize buttons

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cells[i][j] = new JButton();
                cells[i][j].addActionListener(this);
                cells[i][j].setFocusable(false);
                cells[i][j].setBorder(BorderFactory.createRaisedSoftBevelBorder());
                cells[i][j].setBackground(Color.decode("#e0e0e0"));
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                map.add(cells[i][j]);
            }
        }

        // Initialize cell state and mine map
        cell_state = new int[ROW][COL];
        mine_map = new int[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cell_state[i][j] = 0;
                mine_map[i][j] = 0;
            }
        }

        // Initialize mine map
        init_mine_map(MINE_NUM);
        count_mines();
        update_cell_color();
    }

    public void init_mine_map(int k) {
        int count = 0;
        while (count < k) {
            int r = ThreadLocalRandom.current().nextInt(0, ROW - 1);
            int c = ThreadLocalRandom.current().nextInt(0, COL - 1);
            if (mine_map[r][c] != MINE) {
                mine_map[r][c] = MINE;
                count++;
            }
        }
    }

    public void update_print_map() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (cell_state[i][j] == 1) {
                    cells[i][j].setBackground(Color.decode("#cccccc"));
                    // cells[i][j].setOpaque(false);
                    cells[i][j].setFocusPainted(false);
                    if (mine_map[i][j] == MINE)
                        cells[i][j].setText("x");
                    else if (mine_map[i][j] == 0)
                        cells[i][j].setText("");
                    else
                        cells[i][j].setText(String.valueOf(mine_map[i][j]));
                }

            }
        }
    }

    public void update_cell_color() {
        List<String> num_colors = Arrays.asList(
                "#0a0afb",
                "#008200",
                "#fe0000",
                "#000084",
                "#840000",
                "#008284",
                "#840084",
                "#757575");
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (mine_map[i][j] != MINE && mine_map[i][j] != 0)
                    cells[i][j].setForeground(Color.decode(num_colors.get(mine_map[i][j] - 1)));
            }
        }
    }

    public void count_mines() {
        int i = 0, j = 0;
        for (i = 0; i < ROW; i++)
            for (j = 0; j < COL; j++)
                if (mine_map[i][j] != MINE) {
                    int count = 0;
                    if (i - 1 >= 0 && j - 1 >= 0 && mine_map[i - 1][j - 1] == MINE)
                        count++;
                    if (i - 1 >= 0 && mine_map[i - 1][j] == MINE)
                        count++;
                    if (i - 1 >= 0 && j + 1 < COL && mine_map[i - 1][j + 1] == MINE)
                        count++;

                    if (j - 1 >= 0 && mine_map[i][j - 1] == MINE)
                        count++;
                    if (j + 1 < COL && mine_map[i][j + 1] == MINE)
                        count++;

                    if (i + 1 < ROW && j - 1 >= 0 && mine_map[i + 1][j - 1] == MINE)
                        count++;
                    if (i + 1 < ROW && mine_map[i + 1][j] == MINE)
                        count++;
                    if (i + 1 < ROW && j + 1 < COL && mine_map[i + 1][j + 1] == MINE)
                        count++;
                    mine_map[i][j] = count;
                }
    }

    public void open_cell(int i, int j) {
        if (cell_state[i][j] != 1) {
            cell_state[i][j] = 1;
            if (mine_map[i][j] == 0) {
                if (i - 1 >= 0 && j - 1 >= 0)
                    open_cell(i - 1, j - 1);
                if (i - 1 >= 0)
                    open_cell(i - 1, j);
                if (i - 1 >= 0 && j + 1 < COL)
                    open_cell(i - 1, j + 1);

                if (j - 1 >= 0)
                    open_cell(i, j - 1);
                if (j + 1 < COL)
                    open_cell(i, j + 1);

                if (i + 1 < ROW && j - 1 >= 0)
                    open_cell(i + 1, j - 1);
                if (i + 1 < ROW)
                    open_cell(i + 1, j);
                if (i + 1 < ROW && j + 1 < COL)
                    open_cell(i + 1, j + 1);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (e.getSource() == cells[i][j]) {
                    // System.out.println(i + "-" + j);
                    // cell_state[i][j] = 1; // Indicated that this cell has been reveal
                    // update_print_map();
                    open_cell(i, j);
                    update_print_map();

                }

            }
        }

    }

}
