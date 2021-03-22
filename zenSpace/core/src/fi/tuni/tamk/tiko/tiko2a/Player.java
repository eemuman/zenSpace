/*
 * This file was created by:
 * @Petr Halinen
 * Edited to its own class by Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {

    private Body b;
    private Texture runTexture;
    private TextureRegion[][] textureRegion2D;
    private TextureRegion[] textureRegionArray;
    private Animation<TextureRegion> runAnimation;
    private BodyDef myBodyDef;
    private FixtureDef myFixtureDef;
    private float playerWidth = 80f;
    private float playerHeight = 240f;
    private TextureAtlas textureAtlas;


    public Player(int ROWS, int COLS, int WORLD_WIDTH, int WORLD_HEIGHT, World world) {
        runTexture = new Texture("spritesheet.png");

        // Tehdään siitä animaatio (nää kolme voi yhdistää yhteen pötköön,
        // mut jätin näin koska on selkeämpi aluks.)
        textureRegion2D = Utils.setRegionArray(runTexture, ROWS, COLS);
        textureRegionArray = Utils.transformTo1D(textureRegion2D, ROWS, COLS);
        runAnimation = Utils.setAnimation(textureRegionArray, runAnimation, 7);

        // Luodaan pelaajan box2dbody
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
        polygonShape.setAsBox(playerWidth, playerHeight);

        myFixtureDef.shape = polygonShape;
        b.createFixture(myFixtureDef);
    }

    public Player(int ROWS, int COLS) {
        textureAtlas = new TextureAtlas("test.txt");
        //runTexture = new Texture("test.png");

        // Tehdään siitä animaatio (nää kolme voi yhdistää yhteen pötköön,
        // mut jätin näin koska on selkeämpi aluks.)
       // textureRegion2D = Utils.setRegionArray(runTexture, ROWS, COLS);
       // textureRegionArray = Utils.transformTo1D(textureRegion2D, ROWS, COLS);
     //   runAnimation = Utils.setAnimation(textureRegionArray, runAnimation, 7);

        runAnimation = new Animation<TextureRegion>(1f/15f,textureAtlas.getRegions(), Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getRunAnimation() {
        return runAnimation;
    }

    public float getPlayerWidth() {
        return playerWidth;
    }

    public float getPlayerHeight() {
        return playerHeight;
    }
    public Body getB() {
        return b;
    }

}
