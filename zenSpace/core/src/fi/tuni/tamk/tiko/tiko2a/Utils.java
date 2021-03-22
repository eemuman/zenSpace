package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Utils {
    /**
     * The direction that the sprite is facing.
     */
    static boolean direction = true;

    /**
     * Change orientation of the frames of the parameter TextureRegion
     * You can choose the flip axis (x or y).
     * @param animation TextureRegion that is flipped.
     */

    public static void flip(Animation<TextureRegion> animation) {
        TextureRegion [] regions = animation.getKeyFrames();
        for(TextureRegion r : regions) {
            r.flip(true, false);
        }
    }

    /**
     * Change boolean "direction" to the given parameter
     * if it is different from the current value of the boolean "direction".
     * @param dir boolean value to be changed to
     */

    public static void changeDirection(boolean dir) {
        if(dir != direction) {
            direction = dir;
        }
    }

    /**
     * Scale a Rectangle object to wanted size
     * @param r rectangle to be scaled
     * @param scale the desired scale
     * @return scaled rectangle
     */
    public static Rectangle scaleRectangle(Rectangle r, float scale) {
        Rectangle rec = new Rectangle();
        rec.x = r.x * scale;
        rec.y = r.y * scale;
        rec.width = r.width * scale;
        rec.height = r.height * scale;
        return rec;
    }

    /**
     * Transform 2D TextureRegionArray to 1D TextureRegionArray
     * @param arr 2D array to be transformed
     * @param ROWS number of rows in 2D array
     * @param COLS number of columns in 2D array
     * @return 1D TextureRegionArray with all the elements of 2D array in it
     */

    public static TextureRegion [] transformTo1D(TextureRegion[][] arr, int ROWS, int COLS) {
        TextureRegion[] currentFrames = new TextureRegion[(ROWS * COLS)];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                currentFrames[index++] = arr[i][j];
            }
        }
        return currentFrames;
    }

    public static TextureRegion [][] setRegionArray(Texture t, int ROWS, int COLS) {
        return TextureRegion.split(t, t.getWidth()/ COLS, t.getHeight()/ ROWS);
    }

    public static Animation<TextureRegion> setAnimation(TextureRegion[] frames, Animation<TextureRegion> animation, float frameDuration) {
        animation = new Animation<TextureRegion>(frameDuration/60f, frames);
        return animation;
    }


}
