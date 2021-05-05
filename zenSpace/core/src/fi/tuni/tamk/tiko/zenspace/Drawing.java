/**
 * This file was created by:
 * @author Petr H.
 * Edited to fit other classes by:
 * @author Eemil V.
 * <p>
 * Copyright (c) 2021.
 */

package fi.tuni.tamk.tiko.zenspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles rendering what the player draws, the objective circles,
 * checking if player is drawing close enough to the lines
 * and checking if the player has cleared the obstacle.
 */
public class Drawing extends InputAdapter implements Screen {

    /**
     * Instance of the main class of the game {@link zenSpace}
     */
    private zenSpace gme;

    /**
     * An ExtendViewport used to position the cameras correctly {@link ExtendViewport}
     */
    private ExtendViewport scrnView;

    /**
     * {@link Stage}
     */
    private Stage stg;

    /**
     * {@link Table}
     */
    private Table tbl;

    /**
     * First point coordinate X (where player starts drawing)
     */
    private float startX;

    /**
     * First point coordinate Y (where player starts drawing)
     */
    private float startY;

    /**
     * The next point coordinate X (when player has dragged their finger on the screen)
     */
    private float newX;

    /**
     * The next point coordinate Y (when player has dragged their finger on the screen)
     */
    private float newY;

    /**
     * Helper variable used to place the top banner into the spot on the screen.
     */
    private float dynamicUnitScale;

    /**
     * Helper variable used to change the width of the line that player draws
     */
    private float lineWidth = 4f;

    /**
     * Helper variable used to determine the maximum distance
     * the player can draw away from the desired line.
     */
    private float maxDistance = 30f;

    /**
     * Variable used to determine the size of the small circles rendered on the screen
     */
    private float ballRadius = 9f;

    /**
     * Variable to determine the minimum distance between player drawn line and the ellipses.
     */
    private float winDistance = 17.5f;

    /**
     * Helper variable used to check if the screen has been touched
     */
    private boolean touched = false;

    /**
     * Helper variable used to check if player has dragged their finger on the screen
     */
    private boolean moved = false;

    /**
     * Helper variable that is used to check if
     * the current drawing is the first shape the player is drawing or not.
     */
    private boolean firstShape = true;

    /**
     * Boolean that is used to check if the distance of the player drawn line is OK.
     */
    private boolean isDistanceOk;

    /**
     * The point where the player touches the screen.
     */
    private Vector2 firstPoint;

    /**
     * The next point after finger has been dragged.
     */
    private Vector2 inputPoint;

    /**
     * Instance of BundleHandler that is used to manage all the assets. {@link BundleHandler}
     */
    private BundleHandler bundle;

    /**
     * An Instance of InputMultiplexer {@link InputMultiplexer}
     */
    private InputMultiplexer inputMultiplexer;

    /**
     * An instance of a TiledMap used to create the drawing obstacle. {@link TiledMap}
     */
    private TiledMap tiledMap;

    /**
     * Renderer used to render the TiledMap {@link TiledMapRenderer}
     */
    private TiledMapRenderer tiledRenderer;

    /**
     * Images used by this class
     */
    private Image img, banner;

    /**
     * Helper variable used to prevent the update() method from creating multiple new screen instances
     * when the obstacle has been cleared.
     */
    private Boolean shouldRender = true;

    /**
     * Array of TiledMap point objects used to determine where player should (is able) to draw.
     */
    Array<Vector2> mapPointObjects;

    /**
     * Array of TiledMap ellipse objects used to render the small circles
     * and to check if player has cleared the obstacle.
     */
    Array<Ellipse> ellipseArray;

    /**
     * ShapeRenderer used to render the lines and circles {@link ShapeRenderer}
     */
    private ShapeRenderer sr;

    /**
     * Instance of the HUD class {@link HUD}
     */
    private HUD hud;

    /**
     * Tracker that is used to place the "points" array into the next slot of the 2D array.
     */
    private int tracker = 0;

    /**
     * ArrayList in which all the points of one drawing are collected to,
     * meaning all the points between each toucDown and touchUp.
     */
    List<Vector2> points = new ArrayList<>();

    /**
     * 2D Vector2 Array in which all the seperate points arraylists are saved to, every time touchUp is called.
     */
    Vector2[][] array2D = new Vector2[200][3000];


    /**
     * Constructor for the screen
     * @param game zenspace game that the screen is based on
     */
    public Drawing(zenSpace game) {
        // Initialize all the needed elements
        gme = game;
        hud = gme.getHud();
        bundle = gme.getBundle();
        scrnView = gme.getScrnView();
        stg = new Stage(scrnView);
        img = gme.generateFade();
        tiledMap = bundle.getTiledMap(gme.getEste().getEste());
        tiledRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1);
        dynamicUnitScale = scrnView.getWorldHeight() - gme.getwHeight();
        sr = new ShapeRenderer();
        tbl = new Table();
        tbl.setFillParent(true);
        mapPointObjects = new Array<>();
        ellipseArray = new Array<>();
        inputMultiplexer = new InputMultiplexer();
        banner = new Image(new NinePatchDrawable(bundle.getUiAtlas().createPatch("pen_test")));

        // Setup the inputprocessors
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stg);
        inputMultiplexer.addProcessor(hud.stg);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Translate TiledMap objects into the corresponding arrays
        tiledMapPointsToArray("PiirtoPisteet", mapPointObjects);
        tiledMapEllipsesToArray("VoittoPisteet", ellipseArray);

        // Add all the needed elements to table and the stage
        tbl.add(banner).expand().top().height(dynamicUnitScale + 30f).width(scrnView.getWorldWidth());
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
        if (gme.getEste().getEste().equalsIgnoreCase("friend")
                || gme.getEste().getEste().equalsIgnoreCase("parrot")
                || gme.getEste().getEste().equalsIgnoreCase("paper")
                || gme.getEste().getEste().equalsIgnoreCase("penguin")) {
            sr.setColor(48/255f, 103/255f, 154f, 1f);
        } else {
            sr.setColor(1f, 1f, 1f, 1f);
        }
        drawLines();
        if(gme.getEste().getEste().equalsIgnoreCase("friend")
                || gme.getEste().getEste().equalsIgnoreCase("parrot")) {
            sr.setColor(48/255f, 103/255f, 154f, 1f);
        } else {
            sr.setColor(1f, 1f, 1f, 1f);
        }
        drawEllipses();
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

    /**
     * This method is used to reset the all the drawings the player has made.
     */
    public void resetDrawing() {
        points.clear();
        for (int i = 0; i < array2D.length - 1; i++) {
            for (int j = 0; j < array2D[i].length - 1; j++) {
                if (array2D[i][j] != null && array2D[i][j + 1] != null)
                    array2D[i][j] = null;
            }
        }

    }

    /**
     * This method is used to check if the player is drawing close enough to the desired line.
     * Uses {@link Vector2} dst() method for calculating the distance.
     * @param point Players inputPoint that is checked against the mapPointObjects for the distance
     * @return true if the distance is below or equal to maxDistance, false if not.
     */
    public boolean checkDistanceToLinePoints(Vector2 point) {
        for (Vector2 p : mapPointObjects) {
            if (p.dst(point) <= maxDistance) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to check if player has visited (drawn close enough to) an ellipse.
     * Uses {@link Vector2} dst() method for calculating the distance.
     * @param point Players inputPoint that is checked against the ellipseArray
     *             (ellipse positions) for the distance.
     * @return false if not all ellipses have been visited, true if all of them have.
     */
    public boolean checkWinEllipsesForVisit(Vector2 point) {
        for (int i = 0; i < ellipseArray.size; i++) {
            float x = ellipseArray.get(i).x;
            float y = ellipseArray.get(i).y;
            Vector2 vec = new Vector2(x, y);
            if (vec.dst(point) <= winDistance) {
                ellipseArray.removeIndex(i);
            }
        }
        if (!(ellipseArray.size == 0)) {
            return false;
        }
        return true;
    }

    /**
     * This method is used to translate the positions of the TiledMap point objects into the desired
     * Vector2 targetArray.
     * @param layername The name of the layer taht holds the desired objects in the map
     * @param targetArray The Vector2 targetArray that will contain the positions
     */
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

    /**
     * This method is used to translate the TiledMap ellipse objects into ellipses
     * that are saved in the targetArray of ellipses.
     * @param layername The name of the layer that holds the desired objects in the map
     * @param targetArray The Ellipse targetArray that will contain the ellipses
     */
    public void tiledMapEllipsesToArray(String layername, Array<Ellipse> targetArray) {
        MapLayer ellipseObjectLayer = tiledMap.getLayers().get(layername);
        MapObjects ellipseObjects = ellipseObjectLayer.getObjects();
        Array<EllipseMapObject> ellipses = ellipseObjects.getByType(EllipseMapObject.class);
        for (EllipseMapObject e : ellipses) {
            targetArray.add(e.getEllipse());
        }
    }

    /**
     * This method is used to render the lines between drawn points and to render the ellipses.
     */
    private void drawLines() {
        // If the current drawing is the first drawing or first shape then the points ArrayList is rendered.
        if (firstShape) {
            for (int i = 0; i < points.size() - 1; i++) {
                sr.rectLine(points.get(i), points.get(i + 1), lineWidth);
            }
            // Else the 2D array of saved points arrays is rendered
        } else {
            for (int i = 0; i < array2D.length - 1; i++) {
                for (int j = 0; j < array2D[i].length - 1; j++) {
                    if (array2D[i][j] != null && array2D[i][j + 1] != null) {
                        sr.rectLine(array2D[i][j], array2D[i][j + 1], lineWidth);
                    }
                }
            }
        }
    }

    /**
     * This method is used to render the ellipses.
     */
    private void drawEllipses() {
        // Each Ellipse in ellipseArray is rendered
        for (Ellipse e : ellipseArray) {
            sr.circle(e.x, e.y, ballRadius);
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // When the screen is touched the coordinates are collected as a point
        Vector3 vec = new Vector3(screenX, screenY, 0);

        // Translate the point from screen coordinates to world space.
        scrnView.getCamera().unproject(vec);

        // set touched as true
        touched = true;

        // Save the unprojected coordinates into floats (startX and startY)
        startX = vec.x;
        startY = vec.y;

        // Make a point out of those coordinates and if the distance is ok, add it into the points ArrayList
        firstPoint = new Vector2(startX, startY);
        if (isDistanceOk) {
            points.add(firstPoint);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // When the finger is lifted off the screen change all the booleans to false
        // add +1 to tracker and clear the points ArrayList
        touched = false;
        moved = false;
        tracker++;
        points.clear();
        firstShape = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // When the finger is dragged the updated coordinates are collected into a Vector3 point
        Vector3 vec = new Vector3(screenX, screenY, 0);

        // Translate the point from screen coordinates to world space.
        scrnView.getCamera().unproject(vec);

        // Save the unprojected coordinates into floats (newX and newY)
        newX = vec.x;
        newY = vec.y;

        // set moved as true
        moved = true;

        // Check if the distance is OK
        isDistanceOk = checkDistanceToLinePoints(new Vector2(newX, newY));

        // Save the coordinates as a Vector2 point
        inputPoint = new Vector2(newX, newY);

        if (isDistanceOk) {
            // Change "points" ArrayList into "vectors" Array
            Vector2[] vectors = points.toArray(new Vector2[0]);
            // Tallenetaan uusi Vector2 array 2DArrayhyn
            // Save the new Vector2 Array (vectors) into the 2D array of Vector2 Arrays
            for (int i = 0; i < vectors.length; i++) {
                array2D[tracker][i] = vectors[i];
            }
            // If the distance is not ok, reset: the drawings, tracker, firstShape boolean and the ellipseArray
        } else {
            resetDrawing();
            tracker = 0;
            firstShape = true;
            tiledMapEllipsesToArray("VoittoPisteet", ellipseArray);
        }

        return false;
    }

    /**
     * This method is used to update the drawing and change the screen if the obstacle is cleared
     */
    private void update() {
        if (touched && moved) {
            if (isDistanceOk) {
                points.add(inputPoint);
                // Change startX and startY into the coordinates where the previous line ended
                startX = newX;
                startY = newY;
            }
            // If all of the ellipses have been visited change the screen into ResultScreen
            if (checkWinEllipsesForVisit(inputPoint) && shouldRender) {
                inputMultiplexer.clear();
                img.addAction(Actions.sequence(Actions.fadeIn(gme.getFadeIn()), Actions.delay(gme.getFadeIn()), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        shouldRender = false;
                        gme.getEste().setBooleans(false, true);
                        gme.setScreen(new Resultscreen(gme));
                    }
                })));
            }
        }
    }
}
//End of file
