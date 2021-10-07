package com.company;

import com.company.game.GameModule;

public class Main {

    public static GameModule gameModule;

    public static void main(String[] args) {
        gameModule = new GameModule();
        gameModule.start();
    }
}
