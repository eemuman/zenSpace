/*
 * This file was created by:
 * @Petr H. 
 * Edited to fit other classes by Eemil V.
 *
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.List;

public class Piirto extends InputAdapter implements Screen {

    private TextButton reset, back;
    private Table tbl;
    private Label lbl;

    private zenSpace gme;
    private ExtendViewport scrnView;
    private Skin skin;
    private Stage stg;

    private float startX;
    private float startY;
    private float newX;
    private float newY;

    private boolean touched = false;
    private boolean moved = false;
    private boolean firstShape = true;

    private Vector2 firstPoint;
    private Vector2 inputPoint;

    private InputMultiplexer inputMultiplexer;

    private ShapeRenderer sr;

    /**
     * Tracker jolla tallennettava "points" piirros
     * laitetaan aina seuraavaan Arrayhyn
     */
    private int tracker = 0;

    /**
     * ArrayList johon kerätään aina yhden piirtokerran
     * eli touchDown ja touchUp välissä sijoitetut Vector2 pisteet
     */
    List<Vector2> points = new ArrayList<>();

    /**
     * 2D Vector2 Array johon yhden piirtokerran arraylist "points"
     * tallennetaan aina touchUpin tapahtuessa
     */
    Vector2 [][] array2D = new Vector2 [200][3000];

    public Piirto(zenSpace game) {

        gme = game;
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        skin = gme.getSkin();
        sr = new ShapeRenderer();
        tbl = new Table();
        tbl.setFillParent(true);


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stg);
        Gdx.input.setInputProcessor(inputMultiplexer);


        reset = new TextButton("CLEARSCREEN", skin, "TextButtonSmall");
        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = 0; i < array2D.length - 1; i++) {
                    for (int j = 0; j < array2D[i].length - 1; j++) {
                        if(array2D[i][j] != null && array2D[i][j+1] != null)
                            array2D[i][j] = null;
                    }
                }
                tracker = 0;
                firstShape = true;
            }
        });
        back = new TextButton("MAINMENU", skin, "TextButtonSmall");
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                gme.setScreen(new newMainMenu(gme));
            }
        });
        tbl.add(back).expand().top().left().width(225).height(100);
        tbl.add(reset).expand().top().right().width(225).height(100);
        stg.addActor(tbl);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();
        sr.setProjectionMatrix(scrnView.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        drawLines();
        sr.end();
        update();

    }

    @Override
    public void resize(int width, int height) {
        stg.getViewport().update(width, height, true);
        stg.getCamera().update();
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

    private void drawLines() {
        if(firstShape) {
            for (int i = 0; i < points.size() - 1; i++) {
                sr.rectLine(points.get(i), points.get(i+1), 5f);
            }
        } else {
            for (int i = 0; i < array2D.length - 1; i++) {
                for (int j = 0; j < array2D[i].length - 1; j++) {
                    if(array2D[i][j] != null && array2D[i][j+1] != null)
                        sr.rectLine(array2D[i][j], array2D[i][j+1], 5f);
                }
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 vec=new Vector3(screenX,screenY, 0); //Painetaan hiirellä/sormella, niin otetaan siitä kohtaa X ja Y koordinaatit
        scrnView.getCamera().unproject(vec); // Muutetaan nämä X ja Y koordinaatit pikselikoordinaateista kameran koordinaatteihin. (Tai toistepäin, emt...)
        touched = true; //Touched trueksi
        startX = vec.x; //Tallennetaan nämä muunnellut X ja Y koordinaatit startX ja startY floatteihin
        startY = vec.y;
        // Tallenetaan aloituspiste Vector2 ja lisätään se points ArrayListiin
        firstPoint = new Vector2(startX, startY);
        points.add(firstPoint);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touched = false; //Kun sormi/hiirennapin nostetaan, laitetaan booleanit falseksi
        moved = false;
        tracker++;
        points.clear();
        firstShape = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 vec=new Vector3(screenX,screenY, 0); //Kun hiirtä/sormea liikutetaan, otetaan päivitetyt koordinaatit
        scrnView.getCamera().unproject(vec); //muunnetaan ne taas oikeiksi koordinaateiksi

        newX = vec.x; //Tallennetaan nämä newX ja newY floatteihin
        newY = vec.y;
        // Tallenetaan aina seuraava piste Vector2
        inputPoint = new Vector2(newX, newY);
        Vector2 [] v = points.toArray(new Vector2 [0]);
        for (int i = 0; i < v.length; i++) {
            array2D[tracker][i] = v[i];
        }
        moved = true; //muutetaan moved trueksi

        return false;
    }

    private void update() {
        if(touched && moved) {
            points.add(inputPoint);
            startX = newX; //muutetaan startX ja startY koordinaatteihin, mihin viimeisin viiva päättyi.
            startY = newY;
        }
    }
}
//End of file
