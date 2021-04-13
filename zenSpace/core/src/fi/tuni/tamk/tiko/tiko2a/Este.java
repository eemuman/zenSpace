/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;

public class Este {
    private zenSpace gme;
    private BundleHandler bundle;
    private TextureAtlas esteTextures;
    private AtlasRegion currentDraw;
    private int randEste;
    private boolean transition = false, result = false;
    private String[] esteet =
            {"anteater","bat", "butterfly", "crafts", "dragon", "flamingo", "onion",
                    "paint", "plant", "snake1", "snake2", "snake3", "trex", "wall", "zombie", "zombie2"};
    private boolean[] seenAlready = new boolean[esteet.length];

    public Este(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
        initseenAlready();
    }


    public void randomizeEste() {
        do {
            randEste = MathUtils.random(0, esteet.length - 1);
        } while (!checkAndSetSeen());
        esteTextures = bundle.getBackground("Esteet/" + esteet[randEste] + ".atlas");
        setBooleans(true, false);

    }

    public void setBooleans(boolean transition, boolean result) {
        this.transition = transition;
        this.result = result;
        setTexture();
    }

    private void setTexture() {
        if (transition) {
            currentDraw = esteTextures.findRegion("sthdpi");
        } else if (result) {
            currentDraw = esteTextures.findRegion("resulthdpi");
        }
    }

    public AtlasRegion getTexture() {
        return currentDraw;
    }

    public String getEste() {
        return esteet[randEste];
    }

    public int getEsteInt() {
        return randEste;
    }

    private boolean checkAndSetSeen() {
        if(!seenAlready[randEste]) {
            seenAlready[randEste] = true;
            return true;
        }
        return false;
    }

    public void initseenAlready() {
        for (int i = 0; i < seenAlready.length; i++) {
            seenAlready[i] = false;
        }
    }
}


