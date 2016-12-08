package com.race2135.game;

import com.badlogic.gdx.Gdx;
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
    float width, length, tireDiameter, tireThickness;
    float mass;
    public float speed;

    //Engine and gearbox
    String engineSoundLowPath, engineSoundHighPath;
    int engineSoundLowRPM,  engineSoundHighRPM; //RPM of the engine in the sound samples
    int numGears;
    float[] gearRatios;

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
        models = new Array<CarInfo>();

        //TODO put some car definitions in here, and add them to the instance array
        //TODO it might be a good idea to load these from a file if we implement more cars
        CarInfo testCar = new CarInfo();
        testCar.modelName = "Default";
        testCar.modelDescription = "Just a test model.";
        testCar.speed = 120;

        testCar.bodyTexture = new Texture("cars/car.png");
        //testCar.tireTexture = new Texture("");

        testCar.width = 1.8f;
        testCar.length = 4f;
        testCar.tireDiameter = 0.65f;
        testCar.tireThickness = 0.2f;
        testCar.mass = 1400f;

        testCar.engineSoundLowPath = "test_engine_low.wav";
        testCar.engineSoundLowRPM = 900;
        testCar.engineSoundHighPath = "test_engine_high.wav";
        testCar.engineSoundHighRPM = 3600;

        testCar.numGears = 7;
        testCar.gearRatios = new float[]{-1f / 2.285f, 0f,1f / 2.774f, 1f / 1.974f, 1f / 1.534f, 1f, 1f / 0.756f};

        testCar.is4x4 = true;

        models.add(testCar);

        CarInfo furgon = new CarInfo();
        furgon.modelName = "Furgon";
        furgon.modelDescription = "'The biggest...'";
        furgon.bodyTexture = new Texture("cars/car2.png");
        furgon.speed = 140;


        furgon.width = 1.8f;
        furgon.length = 4f;
        furgon.tireDiameter = 0.65f;
        furgon.tireThickness = 0.2f;
        furgon.mass = 2000f;

        furgon.engineSoundLowPath = "test_engine_low.wav";
        furgon.engineSoundLowRPM = 900;
        furgon.engineSoundHighPath = "test_engine_high.wav";
        furgon.engineSoundHighRPM = 3600;

        furgon.numGears = 7;
        furgon.gearRatios = new float[]{-1f / 2.285f, 0f,1f / 2.774f, 1f / 1.974f, 1f / 1.534f, 1f, 1f / 0.756f};

        testCar.is4x4 = true;


        models.add(furgon);

        //MISSING INFOS
    }
}
