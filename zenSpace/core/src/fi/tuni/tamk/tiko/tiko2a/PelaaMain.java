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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class PelaaMain implements Screen {

    private String firstChoice, secondChoice, firstPart, secondPart;
    private zenSpace gme;
    private Skin skin;
    private Label header;
    private ExtendViewport scrnView;
    private Stage stg;
    private Table tbl;
    private TextButton btnTaka, btnEtu;
    private BundleHandler bundle;
    private I18NBundle curLangBundle;
    private String[] firstStrings, secondStrings;
    private int backGroundChoice;

    public PelaaMain(final zenSpace game, int first, int second) {
        gme = game;
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        firstStrings = curLangBundle.get("firstStrings").split(",");
        secondStrings = curLangBundle.get("secondStrings").split(",");
        firstChoice = firstStrings[first];
        backGroundChoice = second;
        secondChoice = secondStrings[second];
        selectParts(first, second);


        skin = bundle.getUiSkin();
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);



        header = new Label(firstPart + "\n" + firstChoice + "\n" + secondPart  + "\n" +  secondChoice +"\nAloitetaanko?", skin);
        header.setWrap(true);
        btnTaka = new TextButton(curLangBundle.get("ei"), skin, "TextButtonSmall");
        btnEtu = new TextButton(curLangBundle.get("Kylla"), skin, "TextButtonSmall");

        btnTaka.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                      //  dispose();
                        gme.setScreen(new Pelaa1(gme));
                    }
                })));
            }
        });

        btnEtu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                             //   dispose();
                                selectBackGround();
                                gme.getEste().randomizeEste();
                                gme.setScreen(new Transition(gme, bundle.getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                            }
                        })));
                /*
                dispose();
                selectBackGround();
                gme.getEste().randomizeEste();
                gme.setScreen(new Transition(gme, bundle.getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                 */
            }
        });


        tbl = new Table();

        tbl.add(header).expand();
        tbl.row();
        tbl.add(btnTaka).left().bottom().height(75).width(200);
        tbl.add(btnEtu).right().bottom().height(75).width(200);
        tbl.setFillParent(true);
        stg.addAction(Actions.alpha(0));
        stg.addAction(Actions.fadeIn(gme.getFadeIn()));

        stg.addActor(tbl);
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
        stg.dispose();
    }
    private void selectBackGround() {

        if(backGroundChoice <= 2) {
            gme.setCurBackground(0);
        } else if(backGroundChoice >2 && backGroundChoice <=5) {
            gme.setCurBackground(1);
        } else if(backGroundChoice > 5) {
            gme.setCurBackground(2);
        }
    }
    private void selectParts(int first, int second) {
        if(first > 2) {
            firstPart = curLangBundle.get("firstPart1");
        } else {
            firstPart = curLangBundle.get("firstPart2");
        }
        if(second > 3) {
            secondPart = curLangBundle.get("secondPart1");
        } else {
            secondPart = curLangBundle.get("secondPart2");
        }
    }
}
