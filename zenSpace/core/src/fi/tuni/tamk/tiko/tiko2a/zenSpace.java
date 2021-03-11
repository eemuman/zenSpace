package fi.tuni.tamk.tiko.tiko2a;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class zenSpace extends Game {
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private OrthographicCamera textCam;
	private BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		textCam = new OrthographicCamera();
		textCam.setToOrtho(false, 800, 480);
		cam.setToOrtho(true, 15, 10);
		font = new BitmapFont(Gdx.files.internal("fontti.fnt"), false);
		font.getData().setScale(0.25f);
		setScreen(new MainMenu(this));
	}


	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCam() {
		return cam;
	}


	public BitmapFont getFont() {
		return font;
	}
	public OrthographicCamera getTextCam() {
		return textCam;
	}

}
