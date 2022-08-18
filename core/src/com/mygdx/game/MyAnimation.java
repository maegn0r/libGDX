package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {

    private Texture img;

    private Animation<TextureRegion> anim;

    private float time;
    private float speed = 2;
    private float startPositionX = 0;
    private float currentPosition = 0;



    public MyAnimation (String name, int col, int row, Animation.PlayMode playMode){
        img = new Texture(name);
        TextureRegion region0 = new TextureRegion(img);
        int xCnt = region0.getRegionWidth() / col;
        int yCnt = region0.getRegionHeight() / row;
        TextureRegion[][] regions0 = region0.split(xCnt, yCnt);
        TextureRegion[] region1 = new TextureRegion[regions0.length*regions0[0].length];
        int count = 0;
        for (int i = 0; i < regions0.length; i++) {
            for (int j = 0; j < regions0[0].length; j++) {
                region1[count++] = regions0[i][j];
            }
        }

        anim = new Animation<TextureRegion>(1/15f, region1);
        anim.setPlayMode(playMode);

        time += Gdx.graphics.getDeltaTime();

    }

    public void moveRight(){
       currentPosition = startPositionX += speed;
    }
    public void moveLeft(){
        currentPosition = startPositionX -= speed;
    }

    public TextureRegion getFrame() {return anim.getKeyFrame(time);}
    public void setTime(float time) {this.time += time;}
    public void zeroTime(){this.time = 0;}
    public boolean isAnimationOver() {return anim.isAnimationFinished(time);}
    public void setPlayMode(Animation.PlayMode playMode) {anim.setPlayMode(playMode);}
    public float getSpeed () {return this.speed;}
    public float getStartPositionX() {return this.startPositionX;}
    public float getCurrentPositionX() {return this.currentPosition;}


    public void dispose() {
        img.dispose();
    }


}
