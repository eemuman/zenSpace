/**
 * This file was created by:
 * @author Petr H.
 * <p>
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.audio.Music;

/**
 * This class is for handling all the sounds and music of the game.
 */
public class Sounds {
    // Get BundleHandler from the class zenSpace
    private static BundleHandler handler = zenSpace.getBundle();

    /**
     * This method is used to load all the sound and music assets.
     */
    public static void loadSounds() {
        handler.manager.load("audio/music/testmusic.mp3", Music.class);
        handler.manager.finishLoading();
    }

    /**
     * This method is used to be able to play the music in other classes.
     * @return returns the Music class audiofile to be played.
     */
    public static Music getMusic() {
        return handler.manager.get("audio/music/testmusic.mp3");
    }

}
