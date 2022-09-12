package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.NewFont;
import com.mygdx.game.OgreAnimation;
import com.mygdx.game.PhysX;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private int clickCounter;
    private OgreAnimation ogreAnimation;
    private OrthographicCamera camera;
    private final Sound startGameSound;
    private final Music music;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private final int[] bg;
    private final int[] l1;
    private PhysX physX;
    private final NewFont font;
    private Body body;
    public static ArrayList<Body> bodies;
    private int score;
    private  int maxScore;

    public GameScreen(Main game) {
        font = new NewFont(30);
        font.setColor(Color.WHITE);
        bodies = new ArrayList<>();
        this.game = game;
        batch = new SpriteBatch();
        ogreAnimation = new OgreAnimation("atlas/ogrepack.atlas", Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.35f;
//        maxScore = physX.getBodys("roll").size;

        TiledMap map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        startGameSound = Gdx.audio.newSound(Gdx.files.internal("start_game.mp3"));
        startGameSound.play();

        music = Gdx.audio.newMusic(Gdx.files.internal("game_music.mp3"));
        music.setLooping(true);
        music.setVolume(0.03f);
        music.play();

        bg = new int[]{map.getLayers().getIndex("Фон")};
        l1 = new int[]{map.getLayers().getIndex("Слой2"), map.getLayers().getIndex("Слой3")};

        physX = new PhysX();
        RectangleMapObject rectMapObject = (RectangleMapObject) map.getLayers().get("сеттинг").getObjects().get("Герой1");
        ogreAnimation.setHeroRect(rectMapObject.getRectangle());
        body = physX.addObject(rectMapObject);


        Array<RectangleMapObject> objects = map.getLayers().get("объекты").getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < objects.size; i++) {
            physX.addObject(objects.get(i));
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        camera.position.x = body.getPosition().x* physX.PPM;
        camera.position.y = body.getPosition().y* physX.PPM;
        camera.update();

        float STEP = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) body.applyForceToCenter(new Vector2(-0.70f, 0), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) body.applyForceToCenter(new Vector2(0.70f, 0), true);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && physX.getContList().isOnGroung()) {
            {
                body.setGravityScale(-8);
            }
        } else {
            body.setGravityScale(1);
            body.setFixedRotation(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= STEP;
        if (physX.getContList().isOnJumper()){body.setGravityScale(-18);};


        if (Gdx.input.isKeyPressed(Input.Keys.Z)) camera.zoom += 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.A) && camera.zoom > 0) camera.zoom -= 0.01f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.setView(camera);
        mapRenderer.render(bg);
        ogreAnimation.idleOgre();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ogreAnimation.update();
            ogreAnimation.setTime(Gdx.graphics.getDeltaTime());}

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) clickCounter++;
        Gdx.graphics.setTitle("Было сделано " + clickCounter + " левых кликов мышкой");
        Rectangle tmp = ogreAnimation.getRect(camera, ogreAnimation.getFrame());
        ((PolygonShape)body.getFixtureList().get(0).getShape()).setAsBox(tmp.width/2.2F/physX.PPM*camera.zoom, tmp.height/2.6f/ physX.PPM*camera.zoom,
                new Vector2(0,-tmp.height/2/physX.PPM*camera.zoom/100), 0);
        ((PolygonShape)body.getFixtureList().get(1).getShape()).setAsBox(
                tmp.width/3/physX.PPM*camera.zoom,
                tmp.height/12/physX.PPM*camera.zoom,
                new Vector2(0,-tmp.height/2.6F/physX.PPM*camera.zoom),
                0);

        ogreAnimation.setTime(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(ogreAnimation.getFrame(), tmp.x,tmp.y, tmp.width, tmp.height);
        batch.end();

        batch.begin();
        font.render(batch, "Звезд собрано: " + String.valueOf(score), 10, Gdx.graphics.getHeight()-10);
        batch.end();

        mapRenderer.render(l1);

        physX.step();
        physX.debugDraw(camera);
        for (int i = 0; i < bodies.size(); i++) {
            physX.destroyBody(bodies.get(i));
            score++;
        }
        bodies.clear();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        ogreAnimation.dispose();
        startGameSound.dispose();
        music.dispose();
    }

}
