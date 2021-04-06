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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Goal implements Screen {
    private zenSpace gme;
    private Image img;
    private Stage stg;
    private TextureAtlas.AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;
    private boolean shouldRender = true;

    private HUD hud;

    public Goal(zenSpace game,TextureAtlas bgTexture) {
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);
        gme = game;
        img = gme.getFadeImg();
        stg.addActor(img);

        hud = gme.getHud();
        this.bgTexture = bgTexture.findRegion("goal");
        Gdx.input.setInputProcessor(hud.stg);
        batch = game.getBatch();
        stg.addAction(Actions.alpha(1));
        stg.addAction(Actions.fadeOut(gme.getFadeIn()));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(scrnView.getCamera().combined);
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(shouldRender) {
            if (hud.isBackMenu()) {
          //      dispose();
            }
            batch.begin();
            batch.draw(bgTexture, 0, 0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
            batch.end();
            hud.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        stg.getViewport().update(width, height, true);
        stg.getCamera().update();
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
        shouldRender = false;
        stg.clear();
        stg.dispose();
    }
}
