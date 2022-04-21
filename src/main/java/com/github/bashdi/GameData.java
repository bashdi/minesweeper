package com.github.bashdi;

public class GameData {

    private static GameData gameData;

    private Minesweeper minesweeper;

    private GameData() {

    }

    public synchronized static GameData getInstance() {
        if (gameData == null) {
            gameData = new GameData();
        }
        return gameData;
    }

    public Minesweeper getMinesweeper() {
        return minesweeper;
    }

    public void setMinesweeper(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
    }
}
