/**
 * This file was created by:
 * @author Eemil V.
 * <p>
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * This is our games asset manager. It handles loading all the assets in our game. (UISKIN.atlas, Languagebundles, Background atlases, obstacle atlases. Etc.
 */
public class BundleHandler {
    public AssetManager manager = new AssetManager();


    /**
     * Load the UI's Atlas using this method. This has all the fonts, buttons, some images, basically everything that is in the menus.
     */
    public void loadAssets() {
        manager.load("data/uiskin.atlas", TextureAtlas.class);
        manager.load("data/uiskin.json", Skin.class);
        manager.finishLoading();
    }

    /**
     * This method is used to load the Language bundle that is needed. First we check if the other language is loaded and unload it if it is, then load the wanted one and return it. We don't use the androids locale to check which language to use
     * @param fin This boolean is used to check if we should load the Finnish or English language bundles.
     * @return The wanted language bundle.
     */
    public I18NBundle getResourceBundle(Boolean fin) {
        if(fin) { //IF FINNISH LANGUAGE IS CHOSEN, CHECK WHETHER THE US LANG IS LOADED AND UNLOAD IT
            if(manager.isLoaded("lang/Text_en_US")) {
                manager.unload("lang/Text_en_US");
            } //LOAD THE FINNISH LANG AND RETURN IT
            manager.load("lang/Text_fi_FI", I18NBundle.class);
            manager.finishLoading();
            return manager.get("lang/Text_fi_FI", I18NBundle.class);
        } else { //IF THE ENGLISH LANGUAGE IS CHOSEN, UNLOAD THE FINNISH LANG IF NEEDED AND LOAD THE ENGLISH BUNDLE.
            if(manager.isLoaded("lang/Text_fi_FI")) {
                manager.unload("lang/Text_fi_FI");
            }
            manager.load("lang/Text_en_US", I18NBundle.class);
            manager.finishLoading();
            return manager.get("lang/Text_en_US", I18NBundle.class);
        }

    }

    /**
     * Getter for the UI-Atlas
     * @return The UI's atlas
     */
    public TextureAtlas getUiAtlas() {
        return manager.get("data/uiskin.atlas", TextureAtlas.class);
    }

    /**
     * Getter for the UI-Json
     * @return Ui-Json
     */
    public Skin getUiSkin() {
        return manager.get("data/uiskin.json", Skin.class);
    }

    /**
     * This method is used to load the Background set that the user chooses AND the RANDOMIZED obstacle that the {@link Obstacle} generates.
     * @param name The backgrounds name we want to load
     * @return The loaded background / Obstacle .atlas
     */
    public TextureAtlas getBackground(String name) {
        if(manager.isLoaded(name, TextureAtlas.class)) {
            return manager.get(name, TextureAtlas.class);
        }
        manager.load(name, TextureAtlas.class);
        manager.finishLoading();
        return manager.get(name, TextureAtlas.class);
    }

    /**
     * This is used to give the {@link Player} class the player objects spritesheet. (The walking stick figure in the transition screens.)
     * @return The players Spritesheet.
     */
    public Texture getPlayerSkin() {
        if(manager.isLoaded("sprites.png", Texture.class)) {
            return manager.get("sprites.png", Texture.class);
        }
        manager.load("sprites.png", Texture.class);
        manager.finishLoading();
        return manager.get("sprites.png", Texture.class);
    }

    /**
     * This method is used to load the obstacle's tiledmap. Which is used at the {@link Drawing} for the players to draw on.
     * @param name The needed tilemap's name
     * @return The loaded tilemap
     */
    public TiledMap getTiledMap(String name) {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("Esteet/"+name+".tmx", TiledMap.class);
        manager.finishLoading();
        return manager.get("Esteet/"+name+".tmx", TiledMap.class);
    }


}
