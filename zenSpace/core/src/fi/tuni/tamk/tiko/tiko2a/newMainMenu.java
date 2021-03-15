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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class newMainMenu implements Screen {

    private Pixmap pixmap;
    private zenSpace game;

    private Stage stg;

    private ScreenViewport scrnView;
    private Skin skin;

    private TextButtonStyle textBtnStyle;
    private TextButton btnPela;
    private TextButton btnAsetukset;
    private Table tbl;

    private BitmapFont font;

    public newMainMenu(zenSpace game) {
        this.game = game;

        font = game.getFont();

        tbl = new Table();
        tbl.setFillParent(true);


        textBtnStyle = new TextButtonStyle();
        skin = new Skin();
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", game.getFont());
        updateBtn();
        btnPela = new TextButton("Pelaa", skin);
        btnAsetukset = new TextButton("Asetukset", skin);
        tbl.add(btnPela).width(250).spaceBottom(10);
        tbl.row();
        tbl.add(btnAsetukset).width(250);
        tbl.center();


        scrnView = game.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);
        stg.addActor(tbl);

        btnPela.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        stg.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stg.draw();
    }

    @Override
    public void resize(int width, int height) {
        stg.getViewport().update(width, height, true);
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


    private void updateBtn() {
        textBtnStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textBtnStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textBtnStyle.checked = skin.newDrawable("white", Color.LIGHT_GRAY);
        textBtnStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textBtnStyle.font = skin.getFont("default");
        skin.add("default", textBtnStyle);
    }
}
