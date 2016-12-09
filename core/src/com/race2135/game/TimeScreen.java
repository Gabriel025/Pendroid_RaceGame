package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Gabriel025 on 2016.12.09.
 */

public class TimeScreen implements Screen {
    Game game;

    SpriteBatch spriteBatch;

    Texture background;
    BitmapFont font;

    String timeStr;
    GlyphLayout glyphLayout;

    Stage stage;
    Skin skin;

    float ratioX = (float)Gdx.graphics.getWidth() / 800, ratioY = (float)Gdx.graphics.getHeight() / 480;

    public TimeScreen(Game g, long time)
    {
        game = g;
        background = new Texture("menu/bg2.jpg");

        long min = time / 60000, sec = (time - min * 60000) / 1000,
                centisec = (time - min * 60000 - sec * 1000) / 10;

        timeStr = "Round complete!\n\n" + min + ":" + sec + "." + centisec;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.color = Color.WHITE;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);

        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, timeStr, Color.WHITE, Gdx.graphics.getWidth(), Align.center, false);

        spriteBatch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.add("default", font);
        skin.add("back", new Texture("menu/back.png"));

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
                game.setScreen(new MainMenu(game));
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(spriteBatch, glyphLayout, 0,
                (float)Gdx.graphics.getHeight() / 2 + glyphLayout.height / 2);
        spriteBatch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

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
