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
    private float dynamicUnitScale, fadeInTime = 3f;
    private String[] motiStrings;
    private Label lbl;
    private I18NBundle curLangBundle;
    private BundleHandler bundle;
    private Skin skin;
    private Table tbl;




    public MotiText(zenSpace game) {
        tbl = new Table();
        dynamicUnitScale = scrnView.getWorldHeight() - gme.getwHeight();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        scrnView = game.getScrnView();
        skin = bundle.getUiSkin();
        gme = game;
        img = gme.generateFade();
        stg = new Stage(scrnView);
        stg.addActor(img);
        Gdx.input.setInputProcessor(stg);
        motiStrings = curLangBundle.get("motiText").split(",");
        lbl = new Label(motiStrings[gme.getRandEste()],skin);
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

    }
}
