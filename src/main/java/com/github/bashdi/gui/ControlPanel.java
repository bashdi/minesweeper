package com.github.bashdi.gui;

import com.github.bashdi.GameData;
import com.github.bashdi.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private MinefieldPanel minefieldPanel;
    private GameData gameData;

    public ControlPanel(MinefieldPanel minefieldPanel) {
        this.minefieldPanel = minefieldPanel;

        gameData = GameData.getInstance();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton createGameButton = new JButton("Create");
        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGame();
            }
        });
        add(createGameButton, gbc);
    }


    private void createGame() {
        minefieldPanel.removeAll();
        gameData.setMinesweeper(new Minesweeper(33, 33, 100));
        gameData.getMinesweeper().generateMinefield();
        minefieldPanel.createMinefield();
        minefieldPanel.invalidate();
        minefieldPanel.validate();
        minefieldPanel.repaint();
    }
}
