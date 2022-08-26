package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OgreAnimation {

    private Texture img;
    private Animation<TextureRegion> anim;
    private TextureAtlas atlas;

    private float time;
    private float speed = 100F;
    private float startPositionX;
    private float startPositionY;
    private float currentPositionX;
    private float currentPositionY;
    private float mapEndX;

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

    public void update(float dt) {
        if (!getFrame().isFlipX()) {
            currentPositionX += speed * dt;
            if (getCurrentPositionX() + getFrame().getRegionWidth() >= mapEndX) {
                for (TextureRegion keyFrame : anim.getKeyFrames()) {
                    keyFrame.flip(true, false);
                }
            }
        } else if (getFrame().isFlipX()) {
            currentPositionX -= speed * dt;
            if (getCurrentPositionX() <= 0.0F) {
                for (TextureRegion keyFrame : anim.getKeyFrames()) {
                    keyFrame.flip(true, false);
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), startPositionX + currentPositionX, startPositionY + currentPositionY);
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

    public float getSpeed() {
        return this.speed;
    }

    public float getCurrentPositionX() {
        return this.currentPositionX;
    }

    public float getCurrentPositionY() {
        return this.currentPositionY;
    }

    public void setStartPositionX(float startPositionX) {
        this.startPositionX = startPositionX;
    }

    public void setStartPositionY(float startPositionY) {
        this.startPositionY = startPositionY;
    }

    public void setMapEndX(float mapEndX) {
        this.mapEndX = mapEndX;
    }

    public void dispose() {
        atlas.dispose();
    }

}