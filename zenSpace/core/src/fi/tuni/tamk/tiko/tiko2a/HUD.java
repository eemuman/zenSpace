/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class HUD implements Screen {

    private Skin skin;
    private TextButton tButton, tButtonJatka, tButtonMenu;
    Stage stg;
    private Table tbl;
    private boolean paused = false;
    private boolean backMenu = false;
    private BundleHandler bundle;
    private Window pause;

    public HUD(zenSpace game) {
        bundle = game.getBundle();

        skin = bundle.getUiSkin();
        tButton = new TextButton("Menu", skin);
        tButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tButton.setVisible(false);
                pause.setVisible(true);
                paused = true;
            }
        });
        tButtonJatka = new TextButton("Jatka peliä", skin);
        tButtonJatka.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tButton.setVisible(true);
                pause.setVisible(false);
                paused = false;
            }
        });
        tButtonMenu = new TextButton("Main Menuun", skin);
        tButtonMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backMenu = true;
                dispose();
            }
        });

        pause = new Window("", skin);
        pause.setMovable(false);
        pause.add(tButtonJatka).expand().center().height(115);
        pause.row();
        pause.add(tButtonMenu).expand().center().height(115);
        pause.pack();
        pause.setBounds((game.getScrnView().getCamera().viewportWidth - 400) / 2.5f, (game.getScrnView().getCamera().viewportHeight - 200) / 2, 400, 300);
        pause.setVisible(false);


        stg = new Stage(game.getScrnView());


        tbl = new Table();
        tbl.add(tButton).top().left().expand().height(115);
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

    public boolean isBackMenu() {
        return backMenu;
    }

    public void setBackMenu() {
        backMenu = false;
        tButton.setVisible(true);
        pause.setVisible(false);
        paused = false;
    }
}
