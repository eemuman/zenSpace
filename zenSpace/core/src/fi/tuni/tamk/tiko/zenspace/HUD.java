/**
 * This file was created by:
 * @author Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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


/**
 * The HUD class for our screen, this is in use at the transition and drawing screens. It's the menu button at top left and when clicked it opens the dialog box in the middle that has continue and main menu buttons.
 * It works by having the dialog box and its buttons being set as hidden and when the menu button at the top left is pressed, the visibility is then changed to visible. Also a boolean that indicates that the dialog box is open is then set to true.
 * That boolean is then used at the transition screen to stop the player sprite from moving when the box is open. (To make it feel as if the game is actually paused.)
 */
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
        tButton = new TextButton(curLangBundle.get("menu"), skin); //This is the menu button at top left, when pressed it changes the boolean and the visibilities
        tButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gme.plop.play(1f);
                tButton.setVisible(false);
                pause.setVisible(true);
                paused = true;
            }
        });
        tButtonJatka = new TextButton(curLangBundle.get("jatka"), skin); //This button hides the dialog box and changes the boolean to false.
        tButtonJatka.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gme.plop.play(1f);
                tButton.setVisible(true);
                pause.setVisible(false);
                paused = false;
            }
        });
        tButtonMenu = new TextButton(curLangBundle.get("valikko"), skin); //This button takes you back to the main menu
        tButtonMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stg.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        gme.plop.play(1f);
                        gme.setCurLevelInt(1); // This is to make sure that the background starts from the first again
                        setBackMenu(); //Changes visibilities and booleans here at the HUD. (Prevents the next playthrough starting with menu open)
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
        pause.setBounds((game.getScrnView().getCamera().viewportWidth - 400) / 2.5f, (game.getScrnView().getCamera().viewportHeight - 200) / 2, 400, 400); //The dialog box, is set to the middle of screen.
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

    /**
     * When the user changes the games language, we also need to update the dialog box's text, that's what we are doing here.
     * See {@link BundleHandler @bundlehandler} to see how the bundlehandler works.
     */
    public void updateText() {
        curLangBundle = bundle.getResourceBundle(gme.isFin());
        tButtonJatka.setText(curLangBundle.get("jatka"));
        tButtonMenu.setText(curLangBundle.get("valikko"));
    }

    /**
     * This is used to make sure that we don't start next playthrough with the menu open. (When exiting the game with the HUD-Main Menu button)
     */
    public void setBackMenu() {
        tButton.setVisible(true);
        pause.setVisible(false);
        paused = false;
    }
}
