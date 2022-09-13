package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Ogre {

    private Animation<TextureRegion> anim;
    private Animation<TextureRegion> idleAnim;
    private TextureAtlas atlas;
    private float time;
    private static float dScale = 2.8f;

    public Ogre(String atlasName, Animation.PlayMode playMode) {
        atlas = new TextureAtlas(atlasName);
        anim = new Animation<TextureRegion>(1 / 10f, atlas.findRegions("Walk"));
        anim.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }
    public void idleOgre() {
        idleAnim = new Animation<TextureRegion>(1 / 20f, atlas.findRegion("Walk",1));
        anim.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void setTime(float time) {
        this.time += time;
    }

    public TextureRegion getFrame() {
        return anim.getKeyFrame(time);
    }

    public void update() {
        time += Gdx.graphics.getDeltaTime();
        anim.setPlayMode(Animation.PlayMode.LOOP);
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
    public Rectangle getRect(OrthographicCamera camera, TextureRegion region) {
        float cx = Gdx.graphics.getWidth()/2 - region.getRegionWidth()/2 / camera.zoom/ dScale;
        float cy = Gdx.graphics.getHeight()/2 - region.getRegionHeight()/2 / camera.zoom/ dScale;
        float cW = region.getRegionWidth() / camera.zoom / dScale;
        float cH = region.getRegionHeight() / camera.zoom / dScale;
        return new Rectangle(cx , cy, cW, cH);
    }

    public void dispose(){
        atlas.dispose();
    }

}
