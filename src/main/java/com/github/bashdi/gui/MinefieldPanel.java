package com.github.bashdi.gui;

import com.github.bashdi.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinefieldPanel extends JPanel {

    private List<MinefieldButton> minefieldButtonList;
    private GridBagConstraints gbc;


    public MinefieldPanel() {


        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;

        GameWindow.minesweeper = new Minesweeper(33, 33, 33);
        GameWindow.minesweeper.generateMinefield();
        createMinefield();

    }


    public void createMinefield() {
        if (GameWindow.minesweeper == null) return;

        minefieldButtonList = new ArrayList<>();
        for (int column = 0; column < GameWindow.minesweeper.getX(); column++) {
            for (int row = 0; row < GameWindow.minesweeper.getY(); row++) {
                gbc.gridx = column;
                gbc.gridy = row;
                MinefieldButton minefieldButton = new MinefieldButton(GameWindow.minesweeper, minefieldButtonList, column, row);
                minefieldButtonList.add(minefieldButton);
                add(minefieldButton, gbc);
            }
        }
    }


}
