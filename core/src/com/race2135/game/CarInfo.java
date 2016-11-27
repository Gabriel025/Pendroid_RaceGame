package com.race2135.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * CarInfo class
 *
 * Describes a given car model. Has a private constructor,
 * and a static list of implemented instances.
 * Fields are not encapsulated (didn't bother :D), and beware that
 * changing their values in runtime is likely to lead to
 * undocumented behavior.
 *
 * Created by Gabriel025 on 2016.11.27.
 */

public class CarInfo {
    public String modelName, modelDescription;

    //Appearance, dimensions, ...
    Texture bodyTexture, tireTexture;
    int width, length, tireDiameter, tireThickness;
    int mass;

    //Engine and gearbox
    Sound engineSound;
    int idleRPM, powerRPM, maxRPM;
    int numGears;
    int[] gearRatios;

    //Tires
    boolean is4x4;
    //TODO add tire drive, friction, slip, and turn related variables

    //Constructor is private, all available instances are created in init()
    private CarInfo() { }

    //Array containing all available models
    public static Array<CarInfo> models;

    //Initializaton
    public static void init()
    {
        //TODO put some car definitions in here, and add them to the instance array
    }
}
