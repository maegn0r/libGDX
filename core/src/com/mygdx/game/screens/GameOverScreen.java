package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.NewFont;


public class GameOverScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture img;
    private final Music music;
    private final NewFont font;

    public GameOverScreen (Main game) {
        font = new NewFont(30);
        font.setColor(Color.FIREBRICK);
        this.game = game;
        img = new Texture("gameover_screen.png");
        Gdx.graphics.setWindowedMode(img.getWidth(), img.getHeight());
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("gameover_music.mp3"));
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();

    }

    @Override
    public void show() {
    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.WHITE);

        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        batch.begin();
        if (StageCounter.getStageCounter() < 4){
        font.render(batch, "Игра окончена! \nСпасибо за игру! \nВы остановились на уровне номер " + StageCounter.getStageCounter() + "\nНажмите F4 для выхода из игры \nНажмите F5, чтобы начать заново " ,Gdx.graphics.getWidth()/8 ,Gdx.graphics.getHeight()/1.6F );}
        else {font.render(batch, "Игра окончена! \nСпасибо за игру! \nИгра пройдена! \nНажмите F4 для выхода из игры \nНажмите F5, чтобы начать заново ",Gdx.graphics.getWidth()/8 ,Gdx.graphics.getHeight()/1.6F );}
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.F4)){
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F5)){
            StageCounter.clearStageCounter();
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

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
        this.batch.dispose();
        this.img.dispose();
        this.music.dispose();
        this.font.dispose();
        this.game.dispose();
    }

    public Texture getImg() {
        return img;
    }
}
