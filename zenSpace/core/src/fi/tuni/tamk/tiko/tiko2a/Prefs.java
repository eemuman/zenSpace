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

    private int amountofCompletions;
    private float volume;

    public Prefs() {
        pref = Gdx.app.getPreferences("My Preferences");
        volume = pref.getFloat("volume", 100);
        amountofCompletions = pref.getInteger("amountofCompletions", 0);
    }


    public void setVolume(float volume) {
        this.volume = volume;
        pref.putFloat("volume", volume);
        pref.flush();
    }

    public void setAmountofCompletions() {
        amountofCompletions++;
        pref.putInteger("amountofCompletions", amountofCompletions);
        pref.flush();
    }

    public float getVolume() {
        return volume;
    }

    public int getAmountofCompletions() {
        return amountofCompletions;
    }
}
