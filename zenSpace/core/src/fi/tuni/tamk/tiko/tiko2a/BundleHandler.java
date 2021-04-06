/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;




import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;




public class BundleHandler {
    public AssetManager manager = new AssetManager();


    public void loadAssets() {
        manager.load("data/uiskin.atlas", TextureAtlas.class);
        manager.load("data/uiskin.json", Skin.class);
        manager.finishLoading();
    }

    public I18NBundle getResourceBundle(Boolean fin) {
        if(fin) {
            if(manager.isLoaded("lang/Text_en_US")) {
                manager.unload("lang/Text_en_US");
            }
            manager.load("lang/Text_fi_FI", I18NBundle.class);
            manager.finishLoading();
            return manager.get("lang/Text_fi_FI", I18NBundle.class);
        } else {
            if(manager.isLoaded("lang/Text_fi_FI")) {
                manager.unload("lang/Text_fi_FI");
            }
            manager.load("lang/Text_en_US", I18NBundle.class);
            manager.finishLoading();
            return manager.get("lang/Text_en_US", I18NBundle.class);
        }

    }
    public TextureAtlas getUiAtlas() {
        return manager.get("data/uiskin.atlas", TextureAtlas.class);
    }

    public Skin getUiSkin() {
        return manager.get("data/uiskin.json", Skin.class);
    }

    public TextureAtlas getBackground(String name) {
        if(manager.isLoaded(name, TextureAtlas.class)) {
            return manager.get(name, TextureAtlas.class);
        }
        manager.load(name, TextureAtlas.class);
        manager.finishLoading();
        return manager.get(name, TextureAtlas.class);
    }
    public Texture getPlayerSkin() {
        if(manager.isLoaded("sprites.png", Texture.class)) {
            return manager.get("sprites.png", Texture.class);
        }
        manager.load("sprites.png", Texture.class);
        manager.finishLoading();
        return manager.get("sprites.png", Texture.class);
    }

    public TiledMap getTiledMap(String name) {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("Esteet/"+name+".tmx", TiledMap.class);
        manager.finishLoading();
        return manager.get("Esteet/"+name+".tmx", TiledMap.class);
    }
}
