package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

public class MyAnimation {

    private Texture img;
    private Animation<TextureRegion> anim;
    private Animation<TextureRegion> idleAnim;
    private TextureAtlas atlas;
    private float time;
    private Rectangle heroRect;
    private static float dScale = 2.8f;


    public MyAnimation(String name, int col, int row, Animation.PlayMode playMode) {
        img = new Texture(name);
        TextureRegion region0 = new TextureRegion(img);
        int xCnt = region0.getRegionWidth() / col;
        int yCnt = region0.getRegionHeight() / row;
        TextureRegion[][] regions0 = region0.split(xCnt, yCnt);
        TextureRegion[] region1 = new TextureRegion[regions0.length * regions0[0].length];
        int count = 0;
        for (int i = 0; i < regions0.length; i++) {
            for (int j = 0; j < regions0[0].length; j++) {
                region1[count++] = regions0[i][j];
            }
        }
        anim = new Animation<TextureRegion>(1 / 20f, region1);
        anim.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }


    public MyAnimation(Animation.PlayMode playMode) {
        atlas = new TextureAtlas("atlas/starAtlas.atlas");
        anim = new Animation<TextureRegion>(1 / 10f, atlas.findRegions("Star"));
        anim.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }


    public void zeroTime() {
        this.time = 0;
    }

    public boolean isAnimationOver() {
        return anim.isAnimationFinished(time);
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        anim.setPlayMode(playMode);
    }

    public Rectangle getHeroRect() {
        return heroRect;
    }

    public void setHeroRect(Rectangle heroRect) {
        this.heroRect = heroRect;
    }

    public void dispose() {
        atlas.dispose();
    }

}
