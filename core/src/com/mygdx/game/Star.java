package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Star {
    private Animation<TextureRegion> anim;
    private TextureAtlas atlas;
    private float time;

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
