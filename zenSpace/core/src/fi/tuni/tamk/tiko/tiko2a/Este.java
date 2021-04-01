/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class Este {
    private zenSpace gme;
    private BundleHandler bundle;
    private TextureAtlas esteTextures;
    private boolean transition = false, piirto = false, result = false;
    private String[] esteet = {"bat", "buttefly", "dragon", "snake1", "snake2", "snake3", "wall"};

    public Este(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();


    }

    private void randomizeEste() {
        int randEste = (int) MathUtils.random(0, esteet.length);

        bundle.getBackground("Esteet/" + esteet[randEste]);
    }
}


