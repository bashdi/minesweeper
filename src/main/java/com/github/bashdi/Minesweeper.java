package com.github.bashdi;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Minesweeper {

    //Spielfeld-Eigenschaften
    private int x, y, bombs;
    private final int MIN_X = 5;
    private final int MIN_Y = 5;
    private final int MIN_BOMBS = 1;

    //Spielfelder
    private int[][] minefield, visibleMinefield;

    //Feldstatus
    public final static int FIELD_BOMB = -1;
    public final static int FIELD_MARKER = -2;
    public final static int FIELD_UNKOWN = -3;

    //Spielstatus
    public final static int GAMESTATUS_WIN = 1;
    public final static int GAMESTATUS_NEUTRAL = 0;
    public final static int GAMESTATUS_LOST = -1;
    private int gamestatus = 0;


    public Minesweeper(int x, int y, int bombs) {
        this.x = Math.max(x, MIN_X);
        this.y = Math.max(y, MIN_Y);
        this.bombs = Math.min(Math.max(bombs, MIN_BOMBS), (this.x * this.y) - 2);
    }


    public int[][] generateMinefield() {
        minefield = new int[x][y];
        visibleMinefield = new int[x][y];

        gamestatus = GAMESTATUS_NEUTRAL;


        //Bomben setzen
        int randomX, randomY;
        for (int i = 1; i <= bombs; i++) {
            //Zufälliges Feld aussuchen, wenn schon Bombe, dann nochmal aussuchen
            while (true) {
                randomX = ThreadLocalRandom.current().nextInt(0, x);
                randomY = ThreadLocalRandom.current().nextInt(0, y);

                if (minefield[randomX][randomY] != FIELD_BOMB) {
                    minefield[randomX][randomY] = FIELD_BOMB;
                    break;
                }
            }
        }


        //Zahlenwerte für alle anderen Felder setzen
        for (int indexX = 0; indexX < x; indexX++) {
            for (int indexY = 0; indexY < y; indexY++) {
                //Wenn keine Bombe, Bomben im Radius zählen
                if (minefield[indexX][indexY] != FIELD_BOMB) {
                    int bombCounter = 0;
                    int checkX = indexX - 1;
                    int checkY;

                    for (int i = 1; i <= 3; i++) {
                        if (checkX < 0 || checkX >= x) {
                            checkX++;
                            continue;
                        }

                        checkY = indexY - 1;
                        for (int j = 1; j <= 3; j++) {
                            if (checkY < 0 || checkY >= y) {
                                checkY++;
                                continue;
                            }
                            if (minefield[checkX][checkY] == FIELD_BOMB) bombCounter++;
                            checkY++;
                        }
                        checkX++;
                    }


                    minefield[indexX][indexY] = bombCounter;
                }
            }
        }


        //Alle Felder des sichtbaren Feldes auf FIELD_UNKOWN setzen
        for (int indexX = 0; indexX < x; indexX++) {
            for (int indexY = 0; indexY < y; indexY++) {
                visibleMinefield[indexX][indexY] = FIELD_UNKOWN;
            }
        }

        return visibleMinefield;
    }


    public int[][] getMinefield() {
        return visibleMinefield;
    }

    public int getMinefield(int x, int y) {
        return visibleMinefield[x][y];
    }


    public void markField(int x, int y) {
        visibleMinefield[x][y] = FIELD_MARKER;
    }


    public int checkField(int x, int y, Set<Point> history) {
        int fieldValue = minefield[x][y];
        visibleMinefield[x][y] = fieldValue;

        if (fieldValue == FIELD_BOMB) {
            gamestatus = GAMESTATUS_LOST;
        } else {
            //Prüfen ob nur noch Bomben übrig sind
            int remainingFields = 0;
            for (int indexX = 0; indexX < x; indexX++) {
                for (int indexY = 0; indexY < y; indexY++) {
                    if (visibleMinefield[indexX][indexY] != FIELD_BOMB) {
                        remainingFields++;
                    }
                }
            }
            if (bombs == remainingFields) {
                gamestatus = GAMESTATUS_WIN;
            }
        }

        //Wenn 0, dann die Felder im Radius auf 0 prüfen und dem visibleMinefield hinzufügen
        if (fieldValue == 0) {
            int checkX = x - 1;
            int checkY;

            for (int i = 1; i <= 3; i++) {
                if (checkX < 0 || checkX >= this.x) {
                    checkX++;
                    continue;
                }

                checkY = y - 1;
                for (int j = 1; j <= 3; j++) {
                    if (checkY < 0 || checkY >= this.y) {
                        checkY++;
                        continue;
                    }
                    if (minefield[checkX][checkY] >= 0) {
                        if (history.add(new Point(checkX, checkY))) {
                            checkField(checkX, checkY, history); //rekursion
                        }
                    }
                    checkY++;
                }
                checkX++;
            }
        }


        return fieldValue;
    }


    public int getGamestatus() {
        return gamestatus;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBombs() {
        return bombs;
    }
}
