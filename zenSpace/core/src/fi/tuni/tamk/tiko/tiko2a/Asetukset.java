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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Asetukset implements Screen {

    private Stage stg;
    private ExtendViewport scrnView;
    private Skin skin;
    private TextButton btnTakaisin;
    private Table tbl;
    private Slider volSlider;
    private zenSpace gme;
    private BundleHandler bundle;
    private I18NBundle curLangBundle;
    private Image headerImg;
    private Label lbl;
    private boolean isChanged;


    public Asetukset(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        headerImg = new Image(bundle.getUiAtlas().findRegion("Cloud_logohdpi"));
        this.skin = bundle.getUiSkin();
        tbl = new Table();
        lbl = new Label(curLangBundle.get("volyymi")+(int)gme.prefs.getVolume(), skin);
        btnTakaisin = new TextButton(curLangBundle.get("takaisin"), skin);
        volSlider = new Slider(0, 100, 3f,false, skin);
        volSlider.setValue(gme.prefs.getVolume());
        tbl.add(headerImg).expandX();
        tbl.row();
        tbl.add(lbl).width(400).height(150).center().expandX();
        tbl.row();
        tbl.add(volSlider).width(400).height(75).padBottom(75);
        tbl.row();
        tbl.add(btnTakaisin).width(400).height(100).padBottom(75).bottom().expandY();
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);
        tbl.setFillParent(true);
        stg.addActor(tbl);
        stg.addAction(Actions.alpha(0));
        stg.addAction(Actions.fadeIn(gme.getFadeIn()));
        btnTakaisin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {

                        gme.setScreen(new newMainMenu(gme));
                    }
                })));
            }
        });
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act(Gdx.graphics.getDeltaTime());
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

    }

    private void update() {
        if(volSlider.isDragging()) {
            lbl.setText(curLangBundle.get("volyymi")+(int) volSlider.getValue());
            isChanged = true;
        }
        if(!volSlider.isDragging() && isChanged) {
            gme.prefs.setVolume((int) volSlider.getValue());
            Gdx.app.log("HOWMANY", "HERE");
            lbl.setText(curLangBundle.get("volyymi")+(int)gme.prefs.getVolume());
            isChanged = false;
        }
    }
}
