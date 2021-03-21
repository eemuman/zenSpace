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
    private Body b;
    private Texture runTexture;
    private TextureRegion[][] textureRegion2D;
    private TextureRegion[] textureRegionArray;
    private Animation<TextureRegion> runAnimation;
    private TextureRegion currentPlayerFrame;
    private BodyDef myBodyDef;
    private FixtureDef myFixtureDef;
    private SpriteBatch batch;
    private ExtendViewport scrnView;

    private float playerWidth = 400f;
    private float playerHeight = 600f;

    /**
     * Helper variable for counting the current frame.
     */
    private float stateTime = 0.0f;

    public Este(zenSpace game) {
        g = game;
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        scrnView = g.getScrnView();
        renderer = new Box2DDebugRenderer();
        cam = g.getTextCam();
        cam.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        createPlayer(playerWidth/2.5f, playerHeight/1.25f);
    }

    private void createPlayer(float width, float height) {
        runTexture = new Texture("spritesheet.png");

        // Create animations
        textureRegion2D = Utils.setRegionArray(runTexture, ROWS, COLS);
        textureRegionArray = Utils.transformTo1D(textureRegion2D, ROWS, COLS);
        runAnimation = Utils.setAnimation(textureRegionArray, runAnimation, 7);

        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.DynamicBody;

        myBodyDef.position.set(WORLD_WIDTH/2f, WORLD_HEIGHT/2f);
        myBodyDef.fixedRotation = true;

        b = world.createBody(myBodyDef);
        b.setUserData(runTexture);

        myFixtureDef = new FixtureDef();
        myFixtureDef.density = 2;
        myFixtureDef.restitution = 0.01f;
        myFixtureDef.friction = 0.5f;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2f, height / 2f);

        myFixtureDef.shape = polygonShape;
        b.createFixture(myFixtureDef);
    }

    void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(currentPlayerFrame,
                b.getPosition().x - 40f,
                b.getPosition().y -325f,
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
        currentPlayerFrame = runAnimation.getKeyFrame(stateTime, true);
        draw(batch);

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
