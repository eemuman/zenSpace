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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.List;

public class Piirto extends InputAdapter implements Screen {

    private TextButton reset;
    private Table tbl, overlay;

    private zenSpace gme;
    private ExtendViewport scrnView;
    private Skin skin;
    private Stage stg;

    private float startX;
    private float startY;
    private float newX;
    private float newY;
    private float dynamicUnitScale;
    private float lineWidth = 3f;
    private float minDistance = 30f;
    private float ballRadius = 7.5f;

    private boolean touched = false;
    private boolean moved = false;
    private boolean firstShape = true;
    private boolean isDistanceOk;

    private Vector2 firstPoint;
    private Vector2 inputPoint;
    private BundleHandler bundle;

    private InputMultiplexer inputMultiplexer;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledRenderer;
    private Image img, banner;

    private Boolean shouldRender = true;

    Array<Vector2> mapPointObjects;
    Array<Ellipse> ellipseArray;

    AtlasRegion bgTexture;

    private ShapeRenderer sr;
    private HUD hud;

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
    Vector2[][] array2D = new Vector2[200][3000];

    /**
     * Constructor for the screen
     * @param game  zenspace peli, jonka pohjalta sceeni luodaan
     * @param bgTexture
     */
    public Piirto(zenSpace game, final AtlasRegion bgTexture) {
        this.bgTexture = bgTexture;
        gme = game;
        hud = gme.getHud();
        bundle = gme.getBundle();
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        img = gme.generateFade();
        Gdx.app.log("HERE", "HEREP");
        tiledMap = bundle.getTiledMap(gme.getEste().getEste());
        tiledRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1);
        dynamicUnitScale = scrnView.getWorldHeight() - gme.getwHeight();
        skin = bundle.getUiSkin();
        sr = new ShapeRenderer();
        tbl = new Table();
        overlay = new Table();
        tbl.setFillParent(true);


        mapPointObjects = new Array<>();
        ellipseArray = new Array<>();
        tiledMapPointsToArray("PiirtoPisteet" , mapPointObjects);
        tiledMapEllipsesToArray("VoittoPisteet", ellipseArray);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stg);
        inputMultiplexer.addProcessor(hud.stg);
        Gdx.input.setInputProcessor(inputMultiplexer);

        banner = new Image(new NinePatchDrawable(bundle.getUiAtlas().createPatch("pen_test")));

        tbl.add(banner).expand().top().height(dynamicUnitScale+30f).width(scrnView.getWorldWidth());
        tbl.add(reset).expand().top().right().height(dynamicUnitScale).padLeft(-490f);

        stg.addActor(tbl);
        stg.addActor(img);
        img.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(gme.getFadeIn())));
        tbl.addAction(Actions.fadeIn(gme.getFadeIn()));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stg.act(Gdx.graphics.getDeltaTime());
        tiledRenderer.setView((OrthographicCamera) scrnView.getCamera());
        tiledRenderer.render();
        sr.setProjectionMatrix(scrnView.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        draw();
        sr.end();
        update();
        stg.act(delta);
        stg.draw();
        hud.render(delta);
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
        stg.clear();
        stg.dispose();
    }

    public void resetDrawing() {
        points.clear();
        for (int i = 0; i < array2D.length - 1; i++) {
            for (int j = 0; j < array2D[i].length - 1; j++) {
                if (array2D[i][j] != null && array2D[i][j + 1] != null)
                    array2D[i][j] = null;
            }
        }

    }

    public boolean checkDistanceToLinePoints(Vector2 point) {
        for (Vector2 p : mapPointObjects) {
            if (p.dst(point) <= minDistance) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWinEllipsesForVisit(Vector2 p) {
        for (int i = 0; i < ellipseArray.size; i++) {
            float x = ellipseArray.get(i).x;
            float y = ellipseArray.get(i).y;
            Vector2 vec = new Vector2(x, y);
            if (vec.dst(p) <= minDistance) {
                ellipseArray.removeIndex(i);
            }
        }
        if (!(ellipseArray.size == 0)) {
            return false;
        }
        return true;
    }

    public void tiledMapPointsToArray(String layername, Array<Vector2> targetArray) {
        MapLayer pointObjectLayer = tiledMap.getLayers().get(layername);
        MapObjects pointObjects = pointObjectLayer.getObjects();
        Array<RectangleMapObject> points = pointObjects.getByType(RectangleMapObject.class);
        for (RectangleMapObject r : points) {
            float x = r.getRectangle().getX();
            float y = r.getRectangle().getY();
            targetArray.add(new Vector2(x, y));
        }
    }

    public void tiledMapEllipsesToArray(String layername, Array<Ellipse> targetArray) {
        MapLayer ellipseObjectLayer = tiledMap.getLayers().get(layername);
        MapObjects ellipseObjects = ellipseObjectLayer.getObjects();
        Array<EllipseMapObject> ellipses = ellipseObjects.getByType(EllipseMapObject.class);
        for (EllipseMapObject e : ellipses) {
            targetArray.add(e.getEllipse());
        }
    }

    private void draw() {
        if (firstShape) {
            for (int i = 0; i < points.size() - 1; i++) {
                sr.rectLine(points.get(i), points.get(i + 1), lineWidth);
            }
        } else {
            for (int i = 0; i < array2D.length - 1; i++) {
                for (int j = 0; j < array2D[i].length - 1; j++) {
                    if (array2D[i][j] != null && array2D[i][j + 1] != null) {
                        sr.rectLine(array2D[i][j], array2D[i][j + 1], lineWidth);
                    }
                }
            }
        }
        for (Ellipse e : ellipseArray) {
            sr.circle(e.x, e.y, ballRadius);
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 vec = new Vector3(screenX, screenY, 0); //Painetaan hiirellä/sormella, niin otetaan siitä kohtaa X ja Y koordinaatit
        scrnView.getCamera().unproject(vec); // Muutetaan nämä X ja Y koordinaatit pikselikoordinaateista kameran koordinaatteihin.
        touched = true; //Touched trueksi
        startX = vec.x; //Tallennetaan nämä muunnellut X ja Y koordinaatit startX ja startY floatteihin
        startY = vec.y;
        // Tallenetaan aloituspiste Vector2 ja lisätään se points ArrayListiin
        firstPoint = new Vector2(startX, startY);
        if (isDistanceOk) {
            points.add(firstPoint);
        }

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
        Vector3 vec = new Vector3(screenX, screenY, 0); //Kun hiirtä/sormea liikutetaan, otetaan päivitetyt koordinaatit
        scrnView.getCamera().unproject(vec); //muunnetaan ne taas oikeiksi koordinaateiksi
        newX = vec.x; //Tallennetaan nämä newX ja newY floatteihin
        newY = vec.y;
        moved = true; //muutetaan moved trueksi
        isDistanceOk = checkDistanceToLinePoints(new Vector2(newX, newY));
        // Tallenetaan aina seuraava piste Vector2
        inputPoint = new Vector2(newX, newY);
        if (isDistanceOk) {
            // Muutetaan arrayList points Vector2 arrayksi
            Vector2[] vectors = points.toArray(new Vector2[0]);
            // Tallenetaan uusi Vector2 array 2DArrayhyn
            for (int i = 0; i < vectors.length; i++) {
                array2D[tracker][i] = vectors[i];
            }
        } else {
            resetDrawing();
            tracker = 0;
            firstShape = true;
            tiledMapEllipsesToArray("VoittoPisteet", ellipseArray);
        }

        return false;
    }

    private void update() {
        if (touched && moved) {
            if (isDistanceOk) {
                points.add(inputPoint);
                startX = newX; //muutetaan startX ja startY koordinaatteihin, mihin viimeisin viiva päättyi.
                startY = newY;
            }
            if (checkWinEllipsesForVisit(inputPoint) && shouldRender) {
                inputMultiplexer.clear();
                img.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()),Actions.delay(gme.getFadeIn()) ,Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.log("!H",  " !W ");
                        shouldRender = false;
                     //   dispose();
                        gme.getEste().setBooleans(false, true);
                        gme.setScreen(new Resultscreen(gme, bgTexture));
                    }
                })));
            }
        }
    }
}
//End of file
