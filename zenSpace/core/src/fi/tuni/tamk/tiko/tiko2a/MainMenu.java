package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {
    private SpriteBatch  batch;
    private zenSpace game;
    private OrthographicCamera cam;
    private OrthographicCamera textCam;
    private BitmapFont font;

    List<ClickableText> clickableTexts = new ArrayList<>();

    public MainMenu(zenSpace game) {
        this.game = game;
        this.batch = game.getBatch();
        cam = game.getCam();
        font = game.getFont();
        textCam = game.getTextCam();

        clickableTexts.add(new ClickableText("ILOINEN", 100, 250, font));
        clickableTexts.add(new ClickableText("SURULLINEN", 450, 250, font));
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
        font.draw(batch, "VALITSE MIELIALA", 225,350);
        for(ClickableText click : clickableTexts) {
            click.update(batch, textCam);
            if(click.checkClicked(textCam)) {
                if(click.getText().equals("ILOINEN")) {
                    game.setScreen(new Iloinen(game));
                } else if (click.getText().equals("SURULLINEN")) {
                    game.setScreen(new Surullinen(game));
                }
            }
        }
        batch.end();


        batch.setProjectionMatrix(cam.combined);
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
