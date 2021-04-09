/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;



public class Resultscreen extends InputAdapter implements Screen {

//    HUD hud;
    private zenSpace gme;
    private ExtendViewport scrnView;
    private SpriteBatch batch;
    private InputMultiplexer inputMultiplexer;
    private AtlasRegion bgText;
    private Image img;
    private Stage stg;
    private boolean touched = false;
    private boolean shouldRender = true;

    public Resultscreen(zenSpace game, AtlasRegion bgTexture) {
        bgText = bgTexture;
        gme = game;
        img = gme.generateFade();
    //    hud = gme.getHud();
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);
        stg.addActor(img);
        inputMultiplexer = new InputMultiplexer();
       // inputMultiplexer.addProcessor(hud.stg);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        batch = game.getBatch();
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
        /*
        if(hud.isBackMenu()) {
            gme.setCurLevelInt(1);
            dispose();
            hud.setBackMenu();
            gme.setScreen(new newMainMenu(gme));
        }

         */
            batch.begin();
            batch.draw(bgText, 0, 0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
            batch.draw(gme.getEste().getTexture(), 0, 0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
            batch.end();

            //   hud.render(delta);

        }
        stg.act(delta);
        stg.draw();
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
        stg.clear();
        stg.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(touched) {
            if (gme.getCurLevel() == 3) {
                stg.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        shouldRender = false;
                        gme.prefs.setAmountofCompletions();
                      //  dispose();
                        gme.setScreen(new Goal(gme, gme.getBundle().getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                    }
                })));
            } else {
                stg.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        shouldRender = false;
                        gme.setCurLevelInt(gme.getCurLevel() + 1);
                        gme.getEste().randomizeEste();
                      //  dispose();
                        gme.setScreen(new Transition(gme, gme.getBundle().getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                    }
                })));

            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touched = true;
        return false;
    }
}
