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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Adam on 2016. 12. 07..
 */
public class CarSelectionMenu implements Screen {
    Game game;

    Sprite[] cars;
    int whichCar = 0;

    Skin skin;
    Stage stage;

    SpriteBatch sb;

    BitmapFont font;

    Sprite speed;

    public CarSelectionMenu(Game g){
        game = g;
        font = new BitmapFont();
        speed = new Sprite(new Texture("menu/bar.png"));

        cars = new Sprite[2];
        cars[0] = new Sprite(new Texture("car.png"));
        cars[0].setScale(1.2f);
        cars[1] = new Sprite(new Texture("cars/ferrari.png"));
        cars[1].setScale(0.1f);
        cars[1].setRotation(180);

        for (int i = 0; i < cars.length; i++){
            cars[i].setPosition(Gdx.graphics.getWidth() / 2 - cars[i].getWidth() / 2, Gdx.graphics.getHeight() / 2 - cars[i].getHeight() / 2);
        }

        sb = new SpriteBatch();

        skin = new Skin();
        stage = new Stage();

        skin.add("left", new Texture("menu/arrowL.png"));
        skin.add("right", new Texture("menu/arrowR.png"));
        skin.add("play", new Texture("menu/play_button.png"));
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
        leftButton.setPosition(Gdx.graphics.getWidth() / 10 * 3 - leftButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - leftButton.getHeight() / 2);
        leftButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                if (whichCar > 0) whichCar--;
                else whichCar = cars.length - 1;

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
        rightButton.setPosition(Gdx.graphics.getWidth() / 10 * 7 - rightButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - rightButton.getHeight() / 2);
        rightButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                if (whichCar < cars.length - 1) whichCar++;
                else whichCar = 0;
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
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 8 - playButton.getHeight() / 2);
        playButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                //game.setScreen(new PlayScreen(game, CarInfo.models.get(whichCar), LevelInfo.levels.get(0)));
                game.setScreen(new MapSelectionMenu(game, CarInfo.models.get(whichCar)));
            }
        });
        stage.addActor(playButton);

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
        stage.draw();

        sb.begin();
        cars[whichCar].draw(sb);
        font.draw(sb, CarInfo.models.get(whichCar).modelName, 20, 440);

        speed.setSize(CarInfo.models.get(whichCar).speed / 2, 12);
        speed.setPosition(Gdx.graphics.getWidth() / 2 - cars[0].getWidth() / 2, Gdx.graphics.getHeight() / 10 * 3.5f);
        font.draw(sb, "Speed  ", Gdx.graphics.getWidth() / 10 * 4.4f - cars[0].getWidth() / 2, Gdx.graphics.getHeight() / 10 * 3.75f);
        speed.draw(sb);
        sb.end();

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
        stage.dispose();
        skin.dispose();
    }
}
