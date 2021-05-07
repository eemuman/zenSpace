/**
 * This file was created by:
 * @author Petr H.
 * <p>
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * This class is for handling all the sounds and music of the game.
 */
public class Sounds {
    public BundleHandler handler;
    private Prefs prefs = new Prefs();
    private Music music;
    private Sound plop;
    private float masterVolume = 1f;

    public Sounds(zenSpace game){
        loadSounds(game);
        plop = setPlopSound();
        music = setMusic();
    }

    /**
     * This method is used to load all the sound and music assets.
     */
    public void loadSounds(zenSpace gm) {
        handler = gm.getBundle();
        if(prefs.getMusicValue()) {
            handler.manager.load("audio/music/ancientwinds_music.wav", Music.class);
        } else {
            handler.manager.load("audio/music/mindful_music.wav", Music.class);
        }
        handler.manager.load("audio/sound/plop.wav", Sound.class);
        handler.manager.finishLoading();
    }

    /**
     * This method is used to create Music object.
     * @return returns the Music class audiofile being used
     */
    public Music setMusic() {
        boolean temp = prefs.getMusicValue();
        if(temp) {
            temp = !temp;
            prefs.setMusicValue(temp);
            return handler.manager.get("audio/music/ancientwinds_music.wav");
        } else {
            temp = !temp;
            prefs.setMusicValue(temp);
            return handler.manager.get("audio/music/mindful_music.wav");
        }
    }

    /**
     * This method is used to create Sound object.
     * @return returns the Sound class audiofile
     */
    public Sound setPlopSound() {
        return handler.manager.get("audio/sound/plop.wav");
    }

    /**
     * This method is used to play the Music in any other class.
     */
    public void playMusic() {
        music.setLooping(true);
        music.setVolume(masterVolume);
        music.play();
    }

    /**
     * This method is used to play the Sound in any other class.
     */
    public void playPlopSound() {
        plop.play(masterVolume);
    }

    public void setVolume(float volume) {
        music.setVolume(volume);
        masterVolume = volume;
    }
}
