/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Transition implements Screen {

    private int movementSpeed  = 50;

    private float currentX;

    private TextureRegion currentPlayerFrame;

    private AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;

    private Player player;
    private String curLevel;
    private Stage stg;
    HUD hud;
    private Image img;

    private boolean shouldRender = true;

    private float stateTime = 0.0f;

    private zenSpace gme;

    public Transition(zenSpace game, TextureAtlas bgTexture) {
        gme = game;
        hud = gme.getHud();
        Gdx.app.log("HERE", "HERET");
        img = gme.generateFade();

        Gdx.input.setInputProcessor(hud.stg);
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);

        stg.addActor(img);
        this.curLevel = "st" + game.getCurLevel();
        this.bgTexture = bgTexture.findRegion(curLevel);
        batch = game.getBatch();
        player = new Player(1, 9, gme.getBundle());

        img.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(gme.getFadeIn())));
            //   stg.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(scrnView.getCamera().combined);
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!hud.isPaused() && shouldRender) {
            stateTime += delta;
            currentX += delta * movementSpeed;
            currentPlayerFrame = player.getRunAnimation().getKeyFrame(stateTime, true);
        }
        if (hud.isBackMenu()) {
            //   dispose();
        }
        batch.begin();
        if(bgTexture != null)
        batch.draw(bgTexture, 0, 0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
        batch.draw(gme.getEste().getTexture(), scrnView.getCamera().viewportWidth / 1.3f, scrnView.getCamera().viewportHeight / 4, scrnView.getCamera().viewportWidth / 5f, scrnView.getCamera().viewportHeight / 5f);
        if (currentPlayerFrame != null) {
            batch.draw(currentPlayerFrame, currentX, scrnView.getCamera().viewportHeight / 4, scrnView.getCamera().viewportWidth / 4f, scrnView.getCamera().viewportHeight / 4f);
        }
        batch.end();
        hud.render(delta);
        checkPlayerPos();

        stg.act(delta);
        stg.draw();
    }


    @Override
    public void resize(int width, int height) {
        scrnView.update(width, height, true);
        scrnView.getCamera().update();
        stg.getViewport().update(width, height, true);
        stg.getCamera().update();
    }

    private void checkPlayerPos() {
        if(currentX >= scrnView.getCamera().viewportWidth / 1.8f && shouldRender) {
            shouldRender = false;
            img.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.run(new Runnable() {
                @Override
                public void run() {
                    Gdx.app.log("ALPHA", String.valueOf(stg.getBatch().getColor().a));
                        gme.getEste().setBooleans(false, false);
                        gme.setScreen(new Piirto(gme, bgTexture));

                }
            })));
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
    stg.clear();
    stg.dispose();
    }
}
