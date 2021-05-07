/**
 * This file was created by:
 * @author Eemil V.
 * Edited by:
 * @author Petr H.
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * This class handles our saved data. Uses the {@link Preferences}.
 */
public class Prefs {

    private Preferences pref;

    private int amountOfEste = Obstacle.getObstacleCount(), amountOfCompletions, amountOfBack = 7;
    private boolean[] amountOfBooleanEste = new boolean[amountOfEste], amountOfBooleanback = new boolean[amountOfBack];
    private boolean isFin;
    private boolean musicValue;
    private float volume;
    private String este = "este", background = "background";

    /**
     * Here we load stuff at the start, stuff like the volume, which language is in use, how many playthroughs/obstacles/backgrounds the player has seen.
     */
    public Prefs() {
        pref = Gdx.app.getPreferences("My Preferences");
        volume = pref.getFloat("volume", 100);
        amountOfCompletions = pref.getInteger("amountofCompletions", 0);
        getAmountOfBoolean(este);
        getAmountOfBoolean(background);
        isFin = pref.getBoolean("isFin", true);
        musicValue = pref.getBoolean("music", true);
    }


    /**
     * This method is used to save the volume when player moves the slider in {@link Settings}.
     * @param volume Volume as float
     */
    public void setVolume(float volume) {
        this.volume = volume;
        pref.putFloat("volume", volume);
        pref.flush();
    }

    /**
     * This method is used to save which language is in use
     * @param isFin The boolean that changes which language is in use
     */
    public void setFin(boolean isFin) {
        this.isFin = isFin;
        pref.putBoolean("isFin", isFin);
        pref.flush();
    }

    /**
     * Returns which language to use.
     * @return The language boolean
     */
    public boolean getFin() {
        return isFin;
    }

    /**
     * This method is used to save which music is in use (was last played).
     * This is to help check what music to play next time.
     * @param m boolean value that affects which music is going to be played
     */
    public void setMusicValue(boolean m) {
        this.musicValue = m;
        pref.putBoolean("music", m);
        pref.flush();
    }

    /**
     * This method returns the current boolean value for the music.
     * @return music boolean
     */
    public boolean getMusicValue() { return this.musicValue; }

    /**
     * This method is used to save the amount of completions the player has done. This is called when the player finishes a playthrough
     */
    public void setAmountofCompletions() {
        amountOfCompletions++;
        pref.putInteger("amountofCompletions", amountOfCompletions);
        pref.flush();
    }

    /**
     * This method is used to get the volume to use.
     * @return Volume as float
     */
    public float getVolume() {
        return volume;
    }

    /**
     * This method is used to get the amount of completions.
     * @return Amount of completions
     */
    public int getAmountofCompletions() {
        return amountOfCompletions;
    }

    /**
     * This method is used to check whether the player is seen the obstacle or not. If it hasn't, change the boolean to true to indicate that the player has seen it.
     * @param index Which obstacle we are checking
     */
    public void setAndCheckEste(int index) {
            if(!amountOfBooleanEste[index]) {
                pref.putBoolean(este + index, true);
                pref.flush();
            }
    }
    /**
     * This method is used to check whether the player is seen the Background or not. If it hasn't, change the boolean to true to indicate that the player has seen it.
     * @param index Which Background we are checking
     */
    public void setAndCheckBack(int index) {
        if (!amountOfBooleanback[index]) {
            pref.putBoolean(background+index, true);
            pref.flush();
        }
    }

    /**
     * We save the information of the seen backgrounds and obstacles as booleans in following way (Obstacle1 = false/true) etc.
     * This method is used to turn the seen booleans into a boolean array, since you can't store arrays in preferences file, we need to store them one by one and then turn them into a array in code.
     * @param whatToCalc This method can be used to calculate both Obstacles and Backgrounds, so we need to figure out which one of those is being calculated.
     */
    public void getAmountOfBoolean(String whatToCalc) {
        int whatCalc;
        if (whatToCalc.equals("este")) {
            whatCalc = amountOfEste;
            for (int i = 0; i < whatCalc; i++) {
                amountOfBooleanEste[i] = pref.getBoolean(whatToCalc + i, false);
            }
        } else {
            whatCalc = amountOfBack;
            for (int i = 0; i < whatCalc; i++) {
                amountOfBooleanback[i] = pref.getBoolean(whatToCalc + i, false);
            }
        }

    }


    /**
     * This method is used to calculate how many of the Backgrounds/Obstacles the player has seen. Using a for-loop and integer. This number is used in {@link Goal} screen.
     * @param whatToCalc This method can be used to calculate both Obstacles and Backgrounds, so we need to figure out which one of those is being calculated.
     * @return The amount of true booleans in the array
     */
    public int calculateAmount(String whatToCalc) {
        int amount = 0, whatCalc;
        if(whatToCalc.equals("este")) {
            whatCalc = amountOfEste;
        } else {
            whatCalc = amountOfBack;
        }
        for (int i = 0; i < whatCalc; i++) {
            if (pref.getBoolean(whatToCalc + i)) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * @return The amount of Obstacles there are in total
     */
    public int getAmountOfEste() {
        return amountOfEste;
    }
    /**
     * @return The amount of Backgrounds there are in total
     */
    public int getAmountOfBack() {
        return amountOfBack;
    }

}
