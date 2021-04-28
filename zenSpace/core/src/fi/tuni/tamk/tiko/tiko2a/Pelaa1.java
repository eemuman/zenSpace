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
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Pelaa1 implements Screen {

    private zenSpace gme;
    private Skin skin;
    private ExtendViewport scrnView;
    private Stage stg;
    private Label header;
    private Table tbl,tblBottom;

    private TextButton btnTaka, btnEte;
    private ButtonGroup buttons;
    private String[] textStrings;
    private TextButton[] btns;
    private BundleHandler bundle;
    private I18NBundle curLangBundle;



    public Pelaa1(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());

        textStrings = curLangBundle.get("firstStrings").split(",");
        btns = new TextButton[textStrings.length];
        skin = bundle.getUiSkin();
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);

        createBtns();
        header = new Label(curLangBundle.get("miltatuntuu"), skin);
        btnTaka = new TextButton(curLangBundle.get("takaisin"), skin, "TextButtonSmall");
        btnEte = new TextButton(curLangBundle.get("seuraava"), skin, "TextButtonSmall");

        btnTaka.addListener(new ChangeListener() {
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

        btnEte.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(buttons.getChecked() != null) {
                    stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            gme.setScreen(new Pelaa2(gme, buttons.getCheckedIndex()));
                        }
                    })));
                }
            }
        });

        tbl = new Table();
        tblBottom = new Table();
        tbl.add(header).expand().padBottom(25).padTop(75);
        addBtnsTable();
        tblBottom.defaults().height(75).width(200).padBottom(5f);;
        tblBottom.add(btnTaka).left().bottom().expand().padLeft(7f);
        tblBottom.add(btnEte).right().bottom().expand().padRight(7f);
        tbl.setFillParent(true);
        tblBottom.setFillParent(true);

        stg.addActor(tbl);
        stg.addActor(tblBottom);
        stg.addAction(Actions.alpha(0));
        stg.addAction(Actions.fadeIn(gme.getFadeIn()));

        buttons = new ButtonGroup(btns);
        addBtnsBtngroup();
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

    private void createBtns() {
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new TextButton(textStrings[i], skin, "toggle");
        }
    }

    private void addBtnsTable() {
        for (int i = 0; i < btns.length; i++) {
            tbl.row();
            if(i == btns.length -1) {
                tbl.add(btns[i]).width(gme.getwWidth() - 15f).height(75).padBottom(175);
            } else {
                tbl.add(btns[i]).width(gme.getwWidth() - 15f).height(75).padBottom(10);
            }
        }
    }
    private void addBtnsBtngroup() {
        for (int i = 0; i < btns.length; i++) {
            buttons.add(btns[i]);
        }
    }
}
