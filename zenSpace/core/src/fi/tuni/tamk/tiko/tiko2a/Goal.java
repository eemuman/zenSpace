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
import com.badlogic.gdx.utils.I18NBundle;
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
    private I18NBundle curLangBundle;
    private Skin skin;
   // Stage stg;
    private Table tbl;
    private Label lbl, lbl1, lbl2;
    private float fadeInTime = 3f;


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
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        skin = bundle.getUiSkin();
        tbl = new Table();
        tbl.setFillParent(true);

        Gdx.input.setInputProcessor(stg);

        back = new TextButton(curLangBundle.get("menu"), skin, "TextButtonSmallWhite");
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gme.setCurLevelInt(1);
                gme.getEste().initseenAlready();
                gme.setScreen(new newMainMenu(gme));
            }
        });
        lbl = new Label(curLangBundle.get("hienoa"), skin, "white");
        lbl1 = new Label(curLangBundle.get("lapipeluu") + gme.prefs.getAmountofCompletions() + curLangBundle.get("kertaa"),skin, "WhiteSmall");
        lbl2 = new Label(gme.prefs.calculateAmount() + "/" + gme.prefs.getAmountOfEste(),skin,"WhiteSmall");
        lbl1.setWrap(true);
        lbl1.setWidth(10f);
        tbl.add(lbl).expand().padTop(250).top();
        tbl.row();
        tbl.add(lbl1).expand().width(375f).padTop(-175f).top();
        tbl.row();
        tbl.add(lbl2).expand();
        tbl.row();
        tbl.add(back).width(300).height(dynamicUnitScale).padBottom(50);
        stg.addActor(tbl);
        stg.addAction(Actions.sequence(Actions.alpha(0),Actions.delay(fadeInTime), Actions.fadeIn(fadeInTime)));
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
        shouldRender = false;
        stg.clear();
        stg.dispose();
    }
}
