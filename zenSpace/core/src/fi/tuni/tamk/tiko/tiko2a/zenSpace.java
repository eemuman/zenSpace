/*
 * This file was created by:
 * @Eemil V.
 *Ime Pekka munaa
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Locale;


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
	private int wWidth = 480;
	private int wHeight = 800;
	private BundleHandler bundle;
	private int curLevelInt;
	private int curBackground;
	private HUD hud;
	private Este este;
	private float fadeIn = 0.25f;
	private Fade fade;
	public Prefs prefs;
	private int curEsteInt;


	private String[] backGrounds = {"anger.atlas", "fear.atlas", "joy.atlas", "depression.atlas"};

	private boolean fin = true;
	/**
	 * Täällä initializetaan kaikki yllä luodut.
	 */
	@Override
	public void create() {
		prefs = new Prefs();
		curLevelInt = 1;
		curBackground = 0;
		bundle = new BundleHandler();
		bundle.loadAssets();
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		textCam = new OrthographicCamera();
		textCam.setToOrtho(false, wWidth, wHeight);
		scrnView = new ExtendViewport(wWidth,wHeight, textCam);
		cam.setToOrtho(true, 15, 10);
		fade = new Fade();
		hud = new HUD(this);
		este = new Este(this);

		setScreen(new newMainMenu(this)); //Luontien jälkeen lähretää MainMenuun...
	}

	public void render () {
		super.render();
	}

	public Image generateFade() {
		return fade.getImage();
	}

	/**
	 * Seuraavaksi gettereitä, jolla saadaan sit noi kaikki niihin muihin luokkiin haettua. Koska ne ovat privateja..
	 *
	 */
	public SpriteBatch getBatch() {
		return batch;
	}
	public ExtendViewport getScrnView() {
		return scrnView;
	}
	public OrthographicCamera getTextCam() {
		return textCam;
	}
	public int getwWidth() {
		return wWidth;
	}
	public boolean isFin() {
		return fin;
	}
	public void setFin() {
		fin = !fin;
	}
	public int getCurLevel() {
		return curLevelInt;
	}
	public void setCurLevelInt(int curLevelInt){
		this.curLevelInt = curLevelInt;
	}
	public String[] getBackGrounds() {
		return backGrounds;
	}
	public int getCurBackground() {
		return curBackground;
	}
	public HUD getHud() {
		return hud;
	}
	public Este getEste() {return este;}
	public void setCurBackground(int curBackground) {
		this.curBackground = curBackground;
	}
	public BundleHandler getBundle() {
		return bundle;
	}

	public int getwHeight() {
		return wHeight;
	}

	public float getFadeIn() {
		return fadeIn;
	}

	public int getCurEsteInt() {
		return este.getEsteInt();
	}

}
