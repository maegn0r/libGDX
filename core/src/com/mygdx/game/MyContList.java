package com.mygdx.game;


import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;

public class MyContList implements ContactListener {

    private int counter;
    private int powerJump;

    public boolean isOnGroung() {return counter > 0;};

    public boolean isOnJumper() {return powerJump > 0;};


    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("Герой1") && tmpB.equals("Star")) {
                GameScreen.bodies.add(b.getBody());
            }
            if (tmpB.equals("Герой1") && tmpA.equals("Star")) {
                GameScreen.bodies.add(a.getBody());
            }
            if (tmpA.equals("ноги") && tmpB.equals("поверхность")) {
                counter++;
            }
            if (tmpB.equals("ноги") && tmpA.equals("поверхность")) {
                counter++;
            }
            if (tmpA.equals("ноги") && tmpB.equals("Jumper")) {
                powerJump++;
            }
            if (tmpB.equals("ноги") && tmpA.equals("Jumper")) {
                powerJump++;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
            if (tmpA.equals("ноги") && tmpB.equals("поверхность")) {
                counter--;
            }
            if (tmpB.equals("ноги") && tmpA.equals("поверхность")) {
                counter--;
            }
            if (tmpA.equals("ноги") && tmpB.equals("Jumper")) {
                powerJump--;
            }
            if (tmpB.equals("ноги") && tmpA.equals("Jumper")) {
                powerJump--;
            }

        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}


