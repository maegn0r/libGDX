package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    int clickCounter;
    MyAnimation animation;


    @Override
    public void create() {
        batch = new SpriteBatch();
        animation = new MyAnimation("atlas/ogrepack.atlas",Animation.PlayMode.LOOP);
    }

    @Override
    public void render() {
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
    public void dispose() {
        batch.dispose();
        animation.dispose();
    }

    public void update(float dt) {
        animation.update(dt);
    }

}
