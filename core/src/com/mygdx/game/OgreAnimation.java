package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

public class OgreAnimation {

    private Texture img;
    private Animation<TextureRegion> anim;
    private TextureAtlas atlas;
    private float time;
    private Rectangle heroRect;


    public OgreAnimation(String name, int col, int row, Animation.PlayMode playMode) {
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

    public OgreAnimation(String atlasName, Animation.PlayMode playMode) {
        atlas = new TextureAtlas(atlasName);
        anim = new Animation<TextureRegion>(1 / 10f, atlas.findRegions("Walk"));
        anim.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }

    public void update() {
        if (!getFrame().isFlipX()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                for (TextureRegion keyFrame : this.anim.getKeyFrames()) {
                    keyFrame.flip(true, false);
                }
            }
        } else if (getFrame().isFlipX())
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                for (TextureRegion keyFrame : this.anim.getKeyFrames()) {
                    keyFrame.flip(true, false);
                }
            }
    }

    public TextureRegion getFrame() {
        return anim.getKeyFrame(time);
    }

    public void setTime(float time) {
        this.time += time;
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
