package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.MyAnimation;

public class GameScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private int clickCounter;
    private MyAnimation animation;


    public GameScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        animation = new MyAnimation("atlas/ogrepack.atlas", Animation.PlayMode.LOOP);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
        ScreenUtils.clear(1, 1, 1, 1);
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        animation.setTime(dt);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) clickCounter++;
        Gdx.graphics.setTitle("Было сделано " + clickCounter + " левых кликов мышкой");

        batch.begin();
        animation.render(batch);
        batch.end();

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
        batch.dispose();
        animation.dispose();
    }

    public void update(float dt) {
        animation.update(dt);
    }

}
