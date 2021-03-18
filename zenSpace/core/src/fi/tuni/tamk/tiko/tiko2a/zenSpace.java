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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	private int wHeight = 480;
	private int wWidth = 800;
	private Skin skin;
	private Image headerImg;

	private String[] firstStrings = {"Polttaa", "Puristaa", "Vapisuttaa", "Kevyelta", "Raskaalta", "Kuplivalta", "Rennolta", "Jaykalta", "Neutraalilta"};
	private String[] secondStrings = {"Vihainen", "Ylpea", "Iloinen", "Surullinen", "Ahdistaa", "Masentaa", "Pelottaa", "Havettaa", "Inhottaa"};





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
		headerImg = new Image(new Texture("zenSpace.png"));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		skin.getFont("default-font").getData().setScale(0.65f);


		setScreen(new newMainMenu(this)); //Luontien jälkeen lähretää MainMenuun...
	}

	public void render () {
		super.render();
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
	public OrthographicCamera getTextCam() {
		return textCam;
	}
	public Skin getSkin() {
		return skin;
	}
	public Image getHeaderImg() {
		return headerImg;
	}
	public int getwWidth() {
		return wWidth;
	}
	public String[] getFirstStrings() {
		return firstStrings;
	}
	public String[] getSecondStrings() {
		return secondStrings;
	}
}
