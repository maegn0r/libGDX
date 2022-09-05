package com.mygdx.game;


import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;

public class MyContList implements ContactListener {



    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null){
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("Герой1") && tmpB.equals("Vasya")) {
                GameScreen.bodies.add(b.getBody());
            }
            if (tmpB.equals("Герой1") && tmpA.equals("Vasya")){
                GameScreen.bodies.add(a.getBody());
            }
            if (tmpA.equals("ноги") && tmpB.equals("поверхность")) {
                GameScreen.setPlayerOnGround(true);}
            if (tmpB.equals("ноги") && tmpA.equals("поверхность")){
                GameScreen.setPlayerOnGround(true);}
            }}




    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null){
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
        if (tmpA.equals("ноги") && tmpB.equals("поверхность")) {
            GameScreen.setPlayerOnGround(false);}
        if (tmpB.equals("ноги") && tmpA.equals("поверхность")){
            GameScreen.setPlayerOnGround(false);}
    }}


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }



    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}


