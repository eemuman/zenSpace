/**
 * This file was created by:
 * @author Eemil V.
 * Edited by:
 * @author Petr H.
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * This Class handles the Obstacles. (The thing on the right at the transition, the tiledmap, and the resulting screen after the next transition)
 */
public class Obstacle {
    private zenSpace gme;
    private BundleHandler bundle;
    private TextureAtlas esteTextures;
    private AtlasRegion currentDraw;
    private boolean transition = false, result = false;
    private static String[] esteet =
            {"anteater", "bat", "bull", "butterfly", "crafts", "dragon", "elephant", "flamingo",
                    "friend", "helium", "lizard", "octopus", "onion",
                    "paint", "paper", "parrot", "penguin", "plant", "snake1",
                    "snake2", "snake3", "trex", "wall", "yoga", "zombie", "zombie2"}; //All the different obstacles, probably could've stored these somewhere else..

    private boolean[] seenAlready = new boolean[esteet.length]; //This is used to make sure that the same obstacle isn't shown more than once per playthrough.

    /**
     * Init the obstacles class see {@link #initseenAlready()} to see what it does.
     * @param game The main game object.
     */
    public Obstacle(zenSpace game) {
        gme = game;
        bundle = gme.getBundle();
        initseenAlready();
    }

    public static int getObstacleCount() {
        return esteet.length;
    }

    /**
     * This method is used to randomize what obstacle to show next. first we randomize a number in a do/while to make sure that we randomize it at least once. While loop condition is that the obstacle hasn't been seen yet. (See: {@link #checkAndSetSeen()})
     * If the obstacle is new, load the obstacle through the {@link BundleHandler}. And set booleans.
     */
    public void randomizeEste() {
        do {
            gme.setRandEste(MathUtils.random(0, esteet.length - 1));
        } while (!checkAndSetSeen());
        esteTextures = bundle.getBackground("Esteet/" + esteet[gme.getCurEsteInt()]); //We load the obstacle using the String array that has all of the obstacle names and using the number we randomized before
        setBooleans(true, false);

    }

    /**
     * This method is used to set booleans for what part to load of the .atlas next. (When transition is true, load the part that is shown when in transition screen and when result is true, load the part that is shown after the drawing mechanic)
     * This could've been done in a single boolean, but I think this is more easy to understand.
     * @param transition If this is true, load the part that is used in the transition screens
     * @param result If this is true, load the part that is used in the in the result screens.
     */
    public void setBooleans(boolean transition, boolean result) {
        this.transition = transition;
        this.result = result;
        setTexture();
    }

    /**
     * This method is used to set the texture that we want to render next. Which part to load, depends on the {@link #setBooleans(boolean, boolean)} method.
     */
    private void setTexture() {
        if (transition) {//sthdpi is the name for the transitionscreen part of the .atlas
            currentDraw = esteTextures.findRegion("sthdpi");
        } else if (result) { //resulthdpi is the name for the resultscreen part of the .atlas
            currentDraw = esteTextures.findRegion("resulthdpi");
        }
    }

    /**
     * This method is used to give other classes the texture that we want to render next
     * @return What texture we want to render now
     */
    public AtlasRegion getTexture() {
        return currentDraw;
    }

    /**
     * This method is used to give other classes the obstacles name as STRING
     * @return Current obstacles name as STRING
     */
    public String getEste() {
        return esteet[gme.getCurEsteInt()];
    }


    /**
     * This method is used to check whether we have seen the current obstacle during this playthrough already. This is done using an a boolean array where we change the boolean to true if we haven't seen it yet. Obviously if the boolean is already true, we return false
     * This is used as a helper class for the {@link #randomizeEste()} method.
     * @return Whether we have seen the current obstacle already this playthrough.
     */
    private boolean checkAndSetSeen() {
        if(!seenAlready[gme.getCurEsteInt()]) {
            seenAlready[gme.getCurEsteInt()] = true;
            return true;
        }
        return false;
    }

    /**
     * This method is used to reset the boolean (All of them to false) array when starting a new playthrough.
     */
    public void initseenAlready() {
        for (int i = 0; i < seenAlready.length; i++) {
            seenAlready[i] = false;
        }
    }
}


