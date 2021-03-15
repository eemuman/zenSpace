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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class newMainMenu implements Screen {

    private Pixmap pixmap;
    private zenSpace game;

    private Stage stg;

    private ExtendViewport scrnView;
    private Skin skin;

    private TextButtonStyle textBtnStyle;
    private TextButton btnPela;
    private TextButton btnAsetukset;
    private Table tbl;

    private BitmapFont font, fontH;

    private OrthographicCamera textCam;
    private SpriteBatch batch;


    private Label zenSpace;
    private Label.LabelStyle lblStyle;

    public newMainMenu(zenSpace game) {
        this.game = game;
        textCam = game.getTextCam();
        batch = game.getBatch();
        font = game.getFont();
        fontH = game.getFontH();

        textBtnStyle = new TextButtonStyle();
        skin = new Skin();
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", font);
        skin.add("title", fontH);
        updateBtn();

        lblStyle = new Label.LabelStyle();
        updateLbl();
        zenSpace = new Label("ZENSPACE", skin);



        tbl = new Table();
        tbl.setFillParent(true);


        fontH.getData().setScale(0.5f);
        btnPela = new TextButton("Pelaa", skin);
        btnAsetukset = new TextButton("Asetukset", skin);
        tbl.add(zenSpace).width(65).height(30).padBottom(100).padRight(75);
        tbl.row();
        tbl.defaults().width(250).height(75);
        tbl.add(btnPela).padRight(50);
        tbl.add(btnAsetukset);


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
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act(Gdx.graphics.getDeltaTime());
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
    private void updateLbl() {
        lblStyle.background = skin.newDrawable("white", Color.CLEAR);
        lblStyle.font = skin.getFont("title");
        skin.add("default", lblStyle);
    }
}
