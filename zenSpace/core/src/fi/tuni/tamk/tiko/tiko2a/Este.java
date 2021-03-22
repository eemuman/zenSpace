/*
 * This file was created by:
 * @Petr H.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Este implements Screen{
    private zenSpace g;
    private World world;
    private int WORLD_WIDTH = 480;
    private int WORLD_HEIGHT = 800;
    private int ROWS = 1, COLS = 10;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera cam;
    private TextureRegion currentPlayerFrame;
    private SpriteBatch batch;
    private Player player;

    private final float TIME_STEP = 1/60f;

    /**
     * Helper variable for counting the current frame.
     */
    private float stateTime = 0.0f;
    private double accumulator = 0;

    public Este(zenSpace game) {
        g = game;
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -9.8f), true);
        player = new Player(ROWS, COLS, WORLD_WIDTH, WORLD_HEIGHT, world);
        renderer = new Box2DDebugRenderer();
        cam = g.getTextCam();
        cam.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }


    void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(currentPlayerFrame,
                // Nää on kämäsesti koska en osannu laittaa pelaajaa tuohon keskelle muuten
                // eli vaatii säätöä.
                player.getB().getPosition().x - 40f,
                player.getB().getPosition().y -325f,
                currentPlayerFrame.getRegionWidth()/2f,
                currentPlayerFrame.getRegionHeight()/2f,
                currentPlayerFrame.getRegionWidth()/2.5f,
                currentPlayerFrame.getRegionHeight()/2.5f,
                1.0f,
                1.0f,
                0f);
        batch.end();

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(world, cam.combined);
        stateTime += delta;
        currentPlayerFrame = player.getRunAnimation().getKeyFrame(stateTime, true);
        draw(batch);
        doPhysicsStep(delta);

    }

    private void doPhysicsStep(float deltaTime) {
        float frameTime = deltaTime;
        if(deltaTime > 1/4f) {
            frameTime = 1/4f;
        }
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
    }
}
