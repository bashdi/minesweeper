package com.github.bashdi.gui;

import com.github.bashdi.Minesweeper;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public static Minesweeper minesweeper;

    public GameWindow() {
        setTitle("Minesweeper");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;


        gbc.gridx = 0;
        gbc.gridy = 1;
        MinefieldPanel minefieldPanel = new MinefieldPanel();
        add(minefieldPanel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new ControlPanel(minefieldPanel), gbc);

        pack();
        setVisible(true);
    }
}
