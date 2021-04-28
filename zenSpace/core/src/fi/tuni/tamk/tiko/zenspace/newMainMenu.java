/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


/**
 * This is the MAIN MENU class, we load into this when the game starts. This class uses {@link Stage},{@link Table} and 'actors' (Images, labels, buttons, etc.) to construct the main menu screen.
 * Changing the language is done through {@link Stack} where the flagButtons are on top of each other with the one not in use visible and the other hidden.
 */
public class newMainMenu implements Screen {

    private Stage stg;

    private ExtendViewport scrnView;
    private Skin skin;

    private TextButton btnPela;
    private TextButton btnAsetukset;
    private TextButton btnExit;
    private Table tbl;

    private zenSpace gme;

    private BundleHandler bundle;
    private I18NBundle curLangBundle;


    private Image headerImg;
    private Image sunImg;
    private Button flagBtnFI;
    private Button flagBtnEN;
    private Stack flagStack;


    /**
     *The contstructor for the main menu. First we get the language used, the UISkin.atlas/Json (See: {@link BundleHandler}) and use those to construct all the 'actors' (Buttons, images, flagStack, etc.)
     * When everything is constructed, we add them to the {@link Table} layout and manipulate their position to what our designers wanted. Then everything is added to the {@link Stage} for Input events and handling of the viewport
     * @param game The game object
     */
    public newMainMenu(final zenSpace game) {
        gme = game;
        scrnView = gme.getScrnView();
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());



        headerImg = new Image(bundle.getUiAtlas().findRegion("Cloud_logohdpi"));
        sunImg = new Image(bundle.getUiAtlas().findRegion("Sunhdpi"));
        skin = bundle.getUiSkin();

        tbl = new Table();

        //Generate the Flagstack, where clicking the flag changes the language
        flagBtnFI = new Button(skin, "FlagbuttonFI");
        flagBtnEN = new Button(skin, "FlagbuttonEN");
        flagStack = new Stack();
        flagStack.add(flagBtnFI);
        flagStack.add(flagBtnEN);

        stg = new Stage(scrnView);
        //Get the text for the buttons in the current language.
        btnPela = new TextButton(curLangBundle.get("pelaa"), skin, "Play");
        btnAsetukset = new TextButton(curLangBundle.get("asetukset"), skin);
        btnExit = new TextButton(curLangBundle.get("sammuta"), skin);
        //Construct the table layout
        tbl.row();
        tbl.add(headerImg).expandY().height(250).width(350).top().padTop(15).padBottom(-5);
        tbl.row();
        tbl.defaults().expandY().width(325).height(115).top();
        tbl.add(btnPela).padBottom(20);
        tbl.row();
        tbl.add(btnAsetukset).padBottom(-20);
        tbl.row();
        tbl.add(btnExit).padBottom(-40);
        tbl.row();
        tbl.add(sunImg).width(425).height(425).bottom().padBottom(-250);
        tbl.row();
        tbl.add(flagStack).width(145).height(115).top().right().padTop(-155).padRight(-15);





        Gdx.input.setInputProcessor(stg);
        tbl.setFillParent(true);
        stg.addActor(tbl);

        stg.addAction(Actions.alpha(0));
        stg.addAction(Actions.fadeIn(gme.getFadeIn()));
        updateButtons(); //Add the text to the buttons


        btnPela.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //This is the Play-button
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        gme.setScreen(new Pelaa1(gme));
                    }
                })));
            }
        });
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //This is the Exit-button
                Gdx.app.exit();
            }
        });

        btnAsetukset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { // This is the Settings-button
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                            //    dispose();
                                gme.setScreen(new Asetukset(gme));
                            }
                        })));
            }
        });

        //When either of the flag buttons is pressed, run these two methods to change which one is visible and which language is in use.
        flagBtnFI.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateTexts();
                updateButtons();
            }
        });
        flagBtnEN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateTexts();
                updateButtons();
            }
        });

    }


    /**
     * This method is used to change WHICH of the flags is currently visible. If the language is set to Finnish, show the English flag and vice versa.
     */
    public void updateButtons() {
        if(gme.isFin()) {
            flagBtnEN.setVisible(true);
            flagBtnFI.setVisible(false);
        } else {
            flagBtnFI.setVisible(true);
            flagBtnEN.setVisible(false);
        }

    }

    /**
     * This method is used to update the Texts in the Main Menu when the language is changed. ALSO changes the {@link HUD} Language.;
     */
    public void updateTexts() {
        gme.setFin();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        btnPela.setText(curLangBundle.get("pelaa"));
        btnAsetukset.setText(curLangBundle.get("asetukset"));
        btnExit.setText(curLangBundle.get("sammuta"));
        gme.getHud().updateText();
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
