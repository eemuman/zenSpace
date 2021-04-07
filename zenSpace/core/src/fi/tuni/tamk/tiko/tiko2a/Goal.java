/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Goal implements Screen {
    private zenSpace gme;
    private Image img;
    private Stage stg;

    private TextButton back;
    private TextureAtlas.AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;
    private boolean shouldRender = true;

    private float dynamicUnitScale;

    private BundleHandler bundle;
    private Skin skin;
   // Stage stg;
    private Table tbl;
    private Label lbl;

    private InputMultiplexer inputMultiplexer;

    public Goal(zenSpace game,TextureAtlas bgTexture) {
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);
        gme = game;
        img = gme.generateFade();
        stg.addActor(img);

     //   hud = gme.getHud();
        this.bgTexture = bgTexture.findRegion("goal");
        batch = game.getBatch();
        scrnView = game.getScrnView();
        dynamicUnitScale = scrnView.getWorldHeight() - gme.getwHeight();
        stg = new Stage(scrnView);
        bundle = gme.getBundle();
        skin = bundle.getUiSkin();
        tbl = new Table();
        tbl.setDebug(true);
        tbl.setFillParent(true);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stg);
        Gdx.input.setInputProcessor(inputMultiplexer);

        back = new TextButton("MENU", skin, "TextButtonSmall");
        back.setDebug(true);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gme.setScreen(new newMainMenu(gme));
            }
        });
        tbl.add(back).expand().top().left().width(225).height(dynamicUnitScale);
        tbl.row();
        lbl = new Label("HIENOA!", skin, "white");
        lbl.setDebug(true);
        tbl.add(lbl).top().center().width(260).height(dynamicUnitScale)
                .padBottom(gme.getwHeight() - dynamicUnitScale).padRight(240f);;
        stg.addActor(tbl);

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
        batch.begin();
        batch.draw(bgTexture, 0,0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
        batch.end();
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
        shouldRender = false;
        stg.clear();
        stg.dispose();
    }
}
