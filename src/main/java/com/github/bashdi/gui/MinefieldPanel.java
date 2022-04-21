package com.github.bashdi.gui;

import com.github.bashdi.GameData;
import com.github.bashdi.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinefieldPanel extends JPanel {

    private List<MinefieldButton> minefieldButtonList;
    private GridBagConstraints gbc;
    private GameData gameData;


    public MinefieldPanel() {
        gameData = GameData.getInstance();
        gameData.setMinesweeper(new Minesweeper(33, 33, 100));
        gameData.getMinesweeper().generateMinefield();

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;

        createMinefield();
    }


    public void createMinefield() {
        if (gameData.getMinesweeper() == null) return;

        minefieldButtonList = new ArrayList<>();
        for (int column = 0; column < gameData.getMinesweeper().getX(); column++) {
            for (int row = 0; row < gameData.getMinesweeper().getY(); row++) {
                gbc.gridx = column;
                gbc.gridy = row;
                MinefieldButton minefieldButton = new MinefieldButton(gameData.getMinesweeper(), minefieldButtonList, column, row);
                minefieldButtonList.add(minefieldButton);
                add(minefieldButton, gbc);
            }
        }
    }


}
