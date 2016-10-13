import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by CelongLiu on 10/10/2016.
 */
public class AggressiveMonster extends Monster {
    private Vector2D currenDirection;
    private final double speed = 0.25;

    AggressiveMonster(String ref, TiledMap map) {
        super(ref, map);
        this.type = "Aggressive";
    }

    void update(Player player, int delta) {
        this.timeSinceLastHit += delta;
        if (this.dist(player) <= 150 && this.dist(player) >= 50)
        {
            currenDirection = new Vector2D(player.getX() - this.getX(), player.getY() - this.getY());
            double ds = delta * speed;
            double dx = ds * currenDirection.x / currenDirection.lengh();
            double dy = ds * currenDirection.y / currenDirection.lengh();
            move(dx, dy);
        }

        if (this.dist(player) <= 50)
        {
            this.attack(player);
        }
    }
}
