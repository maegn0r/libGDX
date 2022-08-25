package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    private final Rectangle mapSize;
    private final ShapeRenderer shapeRenderer;


    public GameScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        ogreAnimation = new OgreAnimation("atlas/ogrepack.atlas", Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TiledMap map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        RectangleMapObject rectMapObject = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("камера");
        camera.position.x = rectMapObject.getRectangle().x;
        camera.position.y = rectMapObject.getRectangle().y;

        rectMapObject = (RectangleMapObject) (map.getLayers().get("объекты").getObjects().get("граница"));
        mapSize = rectMapObject.getRectangle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        float STEP = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mapSize.x < (camera.position.x - 1)) camera.position.x -= STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (mapSize.x + mapSize.width) > (camera.position.x + 1))
            camera.position.x += STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && (mapSize.y + mapSize.height > camera.position.y + 1))
            camera.position.y += STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (mapSize.y < camera.position.y - 1)) camera.position.y -= STEP;

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) camera.zoom += 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.A) && camera.zoom > 0) camera.zoom -= 0.01f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
        ScreenUtils.clear(0, 0, 0, 0);
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        ogreAnimation.setTime(dt);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) clickCounter++;
        Gdx.graphics.setTitle("Было сделано " + clickCounter + " левых кликов мышкой");

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        ogreAnimation.setStartPositionX(mapSize.x);
        ogreAnimation.setStartPositionY(mapSize.y);
        ogreAnimation.setMapEndX(mapSize.width);
        ogreAnimation.render(batch);
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(mapSize.x, mapSize.y, mapSize.width, mapSize.height);
        shapeRenderer.end();

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


    public Rectangle getMapSize() {
        return mapSize;
    }

}
