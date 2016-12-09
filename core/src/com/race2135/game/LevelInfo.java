package com.race2135.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Gabriel025 on 2016.11.29.
 */

public class LevelInfo {
    String levelName, levelDescription, levelTexture, trackTexture;

    public String levelPath;
    public int levelScale;
    public String bodyName;

    public Vector2 startPosition;
    public Vector2 roundPivot; //A point around which the car turns a full 360° upon
    //level completion.

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

        testTrack.levelName = "Basic";
        testTrack.levelDescription = "Just a basic\ntrack.";

        testTrack.levelTexture = "race.png";
        testTrack.trackTexture = "menu/track1.png";

        testTrack.levelPath = "map_physics1.json";
        testTrack.levelScale = 400;
        testTrack.bodyName = "Name";

        testTrack.startPosition = new Vector2(600f / 960f * 400f, (960f - 750f) / 960f * 400f);
        testTrack.roundPivot = new Vector2(565f / 960f * 400f, (960f - 750f) / 960f * 400f);

        levels.add(testTrack);


        LevelInfo sand = new LevelInfo();
        sand.levelName = "Sandy";
        sand.levelDescription = "A simple track\nwith slightly\nworse traction.";

        sand.levelTexture = "race_sand.png";
        sand.trackTexture = "menu/track2.png";

        sand.levelPath = "sandmap.json";
        sand.levelScale = 400;
        sand.bodyName = "map";

        sand.startPosition = new Vector2(205f / 960f * 400f, (960f - 595f) / 960f * 400f);
        sand.roundPivot = new Vector2(235f / 960f * 400f, (960f - 595f) / 960f * 400f);

        levels.add(sand);

        LevelInfo round = new LevelInfo();
        round.levelName = "Round";
        round.levelDescription = "A square track.";

        round.levelTexture = "map3.png";
        round.trackTexture = "track3.png";

        round.levelPath = "map_phys3.json";
        round.levelScale = 400;
        round.bodyName = "Name";

        round.startPosition = new Vector2(215f / 960f * 400f, (960f - 530f) / 960f * 400f);
        round.roundPivot = new Vector2(245f / 960f * 400f, (960f - 530f) / 960f * 400f);

        levels.add(round);
    }
}
