/**
 * This file was created by:
 * @author Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;


import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;



/**
 * Pelin pohjaluokka, täällä luodaan kaikki asiat, mitä käytetään muissa luokissa
 *
 * Spritebatchi, kamerat, fontit, ym.
 */
public class zenSpace extends Game {
	private SpriteBatch batch;
	private ExtendViewport scrnView;
	private OrthographicCamera textCam;
	private int wWidth = 480;
	private int wHeight = 800;
	private static BundleHandler bundle;
	private int curLevelInt, curBackground, randEste;
	private HUD hud;
	private Obstacle este;
	private float fadeIn = 0.25f;
	private Fade fade;
	public Prefs prefs;



	private String[] backGrounds = {"anger","anxiety","sadness","fear","shame","depression","joy"};



	/**
	 * Täällä initializetaan kaikki yllä luodut.
	 */
	@Override
	public void create() {
		prefs = new Prefs();
		randEste = 0;
		curLevelInt = 1;
		curBackground = 0;
		bundle = new BundleHandler();
		bundle.loadAssets();
		Sounds.loadSounds();
		batch = new SpriteBatch();
		textCam = new OrthographicCamera();
		textCam.setToOrtho(false, wWidth, wHeight);
		scrnView = new ExtendViewport(wWidth,wHeight, textCam);
		fade = new Fade();
		hud = new HUD(this);
		este = new Obstacle(this);

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
	public int getwWidth() {
		return wWidth;
	}
	public boolean isFin() {
		return prefs.getFin();
	}
	public void setFin() {
		prefs.setFin(!prefs.getFin());
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
	public Obstacle getEste() {return este;}
	public void setCurBackground(int curBackground) {
		this.curBackground = curBackground;
	}
	public static BundleHandler getBundle() {
		return bundle;
	}

	public int getwHeight() {
		return wHeight;
	}

	public float getFadeIn() {
		return fadeIn;
	}

	public int getCurEsteInt() {
		return randEste;
	}

	public String getEsteString() {return este.getEste();}

	public void setRandEste(int randEste) {
		this.randEste = randEste;
	}

}
