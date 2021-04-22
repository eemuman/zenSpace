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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MotiText implements Screen {
    private Image img;
    private Stage stg;
    private zenSpace gme;
    private ExtendViewport scrnView;
    private float time, fadeInTime = 1f;
    private String[] motiStrings;
    private Label lbl;
    private I18NBundle curLangBundle;
    private BundleHandler bundle;
    private Skin skin;
    private Table tbl;
    boolean shouldUpdate = true;




    public MotiText(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        scrnView = game.getScrnView();
        skin = bundle.getUiSkin();

        tbl = new Table();
        img = gme.generateFade();
        stg = new Stage(scrnView);
        tbl = new Table();
        tbl.setFillParent(true);
        stg.addActor(img);
        Gdx.input.setInputProcessor(stg);
        motiStrings = curLangBundle.get("motiText").split(",");
        lbl = new Label(motiStrings[gme.getCurEsteInt()],skin,"WhiteSmall");
        lbl.setWrap(true);
        lbl.setWidth(10f);
        tbl.add(lbl).width(375f);
        stg.addActor(tbl);
        img.addAction(Actions.fadeOut(gme.getFadeIn()));
        stg.addAction(Actions.sequence(Actions.alpha(0),Actions.delay(fadeInTime), Actions.fadeIn(fadeInTime)));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act(delta);
        stg.draw();
        update();
    }

    @Override
    public void resize(int width, int height) {
        stg.getViewport().update(width, height, true);
        stg.getCamera().update();
    }

    private void update() {
        time += Gdx.graphics.getDeltaTime();
        if(time >= 5f) {
            changeLevel();
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

    private void changeLevel() {
        gme.prefs.setAndCheckEste(gme.getCurEsteInt());
        Gdx.app.log("CURLEVEL", String.valueOf(gme.getCurLevel()));
        if (gme.getCurLevel() == 3) {
            stg.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                @Override
                public void run() {
                    gme.prefs.setAndCheckBack(gme.getCurBackground());
                    gme.prefs.setAmountofCompletions();
                    //  dispose();
                    gme.setScreen(new Goal(gme, gme.getBundle().getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                }
            })));
        } else {
            stg.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                @Override
                public void run() {
                    if(shouldUpdate) {
                        gme.setCurLevelInt(gme.getCurLevel() + 1);
                        shouldUpdate = false;
                    }
                    Gdx.app.log("VALUEFO", String.valueOf(gme.getCurLevel()));
                    gme.getEste().randomizeEste();
                    //  dispose();
                    gme.setScreen(new Transition(gme, gme.getBundle().getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                }
            })));
        }
    }
}
