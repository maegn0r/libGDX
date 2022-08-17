package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int clickCounter;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("javacup.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);

		float x = Gdx.input.getX() - img.getWidth()/2;
		float y = Gdx.graphics.getHeight() - Gdx.input.getY() - img.getHeight()/2;

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) clickCounter++;

		Gdx.graphics.setTitle("Было сделано "+ clickCounter +" левых кликов мышкой");
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
