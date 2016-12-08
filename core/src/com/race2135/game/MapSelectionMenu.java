package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Adam on 2016. 12. 07..
 */
public class MapSelectionMenu implements Screen {
    Game game;

    Sprite[] maps;
    int whichMap = 0;

    Skin skin;
    Stage stage;

    SpriteBatch sb;

    BitmapFont font;

    Sprite dif;
    CarInfo carInfo;

    Texture bg;
    BitmapFont descFont, titleFont;

    float ratioX = (float)Gdx.graphics.getWidth() / 800, ratioY = (float)Gdx.graphics.getHeight() / 480;


    public MapSelectionMenu(Game g, final CarInfo carInfo){
        this.game = g;
        this.carInfo = carInfo;

        bg = new Texture("menu/bg2.jpg");


        descFont = new BitmapFont();
        titleFont = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(15 * ratioX);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        descFont = generator.generateFont(parameter);
        parameter.size = (int)(40 * ratioX);
        titleFont = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        maps = new Sprite[LevelInfo.levels.size];
        for (int i = 0; i < maps.length; i++) {
            maps[i] = new Sprite(new Texture(LevelInfo.levels.get(i).trackTexture));
            maps[i].setSize(200 * ratioX, 300 * ratioY);
            maps[i].setPosition(Gdx.graphics.getWidth() / 2 - maps[i].getWidth() / 2, Gdx.graphics.getHeight() / 2 - maps[i].getHeight() / 2);

        }

        sb = new SpriteBatch();

        skin = new Skin();
        stage = new Stage();

        skin.add("left", new Texture("menu/arrowL.png"));
        skin.add("right", new Texture("menu/arrowR.png"));
        skin.add("play", new Texture("menu/play_button.png"));
        skin.add("back", new Texture("menu/back.png"));

        BitmapFont bfont = new BitmapFont();
        skin.add("default",bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("left", Color.GREEN);
        textButtonStyle.down = skin.newDrawable("left", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("left", Color.GREEN);
        textButtonStyle.over = skin.newDrawable("left", Color.GREEN);
        textButtonStyle.font = skin.getFont("default");

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        final TextButton leftButton = new TextButton("",textButtonStyle);
        leftButton.setSize(leftButton.getWidth() * ratioX, leftButton.getHeight() * ratioY);
        leftButton.setPosition(Gdx.graphics.getWidth() / 10 * 3 - leftButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - leftButton.getHeight() / 2);
        leftButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                if (whichMap > 0) whichMap--;
                else whichMap = LevelInfo.levels.size - 1;

            }
        });
        stage.addActor(leftButton);

        TextButton.TextButtonStyle textButtonStyleR = new TextButton.TextButtonStyle();
        textButtonStyleR.up = skin.newDrawable("right", Color.GREEN);
        textButtonStyleR.down = skin.newDrawable("right", Color.DARK_GRAY);
        textButtonStyleR.checked = skin.newDrawable("right", Color.GREEN);
        textButtonStyleR.over = skin.newDrawable("right", Color.GREEN);
        textButtonStyleR.font = skin.getFont("default");

        textButtonStyleR.font = skin.getFont("default");

        final TextButton rightButton = new TextButton("",textButtonStyleR);
        rightButton.setSize(rightButton.getWidth() * ratioX, rightButton.getHeight() * ratioY);
        rightButton.setPosition(Gdx.graphics.getWidth() / 10 * 7 - rightButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - rightButton.getHeight() / 2);
        rightButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                if (whichMap < maps.length - 1) whichMap++;
                else whichMap = 0;
            }
        });
        stage.addActor(rightButton);

        TextButton.TextButtonStyle playStyle = new TextButton.TextButtonStyle();
        playStyle.up = skin.newDrawable("play", Color.GREEN);
        playStyle.down = skin.newDrawable("play", Color.DARK_GRAY);
        playStyle.checked = skin.newDrawable("play", Color.GREEN);
        playStyle.over = skin.newDrawable("play", Color.GREEN);
        playStyle.font = skin.getFont("default");

        textButtonStyleR.font = skin.getFont("default");

        final TextButton playButton = new TextButton("",playStyle);
        playButton.setSize(ratioX * playButton.getWidth(), ratioY * playButton.getHeight());
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 8 - playButton.getHeight() / 2);
        playButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                //game.setScreen(new PlayScreen(game, CarInfo.models.get(whichCar), LevelInfo.levels.get(0)));
                game.setScreen(new PlayScreen(game, carInfo, LevelInfo.levels.get(whichMap)));
            }
        });
        stage.addActor(playButton);


        TextButton.TextButtonStyle backStyle = new TextButton.TextButtonStyle();
        backStyle.up = skin.newDrawable("back", Color.GREEN);
        backStyle.down = skin.newDrawable("back", Color.DARK_GRAY);
        backStyle.checked = skin.newDrawable("back", Color.GREEN);
        backStyle.over = skin.newDrawable("back", Color.GREEN);
        backStyle.font = skin.getFont("default");

        backStyle.font = skin.getFont("default");

        final TextButton backButton = new TextButton("",backStyle);
        backButton.setSize(backButton.getWidth() / 5.5f * ratioX, backButton.getHeight() / 5.5f * ratioY);
        backButton.setPosition(Gdx.graphics.getWidth() / 10 - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 8 - backButton.getHeight() / 2);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                //game.setScreen(new PlayScreen(game, CarInfo.models.get(whichCar), LevelInfo.levels.get(0)));
                game.setScreen(new CarSelectionMenu(game));
            }
        });
        stage.addActor(backButton);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        sb.begin();
        sb.draw(bg, 0, 0, 800 * ratioX, 480 * ratioY);
        maps[whichMap].draw(sb);


        descFont.draw(sb, "Info: " + LevelInfo.levels.get(whichMap).levelDescription, 15 * ratioX, Gdx.graphics.getHeight() / 10 * 7.5f);
        descFont.draw(sb, "Name: " + LevelInfo.levels.get(whichMap).levelName, 15 * ratioX, Gdx.graphics.getHeight() / 10 * 8);
        titleFont.draw(sb, "Choose a map", Gdx.graphics.getWidth() / 2 - 150 * ratioX, Gdx.graphics.getHeight() / 10 * 9.5f);
        sb.end();

        stage.draw();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
