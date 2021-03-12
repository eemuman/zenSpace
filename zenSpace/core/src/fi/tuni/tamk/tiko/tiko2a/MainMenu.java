/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Menun luokka
 *
 * Luodaan spritebatchit, kamerat, fontit, ym.
 */
public class MainMenu implements Screen {
    private SpriteBatch  batch;
    private zenSpace game;
    private OrthographicCamera cam;
    private OrthographicCamera textCam;
    private BitmapFont font;
    private String ilo = "ILOINEN";
    private String suru = "SURULLINEN";


    List<ClickableText> clickableTexts = new ArrayList<>();

    /**
     *
     * Varastetaan röyhkeästi pääluokalta kaikki kamerat, spritebatchit, fontit, ym.
     * @param game zenSpace pääluokka
     */
    public MainMenu(zenSpace game) {
        this.game = game;
        this.batch = game.getBatch();
        cam = game.getCam();
        font = game.getFont();
        textCam = game.getTextCam();
//Tehdään pari klikattavaa tekstiä
        clickableTexts.add(new ClickableText(ilo, 100, 200, font));
        clickableTexts.add(new ClickableText(suru, 400, 200, font));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(textCam.combined);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        textCam.update();
        batch.begin();
        font.draw(batch, "VALITSE MIELIALA", 210,275);
        for(ClickableText click : clickableTexts) { //For-loopilla käydään kaikki klikattavat tekstit läpi
            click.update(batch); //Ensiksi piirretään
            if(click.checkClicked(textCam)) { //Tarkistetaan onko kyseistä tekstiä klikattu
                if(click.getText().equals(ilo)) { //Jos on klikattu "ILOINEN" tekstiä
                    game.setScreen(new Iloinen(game)); //Vaihdetaan Iloinen peliruutuun
                } else if (click.getText().equals(suru)) { //Jos taas "SURULLINEN" tekstiä
                    game.setScreen(new Surullinen(game)); //vaihdetaan Surullinen peliruutuun
                }
            }
        }
        batch.end();


        batch.setProjectionMatrix(cam.combined); //Piirrettäisiin toisella kameralla jos tarvis....
        batch.begin();
        batch.end();

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
