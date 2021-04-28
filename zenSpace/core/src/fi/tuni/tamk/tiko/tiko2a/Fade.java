/*
 * This file was created by:
 * @Eemil V.
 *
 * TODO: SAADA TÄÄ EHKÄ TOIMII JOSKUS??? EMT
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.utils.GdxRuntimeException;



public class Fade extends Actor {

    public Image getImage(){
        Pixmap pixmap;
        Image text;
        try {
            pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        }catch (GdxRuntimeException e)
        {
            pixmap=new Pixmap(1,1, Pixmap.Format.RGB565);
        }
        pixmap.setColor(135/255f, 206/255f, 235/255f, 1);
        pixmap.fillRectangle(0,0,1,1);
        text = new Image(new TextureRegion(new Texture(pixmap)));
        text.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        text.setColor(Color.WHITE);
        return text;
    }

}
