package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;

public class MyContList implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Fixture c = contact.getFixtureB();
        Fixture d = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null){
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
            String tmpC = (String) c.getUserData();
            String tmpD = (String) d.getUserData();
            if (tmpA.equals("Герой1") && tmpB.equals("Vasya")) {
                GameScreen.bodies.add(b.getBody());
            }
            if (tmpB.equals("Герой1") && tmpA.equals("Vasya")){
                GameScreen.bodies.add(a.getBody());
            }
            if ((tmpC.equals("ноги") && tmpD.equals("поверхность"))) {
                GameScreen.youGetMaxJumpHeight = true;
            } else {GameScreen.youGetMaxJumpHeight = false;}
            if ((tmpD.equals("ноги") && tmpC.equals("поверхность"))){
                GameScreen.youGetMaxJumpHeight = true;
            } else {GameScreen.youGetMaxJumpHeight = false;}
        }



    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
