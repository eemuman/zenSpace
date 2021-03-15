/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Luokka, jolla voidaan muuttaa teksti interaktiiviseksi (Klikattavaksi)
 */
public class ClickableText {
    private String text;
    private BitmapFont font;

    private GlyphLayout layout;

    private int posX;
    private int posY;

    /**
     * @param text Muutettava teksti
     * @param posX Sen X-koordinaatti
     * @param posY Sen Y-koordinaatti
     * @param font Käytetty fontti
     */
    public ClickableText(String text, int posX, int posY, BitmapFont font) {
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.font = font;

        layout = new GlyphLayout(this.font, text);
    }

    /**
     * draw-metodi
     * @param batch Spritebatch mitä käytetään piirtoon
     */
    public void update(SpriteBatch batch) {
        font.draw(batch, layout, posX, posY);
    }

    public boolean checkClicked(OrthographicCamera camera) //Metodilla tsekataan, onko käyttäjä painanut kyseistä tekstiä
    {
        if (Gdx.input.justTouched())
        {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0); //Jos käyttäjä painaa johonkin, otetaan sen X ja Y koordinaatit
            camera.unproject(touch); //Muutetaan koordinaatit käytettäviksi koordinaateiksi
            if (getRectangle().contains(touch.x, touch.y)) //Luodaan tekstistä rectangle ja tsekataan osuuko kyseiset koordinaatit siihen
            {
                return true; //Jos kyl, nii palautetaa kyl
            }
        }
        return false; // Muuten ei
    }

    private Rectangle getRectangle()
    {
        return new Rectangle(posX, posY - (int)layout.height, (int)layout.width, (int)layout.height); //Luodaan rectangle textin ympärille, jota voidaan käyttää collision tarkistamiseen
    }

    public String getText() {
        return text;//Palautetaan se teksti, mihin klikattiin, jotta tiedetään mitä seuraavaksi tapahtuu.
    }
}

