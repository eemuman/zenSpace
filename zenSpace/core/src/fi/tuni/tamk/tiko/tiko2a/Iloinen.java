/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;


/**
 * Iloinen näyttö, käyttää inputadapteria,
 * luodaan kamerat, spritebatchit, pixmapit, fontit,
 * piirron X ja Y koordinattifloatit, kameran leveys,
 * Tekstuuri jota piirretään ja pari booleania.
 */
public class Iloinen extends InputAdapter implements Screen {
    zenSpace game;
    private OrthographicCamera textCam;
    private SpriteBatch batch;
    private Pixmap map;
    private OrthographicCamera cam;
    private float startX;
    private float startY;
    private float newX;
    private float newY;
    private int wWidth = 640;
    private int wHeight = 320;
    private boolean touched = false;
    private boolean moved = false;
    private boolean isEmpty = true;

    private String clr = "CLEARSCREEN";

    private Texture pixText;

    List<ClickableText> clickableTexts = new ArrayList<>();


    /**
     * Luodaan klikattavateksti, jolla voi sitten tyhjätä näytön ja käynnistetään inputprosessori
     * @param game Konstruktorissa otetaan vastaan zenSpace pelin objekti ja otetaan sieltä käyttöön kamerat, fontit, spritebatchit, ym.
     */
    public Iloinen(zenSpace game) {
        this.game = game;
        textCam = game.getTextCam();
        cam = game.getCam();
        batch = game.getBatch();
        map = new Pixmap(wWidth, wHeight, Pixmap.Format.RGBA8888);
        map.setColor(Color.WHITE);
    //    clickableTexts.add(new ClickableText(clr, 25, 315, font));
        Gdx.input.setInputProcessor(this);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Ensiksi käytetään textCamia (Toinen kamera ei ole vielä missään käytössä)
        batch.setProjectionMatrix(textCam.combined);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textCam.update();
        batch.begin();

        //Piirretään klikattavat teksti ja katsotaan onko niitä painettu
        for(ClickableText click : clickableTexts) {
            click.update(batch);
            if(click.checkClicked(textCam)) //Jos jotain on klikattu, katsotaan mitä niistä
            if(click.getText().equals(clr)) { //Tässä tapauksessa on vain clearscreen, mutta voisi olla useampi
                clearScreen();//Jos on, niin tyhjätään näyttö
            }
        } //Piirretään muut tekstit
        //font.draw(batch, "ILOINEN", 275,275);
        //font.draw(batch, "Piirra hymynaama hopso :)", 200, 250);
        if(!isEmpty) { //Jos pixText tekstuuri ei ole tyhjä, niin piirretään se seuraavaksi
            batch.draw(pixText, 0, 0);
        }
        batch.end();

        if(Gdx.input.isTouched()) { //Jos jotain inputtia on painettu, ajetaan update metodi
            update();
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 vec=new Vector3(screenX,screenY, 0); //Painetaan hiirellä/sormella, niin otetaan siitä kohtaa X ja Y koordinaatit
        textCam.unproject(vec); // Muutetaan nämä X ja Y koordinaatit pikselikoordinaateista kameran koordinaatteihin. (Tai toistepäin, emt...)
        touched = true; //Touched trueksi
        startX = vec.x; //Tallennetaan nämä muunnellut X ja Y koordinaatit startX ja startY floatteihin
        startY = vec.y;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touched = false; //Kun sormi/hiirennapin nostetaan, laitetaan booleanit falseksi
        moved = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 vec=new Vector3(screenX,screenY, 0); //Kun hiirtä/sormea liikutetaan, otetaan päivitetyt koordinaatit
        textCam.unproject(vec); //muunnetaan ne taas oikeiksi koordinaateiksi

        newX = vec.x; //Tallennetaan nämä newX ja newY floatteihin
        newY = vec.y;
        moved = true; //muutetaan moved trueksi

        return false;
    }

    public void update() {
        if(touched) {
            if (moved) {
                if(isEmpty) { //Jos tämä on ensimmäinen kerta täällä, laitetaan isEmpty falseksi.
                    isEmpty = false;
                } //Jos touched ja moved ovat kummatkin true, mennään tänne. Toisinsanoen pitää tapahtua jotain liikettä, ennenkuin lähdetään piirtämään.

                //Piirretään viiva startX ja pelialueekorkeus - startY koordinaateista newX ja pelialueenkorkeus - newY koordinaatteihin
                map.drawLine((int) startX, (int) (wHeight - startY), (int) newX, (int) (wHeight - newY)); //Y koordinatti pitää muuttaa koska fuck logic, pixmapin 0,0 koordinaatti on vasen ylänurkka, kun taas kameran 0,0 vasen alanurkka.
                startX = newX; //muutetaan startX ja startY koordinaatteihin, mihin viimeisin viiva päättyi.
                startY = newY;
                if(pixText!=null) { //Jos meillä on jo tekstuuri piirrossa, niin poistetaan tämä vanha.
                    pixText.dispose();
                }
                pixText = new Texture(map); //ja luodaan uusi
            }

        }
    }

    public void clearScreen() { //clearscreen nappia kun painetaan, ajetaan tämä metodi
        map.setColor(Color.CLEAR); //laitetaan pixmapin väri läpinäkyväksi
        map.fill(); //Täytetään pixmap läpinäkyvällä värillä
        map.setColor(Color.WHITE); //vaihdetaan väri takaisin valkoiseksi
        isEmpty = true; //Lopetetaan vanhan tekstuurin renderöinti
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
