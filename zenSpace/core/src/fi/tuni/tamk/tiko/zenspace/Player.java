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

public class Player extends Actor {

    private Texture runTexture;
    private Animation<TextureRegion> runAnimation;
    private float frameDuration = 5f;

    public Player(int ROWS, int COLS, BundleHandler bundle) {
        runTexture = bundle.getPlayerSkin();

        // Tehdään siitä animaatio
        runAnimation = Utils.setAnimation(Utils.transformTo1D(
                Utils.setRegionArray(runTexture, ROWS, COLS), ROWS, COLS), runAnimation, frameDuration);

    }

    public Animation<TextureRegion> getRunAnimation() {
        return runAnimation;
    }

}
