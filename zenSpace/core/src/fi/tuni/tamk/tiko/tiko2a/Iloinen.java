package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Iloinen implements Screen {
    zenSpace game;
    private OrthographicCamera textCam;
    private SpriteBatch batch;
    private BitmapFont font;

    public Iloinen(zenSpace game) {
        this.game = game;
        textCam = game.getTextCam();
        batch = game.getBatch();
        font = game.getFont();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(textCam.combined);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        textCam.update();
        batch.begin();
        font.draw(batch, "ILOINEN", 300,400);
        batch.end();
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

    }
}
