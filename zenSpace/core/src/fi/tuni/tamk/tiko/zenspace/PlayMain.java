/**
 * This file was created by:
 * @author Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * This class is used to show the player what he chose on the {@link Play1} and {@link Play2} screens. Also this uses the information of these mentioned screens to choose the background-set that is to be used.
 */
public class PlayMain implements Screen {

    private String firstChoice, secondChoice, firstPart, secondPart;
    private zenSpace gme;
    private Skin skin;
    private Label header, aloitetaan;
    private ExtendViewport scrnView;
    private Stage stg;
    private Table tbl;
    private TextButton btnTaka, btnEtu;
    private BundleHandler bundle;
    private I18NBundle curLangBundle;
    private String[] firstStrings, secondStrings;
    private int backGroundChoice;
    private Image headerImg;
    private Sound plop; // Added by Petr H.

    /**
     * This is the PlayMain constructor. Uses {@link Stage}, {@link BundleHandler}, etc.
     * @param game The main game object
     * @param first The first ButtonGroup-buttons index as integer
     * @param second The second ButtonGroup-buttons index as integer
     */
    public PlayMain(final zenSpace game, int first, int second) {
        gme = game;
        bundle = gme.getBundle();
        plop = Sounds.getPlopSound();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        headerImg = new Image(bundle.getUiAtlas().findRegion("Cloud_logohdpi"));
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



        header = new Label(firstPart  + firstChoice.toLowerCase() + ".\n" + secondPart +  secondChoice.toLowerCase() + ".", skin, "defaultSmall"); //This is the part where the player is shown his choices.
        aloitetaan = new Label(curLangBundle.get("aloitetaanko"), skin);
        header.setWrap(true);
        header.setWidth(10f);
        btnTaka = new TextButton(curLangBundle.get("ei"), skin, "TextButtonSmall"); //No-button to go back.
        btnEtu = new TextButton(curLangBundle.get("Kylla"), skin, "TextButtonSmall");//Yes-button to start the game.

        btnTaka.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //If no is pressed go back to the first choice screen.
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        plop.play(1f);
                        gme.setScreen(new Play1(gme));
                    }
                })));
            }
        });

        btnEtu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //If yes is pressed go to the gameplay loop.
                stg.addAction(Actions.sequence(Actions.fadeOut(gme.getFadeIn()), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                selectBackGround(); //First we choose what background to pick
                                plop.play(1f);
                                gme.getEste().randomizeEste(); //Then we randomize the first Obstacle to the game.
                                gme.setScreen(new Transition(gme, bundle.getBackground("Backgrounds/" + gme.getBackGrounds()[gme.getCurBackground()])));
                            }
                        })));
            }
        });


        tbl = new Table();

        tbl.add(headerImg).expandY().height(250).width(350).top().padTop(45).padBottom(-60).colspan(2);
        tbl.row();
        tbl.add(header).expand().width(440f).colspan(2).padTop(-160);
        tbl.row();
        tbl.add(aloitetaan).colspan(2).padBottom(75);
        tbl.row();
        tbl.add(btnTaka).left().bottom().height(75).width(200).padBottom(15).padLeft(15);
        tbl.add(btnEtu).right().bottom().height(75).width(200).padBottom(15).padRight(15);
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
    dispose();
    }

    @Override
    public void dispose() {
        stg.dispose();
    }

    /**
     * This is used to choose the background. It is an integer on the main game object that the other classes then use.
     */
    private void selectBackGround() {
        gme.setCurBackground(backGroundChoice);
    }

    /**
     * This method is used to generate a more natural looking sentences. (These are the choice sentences)
     * @param first The first choice as integer
     * @param second The second choice as integer
     */
    private void selectParts(int first, int second) {
        if(gme.isFin()) {
            if (first > 2) {
                firstPart = curLangBundle.get("firstPart1");
            } else {
                firstPart = curLangBundle.get("firstPart2");
            }
            if (second == 0 || second == 2 || second == 6) {
                secondPart = curLangBundle.get("secondPart1");
            } else {
                secondPart = curLangBundle.get("secondPart2");
            }
        }
        else {
            if(first == 0) {
                firstPart = curLangBundle.get("firstPart2");
            } else {
                firstPart = curLangBundle.get("firstPart1");
            }
            secondPart = curLangBundle.get("secondPart1");
        }
    }
}
