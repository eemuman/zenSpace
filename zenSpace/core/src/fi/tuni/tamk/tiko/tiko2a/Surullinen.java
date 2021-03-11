package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Surullinen implements Screen, InputProcessor {
    zenSpace game;
    private OrthographicCamera textCam;
    private SpriteBatch batch;
    private BitmapFont font;
    private Pixmap map;
    private OrthographicCamera cam;
    private float startX;
    private float startY;
    private float newX;
    private float newY;
    private int wHeight = 480;
    private boolean touched = false;
    private boolean moved = false;
    private boolean isEmpty = true;

    private Texture pixText;

    List<ClickableText> clickableTexts = new ArrayList<>();

    public Surullinen(zenSpace game) {
        this.game = game;
        textCam = game.getTextCam();
        cam = game.getCam();
        batch = game.getBatch();
        font = game.getFont();
        map = new Pixmap(800, 480, Pixmap.Format.RGBA8888);
        clickableTexts.add(new ClickableText("CLEARSCREEN", 25, 460, font));
        map.setColor(Color.WHITE);

        Gdx.input.setInputProcessor(this);
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

        for(ClickableText click : clickableTexts) {
            click.update(batch, textCam);
            Gdx.app.log("TRUECLEAR", String.valueOf(click.getText().equals("CLEARSCREEN")));
            if(click.checkClicked(textCam))
            if(click.getText().equals("CLEARSCREEN")) {
                clearScreen();
            }
        }
        font.draw(batch, "SURULLINEN", 325,400);
        font.draw(batch, "Piirrä surunaama suris :(", 200, 350);
        if(!isEmpty) {
            batch.draw(pixText, 0, 0);
        }
        batch.end();

        Gdx.app.log("TREUE?=", isEmpty + " " + moved);
        if(Gdx.input.isTouched()) {
            update();
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            Vector3 vec=new Vector3(screenX,screenY, 0);
            textCam.unproject(vec);
            touched = true;
            startX = vec.x;
            startY = vec.y;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touched = false;
        moved = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 vec=new Vector3(screenX,screenY, 0);
        textCam.unproject(vec);

        newX = vec.x;
        newY = vec.y;
        moved = true;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void update() {
        if(touched) {
            if (moved) {
                if(isEmpty) {
                    isEmpty = false;
                }
                    map.drawLine((int) startX, (int) (wHeight - startY), (int) newX, (int) (wHeight - newY));
                startX = newX;
                startY = newY;
                pixText = new Texture(map);
            }
        }
    }
    public void clearScreen() {

        map.setColor(Color.CLEAR);
        map.fill();
        map.setColor(Color.WHITE);
        isEmpty = true;
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

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

}
