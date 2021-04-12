/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {

    private Preferences pref;

    private int amountOfCompletions;
    private int amountOfEste = 9;
    private boolean[] amountOfBoolean = new boolean[amountOfEste];
    private float volume;
    private String este = "este", background = "background";

    public Prefs() {
        pref = Gdx.app.getPreferences("My Preferences");
        volume = pref.getFloat("volume", 100);
        amountOfCompletions = pref.getInteger("amountofCompletions", 0);
        amountOfBoolean = getAmountOfBoolean();
    }


    public void setVolume(float volume) {
        this.volume = volume;
        pref.putFloat("volume", volume);
        pref.flush();
    }

    public void setAmountofCompletions() {
        amountOfCompletions++;
        pref.putInteger("amountofCompletions", amountOfCompletions);
        pref.flush();
    }

    public float getVolume() {
        return volume;
    }

    public int getAmountofCompletions() {
        return amountOfCompletions;
    }

    public void setAndCheckBoolean(int index) {
            if(!amountOfBoolean[index]) {
                pref.putBoolean(este + index, true);
                pref.flush();
            }
    }

    public boolean[] getAmountOfBoolean() {
        for (int i = 0; i < amountOfEste; i++) {
            amountOfBoolean[i] = pref.getBoolean(este+i, false);
        }
        return amountOfBoolean;
    }

    public int calculateAmount() {
        int amount = 0;
        for (int i = 0; i < amountOfEste; i++) {
            if(pref.getBoolean(este+i)) {
                amount++;
            }
        }
        return amount;
    }

    public int getAmountOfEste() {
        return amountOfEste;
    }
}
