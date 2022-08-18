package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int clickCounter;
	MyAnimation animation;
	float x = 0;
	float y = 0;
	Rectangle rectangle = new Rectangle(0,0,500,500);
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		animation = new MyAnimation("boy.png",6,3, Animation.PlayMode.LOOP);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);


		animation.setTime(Gdx.graphics.getDeltaTime());

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) clickCounter++;
		Gdx.graphics.setTitle("Было сделано "+ clickCounter +" левых кликов мышкой");

		if (!animation.getFrame().isFlipX() && animation.getCurrentPositionX() <= (Gdx.graphics.getWidth() - animation.getFrame().getRegionWidth())) {animation.moveRight();}
		else if (animation.getFrame().isFlipX() && animation.getCurrentPositionX() > 0.0F) {animation.moveLeft();}
		if (animation.getCurrentPositionX() == (Gdx.graphics.getWidth() - animation.getFrame().getRegionWidth()) -1) {animation.getFrame().flip(true,false);}
		if (animation.getCurrentPositionX() == 1.0F) {animation.getFrame().flip(false,false);}

		batch.begin();
		batch.draw(animation.getFrame(), animation.getCurrentPositionX(), y);
		System.out.println(animation.getCurrentPositionX());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		animation.dispose();
	}

}
