import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Goddice on 9/20/2016.
 */
public class Unit {
    protected float posX;
    protected float posY;
    protected Image image;
    protected TiledMap maze;
    private String name;
    protected Camera camera;

    public Unit(String ref, TiledMap map) {
        this.posX = 0;
        this.posY = 0;
        try {
            image = new Image(ref);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        maze = map;

    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getName() {
        return this.name;
    }

    public Image getImage() {
        return this.image;
    }

    public void setCamera(Camera mCamera) {
        this.camera = mCamera;
    }

    public float getX() { return this.posX; }

    public void setX(float x)
    {
        this.posX = x;
    }

    public float getY()
    {
        return this.posY;
    }

    public void setY(float y)
    {
        this.posY = y;
    }

    public void setPos(float x, float y)
    {
        this.setX(x);
        this.setY(y);
    }

    public double dist(Unit other)
    {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void draw(Graphics g) {
        float x = this.posX - camera.getMinX() - image.getWidth() / 2;
        float y = this.posY - camera.getMinY() - image.getHeight() / 2;
        image.draw(x, y);
    }

}
