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
    private boolean transition = false, piirto = false, result = false;
    private String[] esteet = {"bat.atlas", "butterfly.atlas", "dragon.atlas", "snake1.atlas", "snake2.atlas", "snake3.atlas", "wall.atlas"};

    public Este(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
      //  randomizeEste();
    }


    public void randomizeEste() {
        int randEste = (int) MathUtils.random(0, esteet.length -1);
        esteTextures = bundle.getBackground("Esteet/" + esteet[randEste]);
        setBooleans(true, false, false);
    }

    public void setBooleans(boolean transition, boolean piirto, boolean result) {
        this.transition = transition;
        this.piirto = piirto;
        this.result = result;
        setTexture();
    }

    private void setTexture() {
        if(transition) {
            currentDraw = esteTextures.findRegion("sthdpi");
        } else if(piirto) {
            currentDraw = esteTextures.findRegion("drawhdpi");
    } else if(result) {
            currentDraw = esteTextures.findRegion("resulthdpi");
        }
}
    public AtlasRegion getTexture() {
        return currentDraw;
    }
}


