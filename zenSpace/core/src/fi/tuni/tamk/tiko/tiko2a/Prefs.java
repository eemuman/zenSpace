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

    private int amountOfEste = 17, amountOfCompletions, amountOfBack = 7;
    private boolean[] amountOfBooleanEste = new boolean[amountOfEste], amountOfBooleanback = new boolean[amountOfBack];
    private boolean isFin;
    private float volume;
    private String este = "este", background = "background";

    public Prefs() {
        pref = Gdx.app.getPreferences("My Preferences");
        volume = pref.getFloat("volume", 100);
        amountOfCompletions = pref.getInteger("amountofCompletions", 0);
        getAmountOfBoolean(este);
        getAmountOfBoolean(background);
        isFin = pref.getBoolean("isFin", true);
    }


    public void setVolume(float volume) {
        this.volume = volume;
        pref.putFloat("volume", volume);
        pref.flush();
    }

    public void setFin(boolean isFin) {
        this.isFin = isFin;
        pref.putBoolean("isFin", isFin);
        pref.flush();
    }

    public boolean getFin() {
        return isFin;
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

    public void setAndCheckEste(int index) {
            if(!amountOfBooleanEste[index]) {
                pref.putBoolean(este + index, true);
                pref.flush();
            }
    }

    public void setAndCheckBack(int index) {
        if (!amountOfBooleanback[index]) {
            pref.putBoolean(background+index, true);
            pref.flush();
        }
    }

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

    public int getAmountOfEste() {
        return amountOfEste;
    }
    public int getAmountOfBack() {
        return amountOfBack;
    }

}
