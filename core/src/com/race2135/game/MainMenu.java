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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu implements Screen {
    Game game;

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    Texture bg;
    BitmapFont descFont;

    float ratioX = (float)Gdx.graphics.getWidth() / 800, ratioY = (float)Gdx.graphics.getHeight() / 480;

    public MainMenu(Game g){
        game= g;

        batch = new SpriteBatch();
        stage = new Stage();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(20 * ratioX);
        descFont = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        bg = new Texture("menu/background.png");

        skin = new Skin();
        skin.add("play", new Texture("menu/1.png"));
        skin.add("default",descFont);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("play", Color.WHITE);
        textButtonStyle.down = skin.newDrawable("play", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("play", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("play", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        final TextButton textButton = new TextButton("",textButtonStyle);
        textButton.getLabel().setFontScale(ratioX, ratioY);
        textButton.setSize(textButton.getWidth() / 2 * ratioX, textButton.getHeight() / 2 * ratioY);
        textButton.setPosition(Gdx.graphics.getWidth() / 2 - textButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - textButton.getHeight() / 2);
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