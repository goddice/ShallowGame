import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import java.util.Random;

/**
 * Created by Goddice on 9/20/2016.
 */
public abstract class Monster extends Movable {

    public class Vector2D
    {
        float x;
        float y;
        Vector2D(float m_x, float m_y)
        {
            this.x = m_x;
            this.y = m_y;
        }

        public double lengh()
        {
            return Math.sqrt(x * x + y * y);
        }
    }

    protected String type;
    protected final Vector2D [] directions = {
            new Vector2D( 0,  1),
            new Vector2D( 0, -1),
            new Vector2D( 1,  0),
            new Vector2D(-1,  0),
            new Vector2D( 1,  1),
            new Vector2D( 1, -1),
            new Vector2D(-1,  1),
            new Vector2D(-1, -1),
            new Vector2D( 0,  0)
    };

    Monster(String ref, TiledMap map) {
        super(ref, map);
    }

    public String getType() {
        return this.type;
    }

    protected Vector2D genRandomDirection()
    {
        Random random = new Random();
        int idx = random.nextInt(directions.length);
        return new Vector2D(directions[idx].x, directions[idx].y);
    }

    abstract void update(Player player, int delta);

    void dealth() {}


    @Override
    public void draw(Graphics g) {
        super.draw(g);
        this.renderHealthBar(g);
    }
}
