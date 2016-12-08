package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu implements Screen {
    Main game;

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    Texture bg;

    float ratioX = (float)Gdx.graphics.getWidth() / 800, ratioY = (float)Gdx.graphics.getHeight() / 480;

    public MainMenu(Main g){
        game= g;

        batch = new SpriteBatch();
        stage = new Stage();

        bg = new Texture("menu/background.png");

        skin = new Skin();
        Pixmap pixmap = new Pixmap(100, 50, Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        skin.add("background", new Texture(pixmap));
        BitmapFont bfont = new BitmapFont();
        bfont.getData().setScale(ratioX, ratioY);
        skin.add("default",bfont);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        final TextButton textButton = new TextButton("PLAY",textButtonStyle);
        textButton.getLabel().setFontScale(ratioX, ratioY);
        textButton.setSize(textButton.getWidth() * ratioX, textButton.getHeight() * ratioY);
        textButton.setPosition(Gdx.graphics.getWidth() / 2 - textButton.getWidth() / 2 / ratioX, Gdx.graphics.getHeight() / 2 - textButton.getHeight() / 2 / ratioY);
        stage.addActor(textButton);

        textButton.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                textButton.setText("Starting new game");
                game.setScreen(new CarSelectionMenu(game));

            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        batch.begin();
        batch.draw(bg, 0, 0, 800 * ratioX, 480 * ratioY);
        batch.end();
        stage.draw();

    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }
}