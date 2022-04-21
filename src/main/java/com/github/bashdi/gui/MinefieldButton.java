package com.github.bashdi.gui;

import com.github.bashdi.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;

public class MinefieldButton extends JButton {

    private static final Color COLOR_STATUS_UNKOWN = new Color(255, 255, 255);
    private static final Color COLOR_STATUS_BOMB = new Color(204, 1, 1, 255);
    private static final Color COLOR_STATUS_NUMBER = new Color(27, 153, 94);
    private static final Color COLOR_STATUS_ZERO = new Color(40, 229, 140);
    private static final Color COLOR_STATUS_MARKER = new Color(255, 215, 102);

    private int minefieldCordX;
    private int minefieldCordY;

    private int fieldStatus;


    public MinefieldButton(Minesweeper minesweeper, List<MinefieldButton> buttonList, int x, int y) {
        super();
        setMinefieldStatus(Minesweeper.FIELD_UNKOWN);
        minefieldCordX = x;
        minefieldCordY = y;
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setPreferredSize(new Dimension(20, 20));
        setFont(new Font("Arial", Font.BOLD, 10));
        setBorderPainted(false);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //Nur Felder mit Status Unbekannt oder Marker dürfen sich ändern
                if (fieldStatus != Minesweeper.FIELD_UNKOWN && fieldStatus != Minesweeper.FIELD_MARKER) {
                    return;
                }
                //Rechter Mausklick = Marker setzen
                //Alle andere Klicks = Normale Aktion
                if (e.getButton() != MouseEvent.BUTTON3) {
                    int fieldStatus = minesweeper.checkField(x, y, new HashSet<Point>());
                    //0 Bomben im Radius -> deckt alle 0-Felder + Randfelder im Radius auf
                    //Felder der betroffenen Felder aktualisieren
                    if (fieldStatus == 0) {
                        for (MinefieldButton mfb : buttonList) {
                            mfb.setMinefieldStatus(
                                    minesweeper.getMinefield(mfb.minefieldCordX,
                                            mfb.minefieldCordY)
                            );
                        }
                    } else {
                        setMinefieldStatus(fieldStatus);
                    }
                } else {
                    minesweeper.markField(x, y);
                    setMinefieldStatus(Minesweeper.FIELD_MARKER);
                }
            }
        });


    }


    public void setMinefieldStatus(int status) {
        fieldStatus = status;
        switch (status) {
            case Minesweeper.FIELD_UNKOWN:
                setBackground(COLOR_STATUS_UNKOWN);
                setText("?");
                break;
            case Minesweeper.FIELD_MARKER:
                setBackground(COLOR_STATUS_MARKER);
                setText("M");
                break;
            case Minesweeper.FIELD_BOMB:
                setBackground(COLOR_STATUS_BOMB);
                setText("X");
                break;
            case 0:
                setBackground(COLOR_STATUS_ZERO);
                setText("-");
                break;
            default:
                setBackground(COLOR_STATUS_NUMBER);
                setText(String.valueOf(status));
        }

    }


}
