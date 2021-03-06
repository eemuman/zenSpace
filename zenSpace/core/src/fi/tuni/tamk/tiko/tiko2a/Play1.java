/**
 * This file was created by:
 * @author Eemil V.
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

/**
 * This class is the screen where the player chooses how (s)he feels like. Made like every other screen like this {@link Stage}, {@link Table}, etc. The choice that player makes here changes the background set that is seen when playing.
 */
public class Play1 implements Screen {

    private zenSpace gme;
    private Skin skin;
    private ExtendViewport scrnView;
    private Stage stg;
    private Label header;
    private Table tbl,tblBottom;

    private TextButton btnTaka, btnEte;
    private ButtonGroup<TextButton> buttons;
    private String[] textStrings;
    private TextButton[] btns;
    private BundleHandler bundle;
    private I18NBundle curLangBundle;



    /**
     * This constructor creates the layout, uses the {@link BundleHandler} to get the language that is used and the UISkin for the buttons.
     * Buttons are made as {@link ButtonGroup}where the player can only choose one of the feelings and if other is chosen, the old one goes back to not chosen. TextStrings generates the array by splitting the words with ",".
     * The player also has to choose one to continue here.
     * @param game The main game object
     */
    public Play1(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());

        textStrings = curLangBundle.get("firstStrings").split(",");
        btns = new TextButton[textStrings.length];
        skin = bundle.getUiSkin();
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        Gdx.input.setInputProcessor(stg);

        createBtns();  //Generate the buttons with their respective texts using a for-loop
        header = new Label(curLangBundle.get("miltatuntuu"), skin);
        btnTaka = new TextButton(curLangBundle.get("takaisin"), skin, "TextButtonSmall");
        btnEte = new TextButton(curLangBundle.get("seuraava"), skin, "TextButtonSmall");

        btnTaka.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //Back-button to go back to Main Menu
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        gme.sounds.playPlopSound();
                        gme.setScreen(new newMainMenu(gme));
                    }
                })));
            }
        });

        btnEte.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { // Next-button, only usable if one of the ButtonGroup-buttons is checked.
                if(buttons.getChecked() != null) {
                    stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            gme.sounds.playPlopSound();
                            gme.setScreen(new Play2(gme, buttons.getCheckedIndex())); //We send the ButtonGroup-button that is checked as integer for later use.
                        }
                    })));
                }
            }
        });

        tbl = new Table();
        tblBottom = new Table();
        tbl.add(header).expand().padBottom(25).padTop(75);
        addBtnsTable(); //Add the buttons to the Table-layout using For-loop.
        tblBottom.defaults().height(75).width(200).padBottom(5f);;
        tblBottom.add(btnTaka).left().bottom().expand().padLeft(7f);
        tblBottom.add(btnEte).right().bottom().expand().padRight(7f);
        tbl.setFillParent(true);
        tblBottom.setFillParent(true);

        stg.addActor(tbl);
        stg.addActor(tblBottom);
        stg.addAction(Actions.alpha(0));
        stg.addAction(Actions.fadeIn(gme.getFadeIn()));

        //Add the generated buttons to the ButtonGroup
        buttons = new ButtonGroup<>();
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
    dispose();
    }

    @Override
    public void dispose() {
        stg.dispose();
    }

    /**
     * This method is used to generate the buttons using the String array textStrings
     */
     void createBtns() {
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new TextButton(textStrings[i], skin, "toggle");
        }
    }


    /**
     * This method is used to add the buttons to the {@link Table} layout. The last button has added padding at the bottom to have some extra space between the last button and the next/back buttons.
     */
     void addBtnsTable() {
        for (int i = 0; i < btns.length; i++) {
            tbl.row();
            if(i == btns.length -1) {
                tbl.add(btns[i]).width(gme.getwWidth() - 15f).height(75).padBottom(175);
            } else {
                tbl.add(btns[i]).width(gme.getwWidth() - 15f).height(75).padBottom(10);
            }
        }
    }

    /**
     * This method is used to add the buttons to the {@link ButtonGroup}.
     */
     void addBtnsBtngroup() {
        for (int i = 0; i < btns.length; i++) {
            buttons.add(btns[i]);
        }
    }
}
