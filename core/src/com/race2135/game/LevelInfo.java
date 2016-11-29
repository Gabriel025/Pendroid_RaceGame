package com.race2135.game;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Gabriel025 on 2016.11.29.
 */

public class LevelInfo {
    String levelName, levelDescription;

    public String levelPath;
    public int levelScale;
    public String bodyName;

    //TODO add start position

    //Constructor is private, all available instances are created in init()
    private LevelInfo() { }

    //Array containing all available models
    public static Array<LevelInfo> levels;

    //Initializaton
    public static void init()
    {
        levels = new Array<LevelInfo>();

        LevelInfo testTrack = new LevelInfo();

        testTrack.levelName = "Test";
        testTrack.levelDescription = "Just a test track.";

        testTrack.levelPath = "map_physics1.json";
        testTrack.levelScale = 400;
        testTrack.bodyName = "Name";

        levels.add(testTrack);
    }
}
