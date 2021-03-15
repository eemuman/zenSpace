/*
 * This file was created by:
 * @Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Pelin pohjaluokka, täällä luodaan kaikki asiat, mitä käytetään muissa luokissa
 *
 * Spritebatchi, kamerat, fontit, ym.
 */
public class zenSpace extends Game {
	private SpriteBatch batch;
	private ExtendViewport scrnView;
	private OrthographicCamera cam;
	private OrthographicCamera textCam;
	private BitmapFont font, fontH;
	private int wWidth = 640;
	private int wHeight = 320;

	/**
	 * Täällä initializetaan kaikki yllä luodut.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		textCam = new OrthographicCamera();
		textCam.setToOrtho(false, wWidth, wHeight);
		scrnView = new ExtendViewport(wWidth,wHeight, textCam);
		cam.setToOrtho(true, 15, 10);
		font = new BitmapFont(Gdx.files.internal("fontti.fnt"), false);
		fontH = new BitmapFont(Gdx.files.internal("fontti.fnt"), false);//Tässä luodaan fontti, helppo muuttaa, voin näyttää halutessa
		font.getData().setScale(0.15f); //Pikku trikki, jolla saadaan tekstistä vähän sulavempaa on luoda suuri fontti jota downscaletaan pienemmäksi.
		setScreen(new newMainMenu(this)); //Luontien jälkeen lähretää MainMenuun...
	}

	/**
	 * Seuraavaksi gettereitä, jolla saadaan sit noi kaikki niihin muihin luokkiin haettua. Koska ne ovat privateja..
	 *
	 */
	public SpriteBatch getBatch() {
		return batch;
	}
	public OrthographicCamera getCam() {
		return cam;
	}
	public ExtendViewport getScrnView() {
		return scrnView;
	}
	public BitmapFont getFont() {
		return font;
	}
	public BitmapFont getFontH() {
		return fontH;
	}
	public OrthographicCamera getTextCam() {
		return textCam;
	}

}
