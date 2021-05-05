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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * This class is used to show the goal screen. That is the screen that is shown when player plays through the game. This screen shows the player some statistics that we collect. (Playthrough amount, seen background/obstacle amounts)
 * See: {@link Prefs} for more information on what we collect.
 */
public class Goal implements Screen {
    private zenSpace gme;
    private Image img;
    private Stage stg;

    private TextButton back;
    private TextureAtlas.AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;

    private float dynamicUnitScale;

    private BundleHandler bundle;
    private I18NBundle curLangBundle;
    private Skin skin;
    private Table tbl;
    private Label lbl, lbl1, lbl2, lbl3;
    private float fadeInTime = 3f;
    private Sound plop; // Added by Petr H.


    /**
     * The constructor for the goal class.
     * Generating the background, texts for the statistics we have been collecting, setting them up, wrapping them (to prevent the text from overflowing the screen), and then adding a fade in animation to the texts, so that the player first sees the background and then the texts slowly fade in on top of the background.
     * Also has a button that takes you back to the {@link newMainMenu}.
     * @param game The main game object
     * @param bgTexture The background set we are using. (To get which background to render)
     */
    public Goal(zenSpace game,TextureAtlas bgTexture) {
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);
        gme = game;
        img = gme.generateFade();
        stg.addActor(img);

        this.bgTexture = bgTexture.findRegion("goal"); // Load the Goal part of the Bgtexture .atlas
        batch = game.getBatch();
        scrnView = game.getScrnView();
        dynamicUnitScale = scrnView.getWorldHeight() - gme.getwHeight();
        stg = new Stage(scrnView);
        bundle = gme.getBundle();
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        skin = bundle.getUiSkin();
        tbl = new Table();
        tbl.setFillParent(true);

        plop = Sounds.getPlopSound();

        Gdx.input.setInputProcessor(stg);

        back = new TextButton(curLangBundle.get("menu"), skin, "TextButtonSmallWhite"); // Button to put at the BOTTOM TO GO BACK INTO THE MAIN MENU
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                plop.play(1f);
                gme.setCurLevelInt(1);
                gme.getEste().initseenAlready();
                gme.setScreen(new newMainMenu(gme));
            }
        });
        lbl = new Label(curLangBundle.get("hienoa"), skin, "white");
        lbl1 = new Label(curLangBundle.get("lapipeluu") + gme.prefs.getAmountofCompletions() + curLangBundle.get("kertaa"),skin, "WhiteSmall"); // LBL TO SHOW PLAYER HOW MANY PLAYTHROUGHS HAS BEEN PLAYED
        lbl2 = new Label( curLangBundle.get("esteesta")+gme.prefs.calculateAmount("este") + "/" + gme.prefs.getAmountOfEste(),skin,"WhiteSmall"); // LBL TO SHOW HOW MANY OUT OF ALL OBSTACLES THE PLAYER HAS SEEN
        lbl3 = new Label(curLangBundle.get("taustaa") + gme.prefs.calculateAmount("background") + "/" + gme.prefs.getAmountOfBack(), skin, "WhiteSmall"); // SAME AS ABOVE, BUT WITH BACKGROUNDS INSTEAD
        lbl1.setWrap(true);
        lbl1.setWidth(10f);
        lbl2.setWrap(true);
        lbl2.setWidth(10f);
        tbl.add(lbl).expand().padTop(250).top();
        tbl.row();
        tbl.add(lbl1).expand().width(375f).top();
        tbl.row();
        tbl.add(lbl2).expand().width(375f);
        tbl.row();
        tbl.add(lbl3).expand().width(375f);
        tbl.row();
        tbl.add(back).width(300).height(dynamicUnitScale).padBottom(50);
        stg.addActor(tbl);
        img.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(gme.getFadeIn())));
        stg.addAction(Actions.sequence(Actions.alpha(0),Actions.delay(fadeInTime), Actions.fadeIn(fadeInTime))); //MAKE THE TEXTS INVISIBLE FIRST, THEN FADE THEM IN AFTER SET AMOUNT OF TIME.
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
        stg.clear();
        stg.dispose();
    }
}
