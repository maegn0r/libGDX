package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.OgreAnimation;

public class GameScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private int clickCounter;
    private OgreAnimation ogreAnimation;
    private OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ShapeRenderer shapeRenderer;
    private final int[] bg;
    private final int[] l1;
    private PhysX physX;
    private Body body;



    public GameScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        ogreAnimation = new OgreAnimation("atlas/ogrepack.atlas", Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.67f;

        TiledMap map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

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

//        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
//            camera.position.set(ogreAnimation.getCurrentPositionX(), ogreAnimation.getCurrentPositionY(), 0);
//        }

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        camera.update();

        float STEP = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) body.applyForceToCenter(new Vector2(10000,0),true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= STEP;

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) camera.zoom += 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.A) && camera.zoom > 0) camera.zoom -= 0.01f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        ogreAnimation.setTime(dt);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) clickCounter++;
        Gdx.graphics.setTitle("Было сделано " + clickCounter + " левых кликов мышкой");


//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.SKY);
//        for (int i = 0; i < objects.size; i++) {
//            Rectangle mapSize = objects.get(i).getRectangle();
//            shapeRenderer.rect(mapSize.x, mapSize.y, mapSize.width, mapSize.height);
//        }
//        shapeRenderer.end();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        ogreAnimation.setHeroX(body.getPosition().x - ogreAnimation.getHeroRect().width/2);
        ogreAnimation.setHeroY(body.getPosition().y - ogreAnimation.getHeroRect().height/2);
        ogreAnimation.render(batch);
        batch.end();

        mapRenderer.render(l1);

        physX.step();
        physX.debugDraw(camera);

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
    }

    public void update(float dt) {
        ogreAnimation.update(dt);
    }


//    public Rectangle getMapSize() {
//        return mapSize;
//    }

}
