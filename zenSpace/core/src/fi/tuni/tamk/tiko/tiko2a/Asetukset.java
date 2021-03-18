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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Asetukset implements Screen {

    private Stage stg;

    private ExtendViewport scrnView;
    private Skin skin;

    private TextButton btnTakaisin;
    private Table tbl;


    private Slider volSlider;

    private zenSpace gme;

    private Image headerImg;

    private Label lbl;

    public Asetukset(zenSpace game) {
        gme = game;

        headerImg = gme.getHeaderImg();

        this.skin = gme.getSkin();

        tbl = new Table();

        lbl = new Label("VOLYYMI:", skin);

        btnTakaisin = new TextButton("Takaisin", skin);
        volSlider = new Slider(0, 1, 0.05f,false, skin);

        tbl.add(headerImg).expandX();
        tbl.row();
        tbl.add(lbl).width(600).height(150).center().expandX();
        tbl.row();
        tbl.add(volSlider).width(600).height(75).padBottom(75);
        tbl.row();
        tbl.add(btnTakaisin).width(600).height(125).padBottom(75).bottom().expandY();

        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);
        tbl.setFillParent(true);
        stg.addActor(tbl);

        btnTakaisin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                gme.setScreen(new newMainMenu(gme));
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
