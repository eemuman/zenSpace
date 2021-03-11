package fi.tuni.tamk.tiko.tiko2a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class ClickableText {
    private String text;
    private BitmapFont font;

    private GlyphLayout layout;

    private int posX;
    private int posY;

    public ClickableText(String text, int posX, int posY, BitmapFont font) {
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.font = font;

        layout = new GlyphLayout(this.font, text);
    }

    public void update(SpriteBatch batch, OrthographicCamera camera) {
        font.draw(batch, layout, posX, posY);
    }

    public boolean checkClicked(OrthographicCamera camera)
    {
        if (Gdx.input.justTouched())
        {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (getRectangle().contains(touch.x, touch.y))
            {
                System.out.println(text + " has been clicked.");
                return true;
            }
        }
        return false;
    }

    private Rectangle getRectangle()
    {
        return new Rectangle(posX, posY - (int)layout.height, (int)layout.width, (int)layout.height);
    }

    public String getText() {
        return text;
    }
}

