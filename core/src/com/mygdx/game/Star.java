package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Star {
    private Texture img;
    private Animation<TextureRegion> anim;
    private Animation<TextureRegion> idleAnim;
    private TextureAtlas atlas;
    private float time;
    private Rectangle heroRect;
    private static float dScale = 2.8f;

    public Star(String atlasName, Animation.PlayMode playMode) {
        atlas = new TextureAtlas(atlasName);
        anim = new Animation<TextureRegion>(1 / 05f, atlas.findRegions("star"));
        anim.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }
    public void setTime(float time) {
        this.time += time;
    }

    public TextureRegion getFrame() {
        return anim.getKeyFrame(time);
    }
    public void dispose(){
        atlas.dispose();
    }
}
