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
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Goal implements Screen {
    private zenSpace gme;

    private TextureAtlas.AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;

    private HUD hud;

    public Goal(zenSpace game,TextureAtlas bgTexture) {
        gme = game;
        hud = gme.getHud();
        this.bgTexture = bgTexture.findRegion("goal");
        Gdx.input.setInputProcessor(hud.stg);
        batch = game.getBatch();
        scrnView = game.getScrnView();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(scrnView.getCamera().combined);
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(hud.isBackMenu()) {
            gme.setCurLevelInt(1);
            dispose();
            hud.setBackMenu();
            gme.setScreen(new newMainMenu(gme));
        }
        batch.begin();
        batch.draw(bgTexture, 0,0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
        batch.end();
        hud.render(delta);
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
