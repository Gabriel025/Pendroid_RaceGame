package com.race2135.game;

/**
 * GameInstanceInfo class
 *
 * Passed to GameScreen via its constructor, provides information about a game session.
 *
 * Created by Gabriel025 on 2016.11.27.
 */

public class GameInstanceInfo {
    public CarInfo carInfo;
    public String levelPath;

    public GameInstanceInfo(CarInfo car, String levelPath)
    {
        carInfo = car;
        this.levelPath = levelPath;
    }
}
