/**
 * This file was created by:
 * @author Petr H.
 * <p>
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.audio.Music;

public class Sounds {
    private static BundleHandler handler = zenSpace.getBundle();

    public static void loadSounds() {
        handler.manager.load("audio/music/testmusic.mp3", Music.class);
        handler.manager.finishLoading();
    }

    public static Music getMusic() {
        return handler.manager.get("audio/music/testmusic.mp3");
    }

}
