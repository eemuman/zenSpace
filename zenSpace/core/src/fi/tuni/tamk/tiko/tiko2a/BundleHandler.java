/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    public Texture getBackground(String name) {
        manager.load(name+".png", Texture.class);
        manager.finishLoading();
        return manager.get(name+".png", Texture.class);
    }
}
