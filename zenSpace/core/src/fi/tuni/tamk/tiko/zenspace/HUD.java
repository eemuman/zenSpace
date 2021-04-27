/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;


public class HUD implements Screen {

    private Skin skin;
    private TextButton tButton, tButtonJatka, tButtonMenu;
     Stage stg;
    private Image img;
    private Table tbl;
    private boolean paused = false;
    private BundleHandler bundle;
    private Window pause;
    private zenSpace gme;
    private float dynamicUiScale;
    private I18NBundle curLangBundle;

    public HUD(zenSpace game) {
        gme = game;
        bundle = game.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        img = gme.generateFade();
        img.addAction(Actions.alpha(0));
        stg = new Stage(game.getScrnView());
        dynamicUiScale = game.getScrnView().getWorldHeight() - game.getwHeight();
        stg.addActor(img);
        skin = bundle.getUiSkin();
        tButton = new TextButton(curLangBundle.get("menu"), skin);
        tButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tButton.setVisible(false);
                pause.setVisible(true);
                paused = true;
            }
        });
        tButtonJatka = new TextButton(curLangBundle.get("jatka"), skin);
        tButtonJatka.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tButton.setVisible(true);
                pause.setVisible(false);
                paused = false;
            }
        });
        tButtonMenu = new TextButton(curLangBundle.get("valikko"), skin);
        tButtonMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stg.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        gme.setCurLevelInt(1);
                        setBackMenu();
                        gme.getEste().initseenAlready();
                        gme.setScreen(new newMainMenu(gme));
                    }
                })));
            }
        });

        pause = new Window("", skin);
        pause.setMovable(false);
        pause.add(tButtonJatka).expand().center().height(dynamicUiScale);
        pause.row();
        pause.add(tButtonMenu).expand().center().height(dynamicUiScale);
        pause.pack();
        pause.setBounds((game.getScrnView().getCamera().viewportWidth - 400) / 2.5f, (game.getScrnView().getCamera().viewportHeight - 200) / 2, 400, 400);
        pause.setVisible(false);





        tbl = new Table();
        tbl.add(tButton).top().left().expand().height(dynamicUiScale);
        tbl.setFillParent(true);

        stg.addActor(tbl);
        stg.addActor(pause);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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

    public boolean isPaused() {
        return paused;
    }

    public void updateText() {
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        tButtonJatka.setText(curLangBundle.get("jatka"));
        tButtonMenu.setText(curLangBundle.get("valikko"));
    }

    public void setBackMenu() {
        tButton.setVisible(true);
        pause.setVisible(false);
        paused = false;
    }
}
