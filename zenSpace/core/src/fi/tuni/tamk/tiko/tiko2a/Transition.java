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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * This class is used to show the Transition screen. The screen where the Player object walks to the other side. From here we go to the {@link Drawing} screen.
 * We use the {@link Obstacle} class to load the obstacles part that stands on the right side of the screen.
 * Also {@link HUD} Is being rendered here, and if open we stop the playerobject from moving (To make it seem like the game is paused).
 */
public class Transition implements Screen {

    HUD hud;
    private int movementSpeed  = 50;
    private float currentX;
    private TextureRegion currentPlayerFrame;
    private AtlasRegion bgTexture;
    private ExtendViewport scrnView;
    private SpriteBatch batch;
    private Player player;
    private String curLevel;
    private Stage stg;
    private Image img;

    private boolean shouldRender = true;

    private float stateTime = 0.0f;

    private zenSpace gme;

    /**
     * Constructor where we find the background part of the atlas we want to render. Also generate a player object to walk on the screen.
     * @param game Main game object
     * @param bgTexture The background texture we want to render here.
     */
    public Transition(zenSpace game, TextureAtlas bgTexture) {
        gme = game;
        hud = gme.getHud();
        img = gme.generateFade();

        Gdx.input.setInputProcessor(hud.stg);
        scrnView = game.getScrnView();
        stg = new Stage(scrnView);

        stg.addActor(img);
        this.curLevel = "st" + game.getCurLevel(); //We use this to find the part of the Background atlas we want to render here.
        this.bgTexture = bgTexture.findRegion(curLevel); //Here we actually find it using the number from above
        batch = game.getBatch();
        player = new Player(1, 9, gme.getBundle());

        img.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(gme.getFadeIn())));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(scrnView.getCamera().combined);
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!hud.isPaused() && shouldRender) { //If we are paused, stop the player from moving.
            stateTime += delta;
            currentX += delta * movementSpeed; //Player objects X is changed here to simulate walking.
            currentPlayerFrame = player.getRunAnimation().getKeyFrame(stateTime, true);
        }
        batch.begin();
        if(bgTexture != null)
        batch.draw(bgTexture, 0, 0, scrnView.getCamera().viewportWidth, scrnView.getCamera().viewportHeight);
        batch.draw(gme.getEste().getTexture(), scrnView.getCamera().viewportWidth / 1.3f, scrnView.getCamera().viewportHeight / 4, scrnView.getCamera().viewportWidth / 5f, scrnView.getCamera().viewportHeight / 5f);
        if (currentPlayerFrame != null) {
            batch.draw(currentPlayerFrame, currentX, scrnView.getCamera().viewportHeight / 4, scrnView.getCamera().viewportWidth / 4f, scrnView.getCamera().viewportHeight / 4f);
        }
        batch.end();
        hud.render(delta);
        checkPlayerPos();

        stg.act(delta);
        stg.draw();
    }


    @Override
    public void resize(int width, int height) {
        scrnView.update(width, height, true);
        scrnView.getCamera().update();
        stg.getViewport().update(width, height, true);
        stg.getCamera().update();
    }

    /**
     * Here we check that if the player has moved to the right side, and if it has, change to the {@link Drawing} screen.
     */
    private void checkPlayerPos() {
        if(currentX >= scrnView.getCamera().viewportWidth / 1.8f && shouldRender) {
            shouldRender = false;
            img.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.run(new Runnable() {
                @Override
                public void run() {
                        gme.getEste().setBooleans(false, false); //Stop drawing the obstacle from the obstacle class, since we are going to the tiled map.
                        gme.setScreen(new Drawing(gme));

                }
            })));
        }
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
}
