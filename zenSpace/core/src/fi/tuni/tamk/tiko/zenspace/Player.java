/*
 * This file was created by:
 * @Petr Halinen
 * Edited to its own class by Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * This class handels the creation of the player animation
 */
public class Player extends Actor {
    /**
     * Texture of player animation
     */
    private Texture runTexture;

    /**
     * Animation of the player moving
     */
    private Animation<TextureRegion> runAnimation;

    /**
     * The duration on each frame a.k.a the speed of the animation
     */
    private float frameDuration = 5f;

    /**
     * Constructor for Player
     * @param ROWS number of rows in 2D array
     * @param COLS number of columns in 2D array
     * @param bundle the BundleHandler used to manage assets
     */
    public Player(int ROWS, int COLS, BundleHandler bundle) {
        // load texture via BundleHandler
        runTexture = bundle.getPlayerSkin();

        // make it into an animation using Utils class methods
        runAnimation = Utils.setAnimation(Utils.transformTo1D(
                Utils.setRegionArray(runTexture, ROWS, COLS), ROWS, COLS), runAnimation, frameDuration);

    }

    /**
     * Get the run animation
     * @return returns the animation of player running (moving)
     */
    public Animation<TextureRegion> getRunAnimation() {
        return runAnimation;
    }

}
