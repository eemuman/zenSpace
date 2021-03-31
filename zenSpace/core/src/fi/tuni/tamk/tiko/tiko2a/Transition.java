/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Transition implements Screen {

    private int movementSpeed  = 50;

    private float currentX;

    private TextureRegion currentPlayerFrame;
    private TextureAtlas.AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;

    private Player player;
    private String curLevel;
    HUD hud;

    private float stateTime = 0.0f;

    private zenSpace gme;

    public Transition(zenSpace game, TextureAtlas bgTexture) {
        gme = game;
        hud = gme.getHud();
        Gdx.input.setInputProcessor(hud.stg);
        scrnView = game.getScrnView();
        this.curLevel = "st" + game.getCurLevel();
        this.bgTexture = bgTexture.findRegion(curLevel);
        batch = game.getBatch();
        player = new Player(1, 9);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(scrnView.getCamera().combined);
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!hud.isPaused()) {
            stateTime += delta;
            currentX += delta * movementSpeed;
                currentPlayerFrame = player.getRunAnimation().getKeyFrame(stateTime, true);
        }
        if(hud.isBackMenu()) {
            gme.setCurLevelInt(1);
            Gdx.app.log("HERE", "Here??????");
            dispose();
            hud.setBackMenu();
            gme.setScreen(new newMainMenu(gme));
        }
        batch.begin();
        batch.draw(bgTexture, 0,0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
        if(currentPlayerFrame !=null) {
            batch.draw(currentPlayerFrame, currentX, scrnView.getCamera().viewportHeight / 4, scrnView.getCamera().viewportWidth / 2.5f, scrnView.getCamera().viewportHeight / 2.5f);
        }
        batch.end();
        hud.render(delta);
        checkPlayerPos();

    }

    @Override
    public void resize(int width, int height) {
        scrnView.update(width, height, true);
        scrnView.getCamera().update();
    }

    private void checkPlayerPos() {
        if(currentX >= scrnView.getCamera().viewportWidth / 1.8f) {
            dispose();
            gme.setScreen(new Piirto(gme));
        }
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
