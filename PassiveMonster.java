import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by CelongLiu on 10/10/2016.
 */
public class PassiveMonster extends Monster {
    private long noTurningTime;
    private Vector2D currenDirection;
    private final double speed = 0.20;

    PassiveMonster(String ref, TiledMap map) {
        super(ref, map);
        this.type = "Passive";
        this.timeSinceLastFight = 6000;
        this.noTurningTime = 0;
        this.currenDirection = genRandomDirection();
    }

    void update(Player player, int delta) {
        this.timeSinceLastFight += delta;
        if (this.timeSinceLastFight >= 5000)
        {
            if (noTurningTime >= 3000)
            {
                currenDirection = genRandomDirection();
                noTurningTime = 0;
            }
            double ds = delta * speed;
            double dx = ds * currenDirection.x / currenDirection.lengh();
            double dy = ds * currenDirection.y / currenDirection.lengh();
            move(dx, dy);
            noTurningTime += delta;
        }
        if (this.timeSinceLastFight < 5000)
        {
            currenDirection = new Vector2D(this.getX() - player.getX(), this.getY() - player.getY());
            double ds = delta * speed;
            double dx = ds * currenDirection.x / currenDirection.lengh();
            double dy = ds * currenDirection.y / currenDirection.lengh();
            move(dx, dy);
            noTurningTime += delta;
        }
    }
}