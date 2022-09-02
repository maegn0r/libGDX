package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class MenuScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture img;
    private final Rectangle startRect;
    private final ShapeRenderer shapeRenderer;
    private final Music music;
    private final Sound missClickOnMenuScreenSound;
    private final float BUTTON_WIDTH = 250;
    private final float BUTTON_HEIGHT = 100;

    public MenuScreen(Main game) {
        this.game = game;
        img = new Texture("main_screen.png");
        Gdx.graphics.setWindowedMode(img.getWidth(), img.getHeight());
        batch = new SpriteBatch();
        startRect = new Rectangle(Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2, Gdx.graphics.getHeight() / 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        shapeRenderer = new ShapeRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal("main_menu_sound.mp3"));
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();

        missClickOnMenuScreenSound = Gdx.audio.newSound(Gdx.files.internal("ha_ha_ha_error_on_menu_screen.mp3"));

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.WHITE);

        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(startRect.x, startRect.y, startRect.width, startRect.height);
        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            Vector2 vect = new Vector2(x, y);
            if (startRect.contains(vect)) {
//                startGameSound.play();
                game.setScreen(new GameScreen(game));
                dispose();
            }
            else {
                missClickOnMenuScreenSound.play();
            }
        }
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
        this.batch.dispose();
        this.img.dispose();
        this.shapeRenderer.dispose();
        this.music.dispose();
//        this.startGameSound.dispose();
        this.missClickOnMenuScreenSound.dispose();
    }

    public Texture getImg() {
        return img;
    }
}
