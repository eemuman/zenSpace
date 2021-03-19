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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;



public class newMainMenu implements Screen {

    private Stage stg;

    private ExtendViewport scrnView;
    private Skin skin;

    private TextButton btnPela;
    private TextButton btnAsetukset;
    private TextButton btnExit;
    private Table tbl;

    private zenSpace gme;


    private Image headerImg;

    public newMainMenu(final zenSpace game) {

        gme = game;



        headerImg = gme.getHeaderImg();
        skin = gme.getSkin();

        tbl = new Table();
        btnPela = new TextButton("Pelaa", skin);
        btnAsetukset = new TextButton("Asetukset", skin);
        btnExit = new TextButton("Sammuta Peli", skin);
        tbl.add(headerImg).expand();
        tbl.row();
        tbl.add(btnPela).width(325).height(100).bottom().padBottom(75);
        tbl.row();
        tbl.add(btnAsetukset).width(325).height(100).bottom().padBottom(75);
        tbl.row();
        tbl.add(btnExit).width(325).height(100).bottom().padBottom(75);

        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);
        tbl.setFillParent(true);
        stg.addActor(tbl);



        btnPela.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                gme.setScreen(new Pelaa1(gme));
            }
        });
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        btnAsetukset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                gme.setScreen(new Asetukset(gme));
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
