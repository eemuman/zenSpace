/**
 * This file was created by:
 * @author Eemil V.
 * Edited by:
 * @author Petr H.
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;



public class Resultscreen extends InputAdapter implements Screen {

    private zenSpace gme;
    private ExtendViewport scrnView;
    private SpriteBatch batch;
    private InputMultiplexer inputMultiplexer;
    private Image img;
    private Stage stg;
    private boolean touched = false, shouldUpdate = true;
    private float time, fadeInTime = 1f;
    private Skin skin;
    private Table tbl;
    private Label lbl;
    private BundleHandler bundle;
    private String[] motiStrings;
    private I18NBundle curLangBundle;

    public Resultscreen(zenSpace game) {
        gme = game;
        img = gme.generateFade();
        bundle = gme.getBundle();
        skin = bundle.getUiSkin();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        tbl = new Table();
        tbl.setSize(480f, 240f);
        tbl.setPosition(0f, 400f);
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        batch = game.getBatch();
        motiStrings = curLangBundle.get("motiText").split(",");
        lbl = new Label(motiStrings[gme.getCurEsteInt()],skin,"WhiteSmall");
        lbl.setWrap(true);
        lbl.setWidth(10f);
        lbl.setFontScale(1.25f);
        lbl.setAlignment(Align.center);
        tbl.add(lbl).width(360f);
        stg.addActor(tbl);
        stg.addActor(img);
        tbl.addAction(Actions.alpha(0));
        img.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(gme.getFadeIn())));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(scrnView.getCamera().combined);
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setColor(1, 1, 1, 1);
        if(shouldUpdate) {
            batch.begin();
            batch.draw(gme.getEste().getTexture(), 0, 0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
            batch.end();
        }
        stg.act(delta);
        stg.draw();
        update();
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
            changeLevel();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touched = true;
        return false;
    }

    private void update() {
        time += Gdx.graphics.getDeltaTime();
        if(time >= 1f) {
            tbl.addAction(Actions.fadeIn(fadeInTime));
        }
        if(time >= 4f) {
            tbl.addAction(Actions.fadeOut(gme.getFadeIn()));
            changeLevel();
        }
    }

    private void changeLevel() {
        gme.prefs.setAndCheckEste(gme.getCurEsteInt());
        if (gme.getCurLevel() == 3) {
            img.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                @Override
                public void run() {
                    gme.prefs.setAndCheckBack(gme.getCurBackground());
                    gme.prefs.setAmountofCompletions();
                    gme.setScreen(new Goal(gme, gme.getBundle().getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                }
            })));
        } else {
            img.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                @Override
                public void run() {
                    if(shouldUpdate) {
                        gme.setCurLevelInt(gme.getCurLevel() + 1);
                        shouldUpdate = false;
                    }
                    gme.getEste().randomizeEste();
                    gme.setScreen(new Transition(gme, gme.getBundle().getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                }
            })));
        }
    }
}
