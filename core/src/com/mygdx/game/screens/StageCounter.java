package com.mygdx.game.screens;

public class StageCounter {

    private static int stageCounter = 1;

    public static int getStageCounter() {
        return stageCounter;
    }
    public static void upStageCounter() {
        stageCounter++;
    }
    public static void clearStageCounter() {
        stageCounter = 1;
    }

}
